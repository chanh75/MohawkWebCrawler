package com.mohawk.webcrawler.lang.verb;

import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.ScriptContext;

public class NextText_Verb implements BaseVerb {

	@Override
	public int numOfParams() {
		return 1;
	}

	@Override
	public ReturnType returnType() {
		return ReturnType.BOOLEAN;
	}

	@Override
	public Object run(ScriptContext scriptContext, Object... params)
			throws Exception {
		
		Object p1 = LangCore.resolveParameter(scriptContext, params[0]);
		
		if (!(p1 instanceof String)) {
			throw new LanguageException("Parameter must be a string literal>> " + p1);
		}
		
		int i = scriptContext.indexOfTextFromCurrent((String) p1);
		if (i == -1) {
			//throw new NotFoundException("Unable to find tag <h3>");
			return false;
		}
		
		scriptContext.setCursorPosition(i);
		return true;
	}
}
