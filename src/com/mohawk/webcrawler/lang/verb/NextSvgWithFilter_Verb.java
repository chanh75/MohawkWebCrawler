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
import com.mohawk.webcrawler.lang.BreakException;
import com.mohawk.webcrawler.lang.HtmlUtils;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.ScriptContext.SvgContext;

public class NextSvgWithFilter_Verb implements BaseVerb {

    @Override
    public int numOfParams() {
        return 1;
    }

    @Override
    public ReturnType returnType() {
        return ReturnType.BOOLEAN;
    }

    @Override
    public Object run(ScriptContext scriptContext, Object... params) throws Exception {

        //System.out.println("filter>> " + params[0]);
        //String filter = LangCore.createStringLiteral(params[0]);
        String filter = (String) LangCore.resolveParameter(scriptContext, params[0]);
        //System.out.println("filter>> " + filter);

        String docHtml = scriptContext.getDocumentHtml();
        int curPos = scriptContext.getCursorPosition();

        //System.out.println("NextSvgWithFilter >>" + docHtml.substring(curPos, curPos + 80));

        if (HtmlUtils.startsWithTag(docHtml, "<svg>", curPos)) { // move starting position to search for next <svg>
            curPos += 4;
        }

        int svgStartPos = curPos;
        do {
            svgStartPos = HtmlUtils.indexOfStartTag(docHtml, "<svg>", curPos);

            if (svgStartPos != -1) {
                int end = docHtml.indexOf(">", svgStartPos + 1);
                String tag = docHtml.substring(svgStartPos, end + 1);

                if (tag.indexOf(filter) != -1) { // found
                    int svgEndPos = docHtml.indexOf("</svg>", svgStartPos + 1);

                    String svgBody = docHtml.substring(svgStartPos, svgEndPos + "</svg>".length());

                    scriptContext.setSvgContext(new SvgContext(svgBody, svgStartPos));
                    scriptContext.setCursorPosition(svgStartPos);

                    int k = scriptContext.getCursorPosition();
                    //System.out.println("svgBody check>> " + pageContext.getDocumentHtml().substring(k, k + 60));

                    return true;
                } else {
                    curPos = svgStartPos + 1;
                }
            }

        } while (svgStartPos != -1);

        return false;
     }

}
