package com.mohawk.webcrawler.lang.verb;

import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.Variable;

public class WordCount_Verb implements BaseVerb {

	@Override
	public int numOfParams() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public ReturnType returnType() {
		// TODO Auto-generated method stub
		return ReturnType.INTEGER;
	}

	@Override
	public Object run(ScriptContext pageContext, Object... params)
			throws Exception {
		
		Object p1 = LangCore.resolveParameter(pageContext, params[0]);
		String value = null;
		
		if (p1 instanceof Variable) {
			value = String.valueOf(((Variable) p1).getValue());
		} else if (p1 instanceof String) {
			value = (String) p1;
		} else {
			throw new LanguageException("Unable to count number of words in>> " + p1);
		}
		
		String value2 = value.replaceAll("[^A-Za-z0-9 ]", " ");
		
		if (value2.equals("y")) {
			System.out.println("stop");
		}
		String[] arr = ((String) value2).split("\\s+");
		
		int decrease = 0;
		for (String a : arr) {
			if (a.length() == 0) {
				decrease++;
			} else if (a.length() == 1) {
				char c = a.charAt(0);
				if (c != 'I' && c != 'i' && c != 'A' && c != 'a') {
					decrease++;
				} else if (c == '\0') {
					decrease++;
				}
			}
		}
		//System.out.println("Wordcount>> [" + value +"]>>" + (arr.length - decrease));
		return arr.length - decrease;
	}

}
