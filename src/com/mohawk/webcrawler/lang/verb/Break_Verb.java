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

import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.BreakException;
import com.mohawk.webcrawler.lang.ScriptContext;

public class Break_Verb implements BaseVerb {

    @Override
    public int numOfParams() {
        return 0;
    }

    @Override
    public ReturnType returnType() {
        return ReturnType.VOID;
    }

    @Override
    public Object run(ScriptContext context, Object ... params) throws BreakException {

        throw new BreakException();
    }
}
