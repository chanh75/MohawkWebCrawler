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
import com.mohawk.webcrawler.lang.ScriptContext.SvgContext;

public class NextSvgText_Verb implements BaseVerb {

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
        if (svgContext == null) {
            throw new NotSetException("SVG context not sent.");
        }

        String gHtml = svgContext.getGHtml(); //System.out.println("NextSvgText gHtml>> " + gHtml);
        int textPosInG = svgContext.getTextPositionInG();
        if (gHtml == null) { // || gPos == -1) {
            throw new NotSetException("SVG <g> tag not set.");
        }
        int searchPos = -1;

        if (HtmlUtils.startsWithTag(gHtml, "<text>", textPosInG)) {
            String startTag = HtmlUtils.getTagNameAtPosition(gHtml, textPosInG);
            String endTag = "</" + startTag + '>';
            int endTagPos = gHtml.indexOf(endTag, textPosInG);
            searchPos = endTagPos + endTag.length();
        } else {
            searchPos = 0;
        }

        int startPos = HtmlUtils.indexOfStartTag(gHtml, "<text>", searchPos);

        if (startPos == -1) {
            return false;
        }

        int endPos = gHtml.indexOf("</text>", startPos);
        if (endPos == -1) {
            throw new NotFoundException("</text> not found at current cursor position.");
        }

        String textHtml = gHtml.substring(startPos, endPos + "</text>".length());
        //System.out.println("NextSvgText html>> " + textHtml);

        svgContext.setTextHtml(textHtml);
        //svgContext.setTextPositionInSvg(startPos);

        int gPos = svgContext.getGPositionInSvg();

        svgContext.setCursorPosition(gPos + textPosInG + startPos);
        /*
        int k = svgContext.getCursorPosition();
        System.out.println("NextSvgText check1>> " + svgContext.getSvgHtml().substring(k, k+50));
        */

        int svgPos = svgContext.getPositionInDocument();
        pageContext.setCursorPosition(svgPos + gPos + textPosInG + startPos);
        /*
        int p = pageContext.getCursorPosition();
        System.out.println("NextSvgText check2>> " + pageContext.getDocumentHtml().substring(p, p + 30));
        */
        return true;
    }
}
