package com.mohawk.webcrawler.lang.verb;

import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.Variable;

public class Length_Verb implements BaseVerb {

	@Override
	public int numOfParams() {
		return 1;
	}
	
	@Override
	public ReturnType returnType() {
		return ReturnType.INTEGER;
	}
	
	@Override
	public Object run(ScriptContext pageContext, Object ... params) throws Exception {
		
		//System.out.println("..Run Length..");
		
		Object p1 = LangCore.resolveParameter(pageContext, params[0]);
		Object p1Val = null;
		
		if (p1 instanceof Variable) {
			p1Val = ((Variable) p1).getValue();
		} else if (p1 instanceof String) {
			p1Val = (String) p1;
		} else if (p1 instanceof String[]) {
			p1Val = (String[]) p1;
		}
		
		if (p1Val instanceof String) {
			return ((String) p1Val).length();
		} else if (p1Val instanceof String[]) {
			return ((String[]) p1Val).length;
		}
		
		throw new LanguageException("Unable to determine length of object>> " + params[0]);
	}
}
