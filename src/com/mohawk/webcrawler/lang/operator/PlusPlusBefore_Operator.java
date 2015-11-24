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
package com.mohawk.webcrawler.lang.operator;

import com.mohawk.webcrawler.lang.BaseOperator;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.Variable;

public class PlusPlusBefore_Operator extends BaseOperator {

    @Override
    public int numOfParams() {
        return 2;
    }

    @Override
    public OperReturnType returnType() {
        return OperReturnType.VOID;
    }

    @Override
    public Object run(ScriptContext pageContext, Object... params)
            throws Exception {

        Object p = LangCore.resolveParameter(pageContext, params[0]);

        if (p instanceof Variable) {
            Variable pVar = (Variable) p;
            Object val = ((Variable) p).getValue();

            if (val instanceof Integer) {
                int intVal = ((Integer) val) + 1;
                pageContext.setLocalVariable(pVar.getName(), intVal);
                return null;
            }
        }

        throw new LanguageException("++ can only be performed on integer variables>> " + p);
    }
}
