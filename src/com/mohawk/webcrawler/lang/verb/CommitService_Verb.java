package com.mohawk.webcrawler.lang.verb;

import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.ScriptContext;

public class CommitService_Verb implements BaseVerb {

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
		
		//pageContext.commitService();
		return null;
	}
}
