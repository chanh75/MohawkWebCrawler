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
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.ScriptContext.SvgContext;

public class NextSvgGWithFilter_Verb extends BaseVerb {

    @Override
    public int numOfParams() {
        return 1;
    }

    @Override
    public ReturnType returnType() {
        return ReturnType.BOOLEAN;
    }

    @Override
    public Object run(ScriptContext pageContext, Object... params)
            throws Exception {

        //String filter = LangCore.makeStringLiteral(params[0]);
        String filter = (String) LangCore.resolveParameter(pageContext, params[0]);
        //System.out.println("filter>> " + filter);

        SvgContext svgContext = pageContext.getSvgContext();
        String svgHtml = svgContext.getSvgHtml();
        int curPos = svgContext.getCursorPosition();
        int svgStartPos = -1;

        if (HtmlUtils.startsWithTag(svgHtml, "<g>", curPos)) {
            //svgStartPos = HtmlUtils.nextTag(svgHtml, "<g>", curPos + 2);
            String startTag = HtmlUtils.getTagNameAtPosition(svgHtml, curPos);
            String endTag = "</" + startTag + '>';
            int endTagPos = svgHtml.indexOf(endTag, curPos + 2);
            svgStartPos = endTagPos + endTag.length();
        }
        else
            svgStartPos = curPos;


        do {
            //System.out.println("NextSvgGFilter>> " + svgHtml.substring(svgStartPos, svgStartPos + 200));
            svgStartPos = HtmlUtils.indexOfStartTag(svgHtml, "<g>", svgStartPos);

            if (svgStartPos != -1) {
                int end = svgHtml.indexOf(">", svgStartPos + 1);
                String tag = svgHtml.substring(svgStartPos, end + 1);

                if (tag.indexOf(filter) != -1) { // found
                    int gEndPos = svgHtml.indexOf("</g>", svgStartPos + 1);

                    String gBody = svgHtml.substring(svgStartPos, gEndPos + "</g>".length());
                    //System.out.println("NextSvgGFilter gTag>> " + gBody);

                    svgContext.setGHtml(gBody);
                    svgContext.setGPositionInSvg(svgStartPos);
                    svgContext.setCursorPosition(svgStartPos);
                    /*
                    int k = svgContext.getCursorPosition();
                    System.out.println("nextSvgGFilter check1>> " + svgContext.getSvgHtml().substring(k, k + 50));
                    */
                    pageContext.setCursorPosition(svgContext.getPositionInDocument() + svgStartPos);
                    /*
                    int j = pageContext.getCursorPosition();
                    System.out.println("nextSvgGFilter check2>> " + pageContext.getDocumentHtml().substring(j, j + 50));
                    */
                    return true;
                } else {
                    // filter not satisifed, so move onto next <g> tag
                    svgStartPos = svgHtml.indexOf("<g ", svgStartPos + 2);
                }
            }

        } while (svgStartPos != -1);

        return false;
    }

}
