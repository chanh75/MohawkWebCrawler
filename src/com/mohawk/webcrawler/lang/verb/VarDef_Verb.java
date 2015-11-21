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

import com.mohawk.webcrawler.lang.BaseLiteral;
import com.mohawk.webcrawler.lang.BaseVariable;
import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.ScriptContext;

public class VarDef_Verb implements BaseVerb {

    @Override
    public int numOfParams() {
        return 2;
    }

    @Override
    public ReturnType returnType() {
        return ReturnType.VOID;
    }

    @Override
    public Object run(ScriptContext pageContext, Object ... params) throws Exception {

        if (!(params[0] instanceof BaseVariable))
            throw new LanguageException("vardef error: First parameter must be a variable, but is not.");

        if (!(params[1] instanceof BaseLiteral))
            throw new LanguageException("vardef error: Second parameter is not a literal.");

        String varName = ((BaseVariable) params[0]).getName(); // must be a string value

        if (LangCore.isVerb(varName))
            throw new LanguageException("Variable name cannot be a reserved verb.");

        Object value = ((BaseLiteral) params[1]).getValue();
        //if (!LangCore.isLiteral(value))
        //   throw new LanguageException("Value must be a literal number or string>> " + value);

        pageContext.defineLocalVariable(varName);
        pageContext.setLocalVariable(varName, /*LangCore.createLiteral(value)*/value);
        return null;
    }
}
