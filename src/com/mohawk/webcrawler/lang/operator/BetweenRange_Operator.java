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

public class BetweenRange_Operator extends BaseOperator {

    @Override
    public int numOfParams() {
        return 2;
    }

    @Override
    public OperReturnType returnType() {
        return OperReturnType.BOOLEAN;
    }

    @Override
    public Object run(ScriptContext pageContext, Object... params) throws Exception {

        Object p1 = LangCore.resolveParameter(pageContext, params[0]);
        Object p2 = LangCore.resolveParameter(pageContext, params[1]);
        Object p1Val = null;

        if (p1 instanceof Variable)
            p1Val = ((Variable) p1).getValue();
        else if (p1 instanceof Integer)
            p1Val = (Integer) p1;

        if (p1Val instanceof String) {
            String strValue = (String) p1Val;
            Object[] set = (Object[]) p2;
            for (Object s : set) {
                if (strValue.indexOf((String) s) != -1)
                    return true;
            }
            return false;
        }
        else if (p1Val instanceof Number) {
            int numValue = (Integer) p1Val;
            Object[] set = (Object[]) p2;
            return numValue >= (int) set[0] && numValue <= (int) set[1];
        }

        throw new LanguageException("Unable to determine in-between for '" + p1 + "' in range: " + p2);
    }

}
