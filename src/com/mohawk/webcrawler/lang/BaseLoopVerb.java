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

import java.util.LinkedList;
import java.util.Queue;

/**
 * Base verb class for looping-based verbs
 *
 * @author cnguyen
 *
 */
public abstract class BaseLoopVerb<T> extends LinkedList<T> implements BaseVerb {

    private String _expression = null;
    private ScriptContext _scriptContext = null;

    public void setExpression(String expression) {
        _expression = expression;
    }

    public String getExpression() {
        return _expression;
    }

    public void setPageContext(ScriptContext scriptContext) {
        _scriptContext = scriptContext;
    }

    public ScriptContext getPageContext() {
        return _scriptContext;
    }

    public abstract boolean shouldLoop() throws LanguageException;

}
