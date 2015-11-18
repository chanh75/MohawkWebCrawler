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
package com.mohawk.webcrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import com.mohawk.webcrawler.lang.BaseConditionalVerb;
import com.mohawk.webcrawler.lang.BaseEndScope;
import com.mohawk.webcrawler.lang.BaseLoopVerb;
import com.mohawk.webcrawler.lang.BaseOperator;
import com.mohawk.webcrawler.lang.BaseToken;
import com.mohawk.webcrawler.lang.BaseVariable;
import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.BreakException;
import com.mohawk.webcrawler.lang.ExitException;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.verb.ElseIf_Verb;
import com.mohawk.webcrawler.lang.verb.If_Verb;

public class ScriptExecutor {

    /**
     * Executes the stack of tokens and verbs.  The stack is generated from the ScriptCompiler process.
     *
     * @param pageContext holds the context for the script
     * @param executable a stack of tokens and verbs to execute
     * @throws IOException
     * @throws Exception
     */
    public static void exec(ScriptContext pageContext, LinkedList executable)
    throws IOException, Exception {

        try {
            exec0(pageContext, executable);
        } catch (ExitException e) { // catch exit verb

        }
    }

    /**
     *
     * @param pageCursor
     * @param queue
     * @return
     * @throws IOException
     * @throws Exception
     */
    private static void exec0(ScriptContext pageContext, LinkedList<? super BaseToken> executable)
    throws IOException, Exception {

        int len = executable.size();

        for (int i = 0; i < len; i++) {
            Object token = executable.get(i);
            if (token instanceof BaseEndScope)
                return;
            else if (token instanceof BaseLoopVerb) { // while loop

                BaseLoopVerb loop = (BaseLoopVerb) token;
                loop.setPageContext(pageContext);
                try {
                    while (loop.shouldLoop())
                        exec0(pageContext, loop);
                }
                catch (BreakException e) { }
            }
            else if (token instanceof BaseConditionalVerb) { // if-then-else

                If_Verb conditionVerb = (If_Verb) token;
                if (conditionVerb.shouldRunIf(pageContext)) {
                    exec0(pageContext, conditionVerb.getScope());
                    continue;
                }

                boolean runElse = true;
                if (conditionVerb.hasElseIf()) {
                    ArrayList<ElseIf_Verb> elseIfScopes = conditionVerb.getElseIfVerbs();
                    for (ElseIf_Verb elseIfVerb : elseIfScopes) {
                        if (elseIfVerb.shouldRunIf(pageContext)) {
                            exec0(pageContext, elseIfVerb.getScope());
                            runElse = false;
                            break;
                        }
                    }
                }
                if (runElse && conditionVerb.hasElse())
                    exec0(pageContext, conditionVerb.getElseVerb().getScope());
            }
            else if (token instanceof BaseVerb) {

                BaseVerb verbObj = (BaseVerb) token;
                int numOfParams = verbObj.numOfParams();
                Object[] params = new Object[numOfParams];

                for (int p = 0; p < numOfParams; p++)
                    params[p] = executable.get(++i);

                verbObj.run(pageContext, params);
            }
            else if (token instanceof BaseVariable &&
                    pageContext.hasLocalVariable(((BaseVariable) token).getName())) { // variable

                String tokenStr = ((BaseVariable) token).getName();
                Object object = executable.get(++i);
                if (!(object instanceof BaseOperator))
                    throw new LanguageException("Variables [" + tokenStr + "] must be followed by an operator>> " + object);

                BaseOperator operObj = (BaseOperator) object;

                int numOfParams = operObj.numOfParams();
                Object[] params = new Object[numOfParams];
                params[0] = pageContext.getLocalVariable(tokenStr);

                for (int p = 1; p < numOfParams; p++)
                    params[p] = executable.get(++i);

                operObj.run(pageContext, params);
            }
            else
                throw new LanguageException("Undefined code logic for token>> " + token);
        }
    }
}
