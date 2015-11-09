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
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.Variable;

public class Assign_Operator implements BaseOperator {

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

        //Object p1 = LangCore.resolveParameter(pageContext, params[0]);
        String varName = ((Variable) params[0]).getName();
        Object value = LangCore.resolveParameter(pageContext, params[1]);

        pageContext.setLocalVariable(varName, value);

        return null;
    }

}
