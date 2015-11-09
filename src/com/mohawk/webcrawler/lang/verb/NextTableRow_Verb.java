package com.mohawk.webcrawler.lang.verb;

import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.HtmlUtils;
import com.mohawk.webcrawler.lang.NotFoundException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.BaseVerb.ReturnType;
import com.mohawk.webcrawler.lang.ScriptContext.TableContext;

public class NextTableRow_Verb implements BaseVerb {

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

        TableContext tableContext = pageContext.getTableContext();

        String tbodyHtml = tableContext.getTableBodyHtml();
        String tableHtml = tbodyHtml != null ? tbodyHtml : tableContext.getTableHtml();

        int curRowPos = tableContext.getEndRowPositionInTable();

        int startTagPos = HtmlUtils.indexOfStartTag(tableHtml, "<tr>", curRowPos);
        if (startTagPos == -1) {
            return false;
        }

        int endPos = tableHtml.indexOf("</tr>", startTagPos) ;
        if (endPos == -1) {
            throw new NotFoundException("</tr> not found within table context.");
        }

        int endTagPos = endPos + "</tr>".length();
        String rowText = tableHtml.substring(startTagPos, endTagPos);

        tableContext.setStartRowPositionInTable(startTagPos);
        tableContext.setEndRowPositionInTable(endTagPos);

        tableContext.setRowHtml(rowText);

        int tablePos = tableContext.getTablePositionInDocument();
        int tbodyPos = tableContext.getTableBodyPositionInTable();

        // reset column cursor
        tableContext.setColumnPositionInRow(0);

        int cpos = tablePos + tbodyPos + startTagPos;
        pageContext.setCursorPosition(cpos);

        return true;

    }
}
