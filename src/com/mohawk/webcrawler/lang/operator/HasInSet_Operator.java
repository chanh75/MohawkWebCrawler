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

public class HasInSet_Operator implements BaseOperator {

    @Override
    public int numOfParams() {
        // TODO Auto-generated method stub
        return 2;
    }

    @Override
    public OperReturnType returnType() {
        // TODO Auto-generated method stub
        return OperReturnType.BOOLEAN;
    }

    @Override
    public Object run(ScriptContext pageContext, Object... params)
            throws Exception {

        Object p1 = LangCore.resolveParameter(pageContext, params[0]);
        Object p1Val = null;
        Object p2 = LangCore.resolveParameter(pageContext, params[1]);

        if (p1 instanceof Variable) {
            p1Val = ((Variable) p1).getValue();
        }

        if (p1Val instanceof String) {
            String strValue = (String) p1Val;
            Object[] set;
            if (p2 instanceof String) {
                set = new Object[] { p2 };
            } else {
                set = (Object[]) p2;
            }

            for (Object s : set) {
                if (strValue.indexOf((String) s) != -1) {
                    return true;
                }
            }
        } else if (p1Val instanceof Number) {
            Number numValue = (Number) p1Val;
            Object[] set = (Object[]) p2;
            for (Object s : set) {
                if (numValue == ((Number) s)) {
                    return true;
                }
            }
        }

        return false;
    }

}
