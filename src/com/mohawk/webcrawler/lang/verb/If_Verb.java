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

import java.util.ArrayList;

import com.mohawk.webcrawler.lang.BaseConditionalVerb;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.ScriptContext;

/**
 * If verb for if-then tokens
 *
 * @author cnguyen
 *
 */
public class If_Verb extends BaseConditionalVerb {

    private ArrayList<ElseIf_Verb> elseIfVerbs;
    private Else_Verb elseVerb;

    @Override
    public int numOfParams() {
        return 0;
    }

    @Override
    public ReturnType returnType() {
        return ReturnType.VOID;
    }

    @Override
    public Object run(ScriptContext pageContext, Object ... params) throws Exception {
        return true;
    }

    @Override
    public boolean shouldRunIf(ScriptContext pageContext) throws LanguageException {

        String eval = getExpression();
        return LangCore.evaluateExpression(pageContext, eval);
    }

    public void addElseIf(ElseIf_Verb verb) {
        if (this.elseIfVerbs == null)
            this.elseIfVerbs = new ArrayList<>();

        this.elseIfVerbs.add(verb);
    }

    public boolean hasElseIf() {
        return (this.elseIfVerbs != null && this.elseIfVerbs.size() > 0);
    }

    public ArrayList<ElseIf_Verb> getElseIfVerbs() {
        return this.elseIfVerbs;
    }

    public void setElse(Else_Verb verb) {
        this.elseVerb = verb;
    }

    public boolean hasElse() {
        return this.elseVerb != null;
    }

    public Else_Verb getElseVerb() {
        return this.elseVerb;
    }

    public String toString() {
        return super.getScope().toString();
    }
}
