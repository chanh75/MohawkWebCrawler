package com.mohawk.webcrawler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import com.mohawk.webcrawler.lang.BaseConditionalVerb;
import com.mohawk.webcrawler.lang.BaseLoopVerb;
import com.mohawk.webcrawler.lang.BreakException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.verb.CommitService_Verb;
import com.mohawk.webcrawler.lang.verb.GetText_Verb;
import com.mohawk.webcrawler.lang.verb.GetUrl_Verb;
import com.mohawk.webcrawler.lang.verb.GoText_Verb;
import com.mohawk.webcrawler.lang.verb.If_Verb;
import com.mohawk.webcrawler.lang.verb.NextH3_Verb;
import com.mohawk.webcrawler.lang.verb.NextTableBody_Verb;
import com.mohawk.webcrawler.lang.verb.NextTableCol_Verb;
import com.mohawk.webcrawler.lang.verb.NextTable_Verb;
import com.mohawk.webcrawler.lang.verb.ResetHead1_Verb;
import com.mohawk.webcrawler.lang.verb.ResetHead2_Verb;
import com.mohawk.webcrawler.lang.verb.SetDesc_Verb;
import com.mohawk.webcrawler.lang.verb.SetHead1_Verb;
import com.mohawk.webcrawler.lang.verb.SetHead2_Verb;
import com.mohawk.webcrawler.lang.verb.SetPrice_Verb;
import com.mohawk.webcrawler.lang.verb.Var_Verb;
import com.mohawk.webcrawler.lang.verb.While_Verb;

public class RunScriptPrototype {

    public void Run(String filename) throws IOException, Exception {

        String fileContents = FileUtils.readFileToString(new File(filename));

        // reorganize the tokens into scopes
        LinkedList rootScope = compile(fileContents);

        ScriptContext pageCursor = new ScriptContext(new ScriptContext.Config());
        runProgram(pageCursor, rootScope);

        System.out.println("Services>> ");
        /*
        for (Service c : pageCursor.getServices()) {
            System.out.println("[" + c.getCategory1() + "][" + c.getDescription() + "][" + c.getPrice() + "]");
        }
        */
    }

    /**
     *
     * @param pageCursor
     * @param queue
     * @return
     * @throws IOException
     * @throws Exception
     */
    private static void runProgram(ScriptContext pageContext, LinkedList queue)
    throws IOException, Exception {

        int len = queue.size();

        for (int i = 0; i < len; i++) {

            Object token = queue.get(i);

            if (token instanceof BaseLoopVerb) {

                BaseLoopVerb loop = (BaseLoopVerb) token;
                loop.setPageContext(pageContext);

                try {
                    while (loop.shouldLoop()) {
                        runProgram(pageContext, loop.getScope());
                    }
                } catch (BreakException e) {
                }

            } else if (token instanceof BaseConditionalVerb) {

                BaseConditionalVerb conditionVerb = (BaseConditionalVerb) token;

                if (conditionVerb.shouldRunIf(pageContext)) {
                    //System.out.println("Running If with scope>> " + conditionVerb.getIfScope());
                    runProgram(pageContext, conditionVerb.getScope());
                } //else if (conditionVerb.hasElse()) {
                    //System.out.println("Running else with scope>> " + conditionVerb.getElseScope());
                    //runProgram(pageContext, conditionVerb.getElseScope());
                //}

            } else {

                String verb = (String) token;

                //switch (verb) {
                if ("debugon".equals(verb)) {

                    //System.out.println("debugon called.");

                } else if ("geturl".equals(verb)) {

                    (new GetUrl_Verb()).run(pageContext, queue.get(++i));

                } else if ("gotext".equals(verb)) {

                    (new GoText_Verb()).run(pageContext, queue.get(++i));

                } else if ("gettext".equals(verb)) {

                    (new GetText_Verb()).run(pageContext, queue.get(++i));

                } else if ("nexth3".equals(verb)) {

                    (new NextH3_Verb()).run(pageContext);

                } else if ("sethead1".equals(verb)) {

                    (new SetHead1_Verb()).run(pageContext, queue.get(++i));

                } else if ("resethead1".equals(verb)) {

                    (new ResetHead1_Verb()).run(pageContext);

                } else if ("sethead2".equals(verb)) {

                    (new SetHead2_Verb()).run(pageContext, queue.get(++i));

                } else if ("resethead2".equals(verb)) {

                    (new ResetHead2_Verb()).run(pageContext);

                } else if ("nexttable".equals(verb)) {

                    (new NextTable_Verb()).run(pageContext);

                } else if ("nexttablebody".equals(verb)) {

                    (new NextTableBody_Verb()).run(pageContext);

                } else if ("nextcol".equals(verb)) {

                    (new NextTableCol_Verb()).run(pageContext);

                } else if ("var".equals(verb)) {

                    (new Var_Verb()).run(pageContext, queue.get(++i));

                } else if ("setdesc".equals(verb)) {

                    (new SetDesc_Verb()).run(pageContext, queue.get(++i));

                } else if ("setprice".equals(verb)) {

                    (new SetPrice_Verb()).run(pageContext, queue.get(++i));

                } else if ("commitservice".equals(verb)) {

                    (new CommitService_Verb()).run(pageContext);

                } else if ("break".equals(verb)) {

                    throw new BreakException();

                } else {
                    throw new Exception("Unknown verb>> [" + verb + "]");
                }
            }
        }

        //return removedObjects;
    }

    private static LinkedList compile(String programText) {

        Pattern p = Pattern.compile("\"[^\"]+\"|\\([^\\)]+\\)|[^ \r\n]+");
        Matcher m = p.matcher(programText);

        ArrayList<String> tokensList = new ArrayList<String>();
        while (m.find()) {
            tokensList.add(m.group(0).trim());
        }

        Queue<String> tokensQueue = new LinkedList<String>();
        for (String token : tokensList) {
            if (token.trim().length() > 0) {
                tokensQueue.add(token);
            }
        }

        LinkedList rootScope = new LinkedList();
        addScope(tokensQueue, rootScope);

        return rootScope;
    }

    /**
     *
     * @param tokens
     * @param parentScope
     */
    private static void addScope(Queue<String> tokens, Queue parentScope) {

        Queue queue = new LinkedList();

        while (!tokens.isEmpty()) {

            Object token = tokens.poll();

            if ("end".equals(token)) {

                break;

            } else if ("else".equals(token)) {

                parentScope.add(token);
                break;

            } else if ("if".equals(token)) {

                String evaluation = tokens.poll();

                If_Verb ifVerb = new If_Verb();
                ifVerb.setExpression(evaluation);

                parentScope.add(ifVerb);
                addScope(tokens, ifVerb.createScope());

                // check if Else is defined
                LinkedList ifScope = (LinkedList) ifVerb.getScope();
                String elseToken = (String) ifScope.peekLast();

                if ("else".equals(elseToken)) {
                    ifScope.pollLast(); // remove else token
                    addScope(tokens, null); //ifVerb.createElseScope());
                }

            } else if ("while".equals(token) || "for".equals(token)) {

                String evaluation = tokens.poll();

                While_Verb whileVerb = new While_Verb();
                whileVerb.setExpression(evaluation);

                parentScope.add(whileVerb);
                addScope(tokens, whileVerb.createScope());

            } else {

                parentScope.add(token);
            }
        }
    }

    public static void main(String[] args) {

        String filename = "C:\\Users\\cnguyen\\Projects\\ProjectMohawk\\Scripts\\university_hospital_rainbow_children_cleveland.txt";

        try {
            RunScriptPrototype s = new RunScriptPrototype();
            s.Run(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
