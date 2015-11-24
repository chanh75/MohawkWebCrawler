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

public class LogicalOr_Operator extends BaseOperator {

    @Override
    public int numOfParams() {
        return 2;
    }

    @Override
    public OperReturnType returnType() {
        return OperReturnType.BOOLEAN;
    }

    @Override
    public Object run(ScriptContext pageContext, Object... params) throws LanguageException {

        Object p1 = LangCore.resolveParameter(pageContext, params[0]);

        if (!(p1 instanceof Boolean))
            throw new LanguageException("First parameter must be of type Boolean>> " + p1);
        else {
            Boolean p1Val = (Boolean) p1;
            if (p1Val)
                return true;
            else if (params.length > 1) {
                Object p2 = LangCore.resolveParameter(pageContext, params[1]);
                if (p2 instanceof Boolean)
                    return p2;
            }
        }

        return false;
    }
}
