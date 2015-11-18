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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import com.mohawk.webcrawler.lang.BaseEndScope;
import com.mohawk.webcrawler.lang.BaseLiteral;
import com.mohawk.webcrawler.lang.BaseToken;
import com.mohawk.webcrawler.lang.BaseVariable;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.verb.ElseIf_Verb;
import com.mohawk.webcrawler.lang.verb.Else_Verb;
import com.mohawk.webcrawler.lang.verb.If_Verb;
import com.mohawk.webcrawler.lang.verb.While_Verb;

public class ScriptCompiler {

    /**
     * Takes a script file and creates a queue of string token and verb objects
     *
     * @param filename the full file name of the script
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static LinkedList<BaseToken> compile(String filename) throws IOException, Exception {

        List<String> lines = FileUtils.readLines(new File(filename), "UTF-8");
        StringBuffer fileContents = new StringBuffer();

        CommentRemover comment = new CommentRemover();

        for (String line : lines) {
            fileContents.append(comment.remove(line)).append(' ');
        }

        System.out.printf("fileContents %s", fileContents);

        // reorganize the tokens into scopes
        return compile0(fileContents.toString());

    }

    /**
     * Parses out each token in the program, and then calls addScope.
     *
     * @param programText
     * @return
     * @throws CompilationException
     */
    private static LinkedList<BaseToken> compile0(String programText)
    throws CompilationException {

        Pattern p = Pattern.compile("\"[^\"]+\"|\\([^\\)]+\\)|[^ \r\n]+");
        Matcher m = p.matcher(programText);

        ArrayList<String> tokensList = new ArrayList<String>();
        while (m.find())
            tokensList.add(m.group(0).trim());

        Queue<String> tokensQueue = new LinkedList<String>();
        for (String token : tokensList) {
            if (token.trim().length() > 0)
                tokensQueue.add(token);
        }

     // root scope, wil contain both String and BaseVerb objects
        LinkedList<BaseToken> rootScope = new LinkedList<BaseToken>();

        addScope(tokensQueue, rootScope);

        return rootScope;
    }

    /**
     *
     * @param tokens
     * @param parentScope
     */
    private static void addScope(Queue<String> tokens, Queue<? super BaseToken> parentScope)
    throws CompilationException {

        while (!tokens.isEmpty()) {

            String token = tokens.poll();
            if ("end".equals(token) || "else".equals(token) || "elseif".equals(token)) {
                parentScope.add(new BaseEndScope(token));
                break;
            }
            else if ("if".equals(token)) {
                String expression = tokens.poll();

                If_Verb ifVerb = new If_Verb();
                ifVerb.setExpression(expression);

                parentScope.add(ifVerb);
                addScope(tokens, ifVerb.createScope());

                // check if elseif or else is defined
                LinkedList ifScope = (LinkedList) ifVerb.getScope();
                Object elseToken = ifScope.peekLast();

                if (elseToken instanceof BaseEndScope) {
                    ifScope.pollLast(); // remove elseif or else from if scope

                    while (elseToken instanceof BaseEndScope) {

                        String elseStr = ((BaseEndScope) elseToken).getName();
                        if ("end".equals(elseStr))
                            break;
                        else if ("elseif".equals(elseStr)) {

                            String exp = tokens.poll();
                            ElseIf_Verb elseIfVerb = new ElseIf_Verb();
                            elseIfVerb.setExpression(exp);
                            ifVerb.addElseIf(elseIfVerb);

                            addScope(tokens, elseIfVerb.createScope());
                            elseToken = elseIfVerb.getScope().pollLast();
                        }
                        else if ("else".equals(elseStr)) {

                            Else_Verb elseVerb = new Else_Verb();
                            ifVerb.setElse(elseVerb);

                            addScope(tokens, elseVerb.createScope());
                            elseToken = elseVerb.getScope().pollLast();
                        }
                    }
                }
            }
            else if ("while".equals(token)) {

                String evaluation = tokens.poll();

                While_Verb whileVerb = new While_Verb();
                whileVerb.setExpression(evaluation);

                parentScope.add(whileVerb);
                addScope(tokens, whileVerb);

            }
            else if (LangCore.isVerb(token)) { // verb
                try {
                    parentScope.add(LangCore.createVerbObject((String) token));
                }
                catch (Exception e) {
                    e.printStackTrace();
                    throw new CompilationException(e.getLocalizedMessage());
                }
            }
            else if (LangCore.isLiteral(token)) {  // literal
                try {
                    parentScope.add(new BaseLiteral(LangCore.createLiteral(token)));
                }
                catch (LanguageException e) {
                    throw new CompilationException(e.getLocalizedMessage());
                }
            }
            else if (LangCore.isOperator(token)) { // operator
                try {
                    parentScope.add(LangCore.createOperatorObject(token));
                }
                catch (LanguageException e) {
                    throw new CompilationException(e.getLocalizedMessage());
                }
            }
            else // default to variable
                parentScope.add(new BaseVariable(token));
        }
    }
}
