package com.mohawk.webcrawler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.mohawk.datawarehouse.WebCrawlerDataWarehouse;
import com.mohawk.datawarehouse.entities.WCProviderConfig;
import com.mohawk.webcrawler.lang.ScriptContext;


public class MainOrg {
	
	/**
	 * 
	 * @author cnguyen
	 *
	 */
	private static class ParamContext {
		
		private boolean debug;
		private String scriptName;
		private WebCrawlerDataWarehouse.SqlWhere where = null;
		
		public ParamContext(String[] args) throws Exception {
	
			for (int i = 0; i < args.length; i++) {
				if ("-debug".equals(args[i])) {  // no database inserts/updates
					this.debug = true;
				} else if ("-p".equals(args[i])) {
					this.where = new WebCrawlerDataWarehouse.SqlWhere(args[++i]);
				} else if ("-s".equals(args[i])) {
					this.scriptName = args[++i];		
				}
			}
		}
		public boolean debug() { return this.debug; }
		public String getScriptName() { return this.scriptName; }
		public WebCrawlerDataWarehouse.SqlWhere getProviderList() { return this.where; }
	}
	
	public static void main(String[] args) {
		
		try {
			
			ParamContext arg = new ParamContext(args);
			
			// trunc the database webcrawler tables
			if (!arg.debug() && arg.getProviderList() == null) {
				System.out.println("Truncating WC tables!");
				WebCrawlerDataWarehouse.truncTables();
			} else if (!arg.debug() && arg.getProviderList() != null) {
				System.out.println("Deleting WC records.");
				int recsDeleted = WebCrawlerDataWarehouse.deleteErrorLog(arg.getProviderList());
				System.out.println("Records deleted>> " + recsDeleted);
			}
			
			// get all the providers to run data scraping for
			
			List<WCProviderConfig> providers = WebCrawlerDataWarehouse.getActiveProviderConfigs(arg.getProviderList());
			
			if (providers.size() == 0) {
				System.out.println("No providers found.");
				return;
			}
			
			System.out.println("Processing WC tasks for providers>> " + Arrays.toString(providers.toArray(new WCProviderConfig[0])));
			
			// loop through each provider
			for (WCProviderConfig provider : providers) {
				
				if (arg.getScriptName() != null) {
					provider.setScript_name(arg.getScriptName());
				}
				
				System.out.println("Running WC task for provider, id=[" + provider.getProvider_id() +"], script=[" + provider.getScript_name() + "]");
				try {
					
					int recsDeleted = WebCrawlerDataWarehouse.deleteProviderServices(provider.getProvider_id());
					System.out.println("Records in wc_provider_service deleted>> " + recsDeleted);
					
					ScriptContext.Config config = new ScriptContext.Config();
					//config.setProviderId(provider.getProvider_id());
					//config.setSourceType(provider.getSource_type());
					//config.setScriptParamUrl(provider.getScript_param_url());
					config.setDebug(arg.debug());
					
					ScriptContext pageContext = new ScriptContext(config);
		
					String dataDirectory = System.getenv("OPENSHIFT_DATA_DIR");
					String scriptsDirectory = null;
					
					if (dataDirectory != null) {
						scriptsDirectory = dataDirectory +"webcrawler/scripts/";
					} else {
						scriptsDirectory = System.getenv("LOCAL_SCRIPTS_DIR") + '\\';
					}
					
					// compile the script
					System.out.println("Compiling script>> " + provider.getScript_name());
					LinkedList executable = ScriptCompiler.compile(scriptsDirectory + provider.getScript_name());
					
					// execute the script
					System.out.println("Executing script>> " + provider.getScript_name());
					ScriptExecutor.exec(pageContext, executable);
					
					/*
					ArrayList<ScriptContext.Service> services = pageContext.getServices();
					
					//System.out.println("Provider Id>> " + provider.getProvider_id() + ":" + pageContext.getConfig().getScriptParamUrl());
					for (ScriptContext.Service service : services) {
						
						System.out.println("Service>> id=[" + provider.getProvider_id() + "], " + 
								"cat1=[" + service.getCategory1() + "], cat2=[" + service.getCategory2() + "], " + 
								"desc=["  + service.getDescription() + "], price=[" + service.getPrice() + "]");
						
						// insert into services table for providers
						if (!arg.debug()) {
							System.out.println("Inserting Service into WC table...");
							WebCrawlerDataWarehouse.createProviderService(
									provider.getProvider_id(),
									service.getCategory1(),
									service.getCategory2(),
									service.getDescription(), 
									service.getPrice());
						}
					}
					*/
					System.out.print("\n\n");
					
				} catch (Exception e) {
					
					// log error in the database
					e.printStackTrace();
					
					if (!arg.debug()) {
						StringWriter writer = new StringWriter();
						PrintWriter pw = new PrintWriter(writer);
						e.printStackTrace(pw);
						pw.flush();
						
						System.out.println("Error! Inserting into WC error table...");
						WebCrawlerDataWarehouse.createErrorLog(provider.getProvider_id(), writer.getBuffer().toString());
					}
				}
			}
			
			System.out.println("WebCrawler done!");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
}
