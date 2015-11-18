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

import com.mohawk.webcrawler.lang.BaseVariable;
import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.ScriptContext;

public class Var_Verb implements BaseVerb {

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

//        String variableName = (String) params[0];
//        if (LangCore.isVerb(variableName)) {
//            throw new Exception("Variable name is a reserved verb.");
//        }

        if (!(params[0] instanceof BaseVariable))
            throw new Exception("var error: first parameter must be a variable name>> " + params[0]);

        String varName = ((BaseVariable) params[0]).getName();
        pageContext.defineLocalVariable(varName);
        return null;
    }
}
