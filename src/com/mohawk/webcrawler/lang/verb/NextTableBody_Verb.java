package com.mohawk.webcrawler.lang.verb;

import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.HtmlUtils;
import com.mohawk.webcrawler.lang.NotFoundException;
import com.mohawk.webcrawler.lang.NotSetException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.BaseVerb.ReturnType;
import com.mohawk.webcrawler.lang.ScriptContext.TableContext;

public class NextTableBody_Verb implements BaseVerb {

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
		
		// verb only works when table context has been set.
		TableContext tableContext = pageContext.getTableContext();
		if (tableContext == null) {
			throw new NotSetException("Table context has not been called.");
		}
		
		String tableHtml = tableContext.getTableHtml();
		//System.out.println("tableHtml>> " + tableHtml);
		
		int start = HtmlUtils.indexOfStartTag(tableHtml, "<tbody>");
		
		if (start == -1) {
			//throw new NotFoundException("<tbody> not found within table context.");
			return false;
		} else {
			
			int end = tableHtml.indexOf("</tbody>", start);
			String tbodyHtml = tableHtml.substring(start, end + "</tbody>".length());
			//System.out.println("tbodyHtml>>" + tbodyHtml);
			
			tableContext.setTableBodyHtml(tbodyHtml);
			tableContext.setTableBodyPositionInTable(start);
			
			int tablePos = tableContext.getTablePositionInDocument();
			pageContext.setCursorPosition(tablePos + start);
			
			/*
			int s = pageContext.getCursorPosition();
			System.out.println("NextTableBody check>> " + pageContext.getDocumentHtml().substring(s, s + 40));
			*/
			
			return true;
		}		
	}
}
