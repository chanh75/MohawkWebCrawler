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
import com.mohawk.webcrawler.lang.NotSetException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.BaseVerb.ReturnType;
import com.mohawk.webcrawler.lang.ScriptContext.TableContext;

public class NextTableCol_Verb extends BaseVerb {

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

        if (tableContext == null) {

            int index = pageContext.indexOfTagFromCurrent("<td>");
            if (index == -1) {
                return false;
            } else {
                pageContext.setCursorPosition(index);
                return true;
            }

        } else {

            String rowHtml = pageContext.getTableContext().getRowHtml();
            if (rowHtml == null) {
                throw new NotSetException("Row html has not beed set.");
            }

            int columnPos = tableContext.getColumnPositionInRow();

            int startPos = HtmlUtils.indexOfStartTag(rowHtml, "<td>", columnPos);
            if (startPos == -1) {
                //throw new NotFoundException("<td> not found in row context.");
                return false;
            }

            int endPos = rowHtml.indexOf("</td>", startPos);
            if (endPos == -1) {
                throw new NotFoundException("</td> not found in row context.");
            }

            String tdText = rowHtml.substring(startPos, endPos + "</td>".length());
            //System.out.println("NextCol colText>> " + tdText);

            tableContext.setColumnPositionInRow(columnPos + startPos);

            int tablePos = tableContext.getTablePositionInDocument();
            int tableBodyPos = tableContext.getTableBodyPositionInTable();
            int rowStartPos = tableContext.getStartRowPositionInTable();

            int s = tablePos + tableBodyPos + rowStartPos + startPos;
            pageContext.setCursorPosition(s);

            //System.out.println("NextCol check>> " + pageContext.getDocumentHtml().substring(s, s + 10));

            tableContext.setColumnPositionInRow(endPos + "</td>".length());

            return true;
        }
    }
}
