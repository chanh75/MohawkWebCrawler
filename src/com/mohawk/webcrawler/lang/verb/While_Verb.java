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

import com.mohawk.webcrawler.lang.BaseLoopVerb;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.BaseVerb.ReturnType;

public class While_Verb extends BaseLoopVerb {

    @Override
    public int numOfParams() {
        return 1;
    }

    @Override
    public ReturnType returnType() {
        return ReturnType.VOID;
    }

    @Override
    public boolean shouldLoop() throws LanguageException {

        String eval = getExpression();
        ScriptContext pageContext = getPageContext();
        //System.out.println("shouldLoop eval>> " + eval);

        boolean result = LangCore.evaluateExpression(pageContext, eval);
        //System.out.println("result>> " + result);

        return result;

    }

    public Object run(ScriptContext pageCursor, Object ... param) {

        return null;
    }
}
