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

public class NextTable_Verb implements BaseVerb {

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

        String documentHtml = pageContext.getDocumentHtml();
        int curPos = pageContext.getCursorPosition();
        //System.out.println("next table>> " + documentHtml.substring(curPos, curPos + 30));

        if (HtmlUtils.startsWithTag(documentHtml, "<table>", curPos)) {
            String endTag = "</table>";
            int endPos = documentHtml.indexOf(endTag, curPos + 7);
            if (endPos != -1) {
                curPos = endPos + 8;
            }
        }

        int start = HtmlUtils.indexOfStartTag(documentHtml, "<table>", curPos);

        if (start == -1) {
            //throw new NotFoundException("<table> not found after the current cursor position.");
            return false;
        }

        int end = documentHtml.indexOf("</table>", start);
        if (end == -1) {
            throw new NotFoundException("</table> not found at current cursor position.");
        }
        String tableHtml = documentHtml.substring(start, end + "</table>".length());

        pageContext.setCursorPosition(start);
        pageContext.setTableContext(new TableContext(tableHtml, start));
        /*
        int s = pageContext.getCursorPosition();
        System.out.println("Nexttable check>> " + documentHtml.substring(s, s + 100));
        */
        return true;
    }
}
