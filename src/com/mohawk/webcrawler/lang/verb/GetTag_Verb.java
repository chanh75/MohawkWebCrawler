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
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.NotSetException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.Variable;

public class GetTag_Verb implements BaseVerb {

    @Override
    public int numOfParams() {
        return 1;
    }

    @Override
    public ReturnType returnType() {
        return ReturnType.VOID;
    }

    @Override
    public Object run(ScriptContext pageContext, Object ... params) throws Exception {

        Object p1 = LangCore.resolveParameter(pageContext, params[0]);

        if (!(p1 instanceof Variable)) {
            throw new LanguageException("Variable expected as parameter>> " + params[0]);
        }
        Variable var = (Variable) p1;

        String html = pageContext.getDocumentHtml();
        int curPos = pageContext.getCursorPosition();
        //System.out.println("GetTag html>> " + html.substring(curPos, curPos + 30));

        if (!HtmlUtils.startsWithTag(html, curPos)) {
            throw new NotSetException("Current position in document is not a tag.");
        }

        String rawTagText = HtmlUtils.getTagBodyAtPosition(html, curPos);
        //System.out.println("GetTag raw>> " + rawTagText);

        pageContext.setLocalVariable(var.getName(), rawTagText);

        return null;
    }
}
