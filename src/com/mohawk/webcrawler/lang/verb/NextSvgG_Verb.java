package com.mohawk.webcrawler.lang.verb;

import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.HtmlUtils;
import com.mohawk.webcrawler.lang.NotSetException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.ScriptContext.SvgContext;

public class NextSvgG_Verb implements BaseVerb {

	@Override
	public int numOfParams() {
		return 0;
	}
	
	@Override
	public ReturnType returnType() {
		return ReturnType.BOOLEAN;
	}
	
	@Override 
	public Object run(ScriptContext pageContext, Object ... params) throws Exception {
		//System.out.println("..In NextSvgG...");
		SvgContext svgContext = pageContext.getSvgContext();
		if (svgContext == null) {
			throw new NotSetException("Svg Context not set.");
		}
		String svgHtml = svgContext.getSvgHtml();
		int svgCurPos = svgContext.getCursorPosition(); 
		//System.out.println("svgCurPos>> " + svgCurPos + ":" + svgHtml.substring(svgCurPos, svgCurPos + 40));
		int searchPos = -1;
		
		if (HtmlUtils.startsWithTag(svgHtml, "<g>", svgCurPos)) { // if cursor is at current tag, go to next tag
			String startTag = HtmlUtils.getTagNameAtPosition(svgHtml, svgCurPos);
			String endTag = "</" + startTag + '>';
			int endTagPos = svgHtml.indexOf(endTag, svgCurPos + 1);
			searchPos = endTagPos + endTag.length();
		} else {
			searchPos = svgCurPos;
		}
		
		int gTagStartPos = HtmlUtils.indexOfStartTag(svgHtml, "<g>", searchPos);
		if (gTagStartPos == -1)  {
			return false;
		} else {
		
			int gTagEndPos = svgHtml.indexOf("</g>", gTagStartPos + 3);
			String gTagHtml = svgHtml.substring(gTagStartPos, gTagEndPos + "</g>".length());
			//System.out.println("gTag>> " + gTagHtml);
			
			svgContext.setGHtml(gTagHtml);
			svgContext.setGPositionInSvg(gTagStartPos);
			svgContext.setCursorPosition(gTagStartPos);
			
			int svgPosInDoc = svgContext.getPositionInDocument();
			pageContext.setCursorPosition(svgPosInDoc + gTagStartPos);
			
			
			int j = pageContext.getCursorPosition();
			//System.out.println("NextSvgG check>> "+ pageContext.getDocumentHtml().substring(j ,j + 50));
			
			return true;
		}
	}
}
