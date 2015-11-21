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

public class NextH3_Verb implements BaseVerb {

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

        String html = pageContext.getDocumentHtml();
        int curPos = pageContext.getCursorPosition();
        int index = -1;

        if (HtmlUtils.startsWithTag(html, "<h3>", curPos)) {
            String endTag = "</h3>";
            int endTagPos = html.indexOf(endTag, curPos + 4);
            int searchPos = endTagPos + endTag.length();
            index = html.indexOf("<h3>", searchPos);
        }
        else
            index = pageContext.indexOfTagFromCurrent("<h3>");

        if (index == -1)
            return false;

        pageContext.setCursorPosition(index);
        return true;
    }
}
