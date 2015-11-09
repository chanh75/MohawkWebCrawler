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
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.ScriptContext;

public class InvertBoolean_Operator implements BaseOperator {

    @Override
    public int numOfParams() {
        // TODO Auto-generated method stub
        return 1;
    }

    @Override
    public OperReturnType returnType() {
        // TODO Auto-generated method stub
        return OperReturnType.BOOLEAN;
    }

    @Override
    public Object run(ScriptContext pageContext, Object... params)
            throws Exception {
        // TODO Auto-generated method stub

        Object p1 = params[0];
        //System.out.println("InvertBool p1>> " + p1);

        if (p1 instanceof Boolean) {
            return !(Boolean)p1;
        }

        throw new LanguageException("Unable to invert value>> " + p1);
    }

}
