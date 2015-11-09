package com.mohawk.webcrawler.lang.verb;

import com.mohawk.webcrawler.lang.BaseConditionalVerb;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.ScriptContext;

public class Else_Verb extends BaseConditionalVerb {

	@Override
	public int numOfParams() {
		return 0;
	}
	
	@Override
	public ReturnType returnType() {
		return ReturnType.VOID;
	}
	
	@Override
	public Object run(ScriptContext pageContext, Object ... params) throws Exception {
		return true;
	}
	
	@Override
	public boolean shouldRunIf(ScriptContext pageContext) throws LanguageException {
		
		//String eval = getExpression();
		//System.out.println("IfVerb eval>> " + eval);
		
		//boolean result = LangCore.evaluateExpression(pageContext, eval);
		//System.out.println("IfVerb >> " + result + ", " + eval);
		
		return true;
	}
}
