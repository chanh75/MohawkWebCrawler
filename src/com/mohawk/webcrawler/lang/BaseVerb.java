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
package com.mohawk.webcrawler.lang;


/**
 * Base verb.  Verbs are string tokens in a script file that perform some action.
 *
 * @author cnguyen
 *
 */
public abstract class BaseVerb extends BaseToken {

    public enum ReturnType {
        VOID,
        BOOLEAN,
        INTEGER,
        DOUBLE,
        STRING
    }

    /**
     *
     * @return
     */
    public abstract int numOfParams();

    /**
     *
     * @return
     */
    public abstract ReturnType returnType();

    /**
     *
     * @param scriptContext
     * @param params
     * @return
     * @throws Exception
     */
    public abstract Object run(ScriptContext scriptContext, Object ... params) throws Exception;

}
