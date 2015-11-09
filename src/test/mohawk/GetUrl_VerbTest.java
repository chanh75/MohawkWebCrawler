package test.mohawk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.net.UnknownHostException;

import org.junit.Test;

import com.mohawk.webcrawler.lang.BaseVerb.ReturnType;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.verb.GetUrl_Verb;

public class GetUrl_VerbTest {

	@Test
	public void testNumOfParams() {
		GetUrl_Verb verb = new GetUrl_Verb();
		int num = verb.numOfParams();
		
		assertEquals(1, num);
	}

	@Test
	public void testReturnType() {
		GetUrl_Verb verb = new GetUrl_Verb();
		ReturnType rt = verb.returnType();
		
		assertEquals(ReturnType.VOID, rt);
	}

	@Test
	public void testRun() {
		
		try {
			
			GetUrl_Verb verb = new GetUrl_Verb();
			
			ScriptContext.Config config = new ScriptContext.Config();
			ScriptContext scriptContext = new ScriptContext(config);
			Object[] params = new Object[] { "\"http://www.yahoo.com\"" };
			
			verb.run(scriptContext, params);
			
			String html = scriptContext.getDocumentHtml();
			
			assertNotNull(html);
		
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	@Test
	public void testRunFail1() {
		
		try {
			
			GetUrl_Verb verb = new GetUrl_Verb();
			
			ScriptContext.Config config = new ScriptContext.Config();
			ScriptContext scriptContext = new ScriptContext(config);
			Object[] params = new Object[] { "\"http://hyeff.com/\"" };
			
			verb.run(scriptContext, params);
			
			String html = scriptContext.getDocumentHtml();
			System.out.println("html>> " + html);
			
			fail("Should not be here.");
		
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals(UnknownHostException.class, e.getClass());
		}
	}
}
