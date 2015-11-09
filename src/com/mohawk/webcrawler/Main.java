package com.mohawk.webcrawler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import com.mohawk.webcrawler.lang.ScriptContext;


public class Main {

    /**
     *
     * @author cnguyen
     *
     */
    private static class ParamContext {

        private boolean debug;
        private String scriptName;
        private String constants;
        private String cacheDir;

        public ParamContext(String[] args) throws Exception {

            for (int i = 0; i < args.length; i++) {
                if ("-d".equals(args[i])) {  // no database inserts/updates
                    this.debug = true;
                } else if ("-p".equals(args[i])) { // batch number of scripts

                } else if ("-s".equals(args[i])) { // name of script
                    this.scriptName = args[++i];
                } else if ("-v".equals(args[i])) { // JSON list of constants/variables
                    this.constants = args[++i];
                } else if ("-c".equals(args[i])) {
                    this.cacheDir = args[++i];
                }
            }
        }
        public boolean debug() { return this.debug; }
        public String getScriptName() { return this.scriptName; }
        public String getConstants() { return this.constants; }
        public String getCacheDirectory() { return this.cacheDir; }
    }

    public static void main(String[] args) {

        try {

            ParamContext arg = new ParamContext(args);

            ScriptContext.Config config = new ScriptContext.Config();

            config.setScriptFilename(arg.getScriptName());
            config.setEnvironmentVariables(arg.getConstants());
            config.setCacheDirectory(arg.getCacheDirectory());
            config.setDebug(arg.debug());

            ScriptContext scriptContext = new ScriptContext(config);

            String scriptFile = config.getScriptFilename();

            // compile the script
            System.out.println("Compiling script>> " + scriptFile);
            LinkedList executable = ScriptCompiler.compile(scriptFile);

            // execute the script
            System.out.println("Executing script>> " + scriptFile);
            ScriptExecutor.exec(scriptContext, executable);

            // print dataset
            ArrayList<ScriptContext.DataSet> dataSet = scriptContext.getDataSet();
            System.out.println("\n");
            for (ScriptContext.DataSet row : dataSet) {
                if (row.getValue().length == 1) {
                    System.out.println("row>> " + row.getLabel() + ":" + row.getValue()[0]);
                } else {
                    System.out.println("row>> " + row.getLabel() + ":" + Arrays.toString(row.getValue()));
                }
            }

            System.out.println("WebCrawler done!");

        } catch (Exception e) {

            // log error in the database
            e.printStackTrace();

        }

    }

}
