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
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.ScriptContext.SvgContext;

public class GoText_Verb implements BaseVerb {

    @Override
    public int numOfParams() {
        return 1;
    }

    @Override
    public ReturnType returnType() {
        return ReturnType.BOOLEAN;
    }

    @Override
    public Object run(ScriptContext pageContext, Object ... params) throws Exception {

        Object p1 = LangCore.resolveParameter(pageContext, params[0]);

        if (!(p1 instanceof String))
            throw new LanguageException("String literal parameter required for GoText verb>> " + p1);

        String text = (String) p1;

        int i = -1;
        if (pageContext.getSvgContext() != null) {
            SvgContext svgContext = pageContext.getSvgContext();
            int foundPos = svgContext.indexOf(text);
            if (foundPos != -1) {
                svgContext.setCursorPosition(foundPos);
                i = svgContext.getPositionInDocument() + foundPos;
            }
        }
        else
            i = pageContext.indexOfText(text);

        if (i == -1)
            throw new Exception("Unable to find text within html document>> [" + text + "]");
        else {
            pageContext.setCursorPosition(i);
            return true;
        }

    }
}
