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
 * Base class for operators
 *
 * @author cnguyen
 *
 */
public interface BaseOperator extends BaseToken {

    public enum OperReturnType {
        VOID,
        BOOLEAN
    }

    /**
     *
     * @return number of parameters that the operator requires
     */
    public int numOfParams();

    /**
     *
     * @return return type of operator
     */
    public OperReturnType returnType();

    /**
     * Invoked by the engine when the operator is to be executed.
     *
     * @param scriptContext
     * @param params
     * @return
     * @throws Exception
     */
    public Object run(ScriptContext scriptContext, Object ... params) throws Exception;

}
