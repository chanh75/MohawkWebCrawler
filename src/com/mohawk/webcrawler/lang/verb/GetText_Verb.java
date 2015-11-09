package com.mohawk.webcrawler.lang.verb;

import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.HtmlUtils;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.Variable;

public class GetText_Verb implements BaseVerb {

	@Override
	public int numOfParams() {
		return 1;
	}
	
	@Override
	public ReturnType returnType() {
		return ReturnType.VOID;
	}
	
	@Override
	public Object run(ScriptContext pageContext, Object ... params) throws Exception {
		
		//String variableName = (String) params[0];
		Object p1 = LangCore.resolveParameter(pageContext, params[0]);
		
		if (!(p1 instanceof Variable)) {
			throw new LanguageException("Variable required for GetText verb>> " + p1);
		}
		
		Variable variable = (Variable) p1;
		
		int cursorPos = pageContext.getCursorPosition();
		String documentHtml = pageContext.getDocumentHtml();
		String value = null;
		
		if (HtmlUtils.startsWithTag(documentHtml, cursorPos)) {
			
			value = HtmlUtils.getTagBodyAtPosition(documentHtml, cursorPos);
			//System.out.println("GetText value1>> [" + value + "]");
			
		} else {
			// grab until a '<' is hit
			value = HtmlUtils.getTextAtPosition(documentHtml, cursorPos);
			//System.out.println("GetText value2>> [" + value + "]");
		}
		
		String rawText = HtmlUtils.stripHtml(value).trim();
		//System.out.println("GetText rawText>> [" + rawText + "]");
		pageContext.setLocalVariable(variable.getName(), rawText);
		
		return true;
	}
}
