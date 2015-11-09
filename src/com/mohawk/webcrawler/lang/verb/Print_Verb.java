package com.mohawk.webcrawler.lang.verb;

import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.BaseVerb.ReturnType;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.ScriptContext;

public class Print_Verb implements BaseVerb {

	@Override
	public int numOfParams() {
		return 1;
	}
	
	@Override
	public ReturnType returnType() {
		return ReturnType.VOID;
	}
	
	@Override
	public Object run(ScriptContext scriptContext, Object ... params) throws Exception {
		
		Object p1 = LangCore.resolveParameter(scriptContext, params[0]);
		
		System.out.println("Print: " + String.valueOf(p1));
		
		return null;
	}
}
