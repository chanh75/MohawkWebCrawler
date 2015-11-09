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
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.Variable;

public class GetText_Verb implements BaseVerb {

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

        //String variableName = (String) params[0];
        Object p1 = LangCore.resolveParameter(pageContext, params[0]);

        if (!(p1 instanceof Variable)) {
            throw new LanguageException("Variable required for GetText verb>> " + p1);
        }

        Variable variable = (Variable) p1;

        int cursorPos = pageContext.getCursorPosition();
        String documentHtml = pageContext.getDocumentHtml();
        String value = null;

        if (HtmlUtils.startsWithTag(documentHtml, cursorPos)) {

            value = HtmlUtils.getTagBodyAtPosition(documentHtml, cursorPos);
            //System.out.println("GetText value1>> [" + value + "]");

        } else {
            // grab until a '<' is hit
            value = HtmlUtils.getTextAtPosition(documentHtml, cursorPos);
            //System.out.println("GetText value2>> [" + value + "]");
        }

        String rawText = HtmlUtils.stripHtml(value).trim();
        //System.out.println("GetText rawText>> [" + rawText + "]");
        pageContext.setLocalVariable(variable.getName(), rawText);

        return true;
    }
}
