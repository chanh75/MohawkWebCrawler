package com.mohawk.webcrawler.lang.verb;

import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.ScriptContext;

public class NextDiv_Verb implements BaseVerb {

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
		
		int index = pageContext.indexOfTagFromCurrent("<div>");
		if (index == -1) {
			return false;
		} else {
			
			String html = pageContext.getDocumentHtml();
			//System.out.println("html>> " + html.substring(index, index + 30));
			
			pageContext.setCursorPosition(index);
			return true;
		}
	}
}
