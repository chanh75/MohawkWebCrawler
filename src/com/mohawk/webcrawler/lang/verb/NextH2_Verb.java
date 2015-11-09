package com.mohawk.webcrawler.lang.verb;

import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.ScriptContext;

public class NextH2_Verb implements BaseVerb {

	@Override
	public int numOfParams() {
		return 0;
	}

	@Override
	public ReturnType returnType() {
		return ReturnType.BOOLEAN;
	}

	@Override
	public Object run(ScriptContext pageContext, Object... params)
			throws Exception {
		
		int i = pageContext.indexOfTagFromCurrent("<h2>");
		if (i == -1) {
			//throw new NotFoundException("Unable to find tag <h3>");
			return false;
		}
		
		pageContext.setCursorPosition(i);
		return true;
	}

}
