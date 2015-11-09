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
