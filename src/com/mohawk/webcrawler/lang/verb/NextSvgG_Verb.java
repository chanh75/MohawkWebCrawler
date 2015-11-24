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
import com.mohawk.webcrawler.lang.NotSetException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.ScriptContext.SvgContext;

public class NextSvgG_Verb extends BaseVerb {

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

        SvgContext svgContext = pageContext.getSvgContext();
        if (svgContext == null)
            throw new NotSetException("Svg Context not set.");

        String svgHtml = svgContext.getSvgHtml();
        int svgCurPos = svgContext.getCursorPosition();
        int searchPos = -1;

        if (HtmlUtils.startsWithTag(svgHtml, "<g>", svgCurPos)) { // if cursor is at current tag, go to next tag
            String startTag = HtmlUtils.getTagNameAtPosition(svgHtml, svgCurPos);
            String endTag = "</" + startTag + '>';
            int endTagPos = svgHtml.indexOf(endTag, svgCurPos + 1);
            searchPos = endTagPos + endTag.length();
        }
        else
            searchPos = svgCurPos;

        int gTagStartPos = HtmlUtils.indexOfStartTag(svgHtml, "<g>", searchPos);
        if (gTagStartPos == -1)
            return false;
        else {
            int gTagEndPos = svgHtml.indexOf("</g>", gTagStartPos + 3);
            String gTagHtml = svgHtml.substring(gTagStartPos, gTagEndPos + "</g>".length());

            svgContext.setGHtml(gTagHtml);
            svgContext.setGPositionInSvg(gTagStartPos);
            svgContext.setCursorPosition(gTagStartPos);

            int svgPosInDoc = svgContext.getPositionInDocument();
            pageContext.setCursorPosition(svgPosInDoc + gTagStartPos);
            int j = pageContext.getCursorPosition();

            return true;
        }
    }
}
