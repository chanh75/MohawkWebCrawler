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

import org.apache.commons.lang3.ArrayUtils;

import com.mohawk.webcrawler.lang.BaseOperator;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.Variable;

public class AddThenAssign_Operator implements BaseOperator {

    @Override
    public int numOfParams() {
        return 2;
    }

    @Override
    public OperReturnType returnType() {
        return OperReturnType.VOID;
    }

    @Override
    public Object run(ScriptContext pageContext, Object... params) throws Exception {

        Object p1 = LangCore.resolveParameter(pageContext, params[0]);
        Object p2 = LangCore.resolveParameter(pageContext, params[1]);
        Object p2Val = null;

        // first resolve param 1
        if (p2 instanceof Variable) {
            Variable v2 = (Variable) p2;
            p2Val = pageContext.getLocalVariable(v2.getName());
        }
        else
            p2Val = p2;

        if (p1 instanceof Variable) {

            Variable var1 = (Variable) p1;
            String varName = var1.getName();
            Object p1Val = var1.getValue();

            if (p1Val instanceof Integer && p2Val instanceof Integer)
                pageContext.setLocalVariable(varName, ((Integer) p1Val) + ((Integer) p2Val));
            else if (p1Val instanceof Double && p2Val instanceof Double)
                pageContext.setLocalVariable(varName, ((Double) p1Val) + ((Double) p2Val));
            else if (p1Val instanceof String && p2Val instanceof String)
                pageContext.setLocalVariable(varName, ((String) p1Val) + ((String) p2Val));
            else if (p1Val instanceof Object[] && p2Val instanceof Object[])
                pageContext.setLocalVariable(varName, ArrayUtils.addAll((Object[]) p1Val, (Object[]) p2Val));
            else
                throw new LanguageException("Unable to run operator '+=' on variable (" + varName + ") with value>> " + p2Val);
        }
        else
            throw new LanguageException("Can only run operator '+=' on variables!");

        return null;
    }
}
