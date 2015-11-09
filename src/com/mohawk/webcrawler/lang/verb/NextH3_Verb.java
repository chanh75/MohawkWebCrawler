package com.mohawk.webcrawler.lang.verb;

import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.HtmlUtils;
import com.mohawk.webcrawler.lang.NotFoundException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.BaseVerb.ReturnType;

public class NextH3_Verb implements BaseVerb {

	@Override
	public int numOfParams() {
		return 0;
	}
	
	@Override
	public ReturnType returnType() {
		return ReturnType.BOOLEAN;
	}
	
	@Override
	public Object run(ScriptContext pageContext, Object ... param) throws Exception {
		
		String html = pageContext.getDocumentHtml();
		int curPos = pageContext.getCursorPosition();
		int index = -1;
		
		//System.out.println("h3>> " + html.substring(curPos, curPos + 20));
		if (HtmlUtils.startsWithTag(html, "<h3>", curPos)) {
			
			String endTag = "</h3>";
			int endTagPos = html.indexOf(endTag, curPos + 4);
			int searchPos = endTagPos + endTag.length();
			
			index = html.indexOf("<h3>", searchPos);
			
		} else {
			index = pageContext.indexOfTagFromCurrent("<h3>");
			
		}
		
		if (index == -1) {
			return false;
		}
		
		pageContext.setCursorPosition(index);
		return true;
	}
}
