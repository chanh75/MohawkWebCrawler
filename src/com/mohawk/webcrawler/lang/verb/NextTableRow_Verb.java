/**
 * Copyright 2015 Chanh Nguyen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mohawk.webcrawler.lang.verb;

import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.HtmlUtils;
import com.mohawk.webcrawler.lang.NotFoundException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.BaseVerb.ReturnType;
import com.mohawk.webcrawler.lang.ScriptContext.TableContext;

public class NextTableRow_Verb extends BaseVerb {

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
        if (startTagPos == -1)
            return false;

        int endPos = tableHtml.indexOf("</tr>", startTagPos) ;
        if (endPos == -1)
            throw new NotFoundException("</tr> not found within table context.");

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
