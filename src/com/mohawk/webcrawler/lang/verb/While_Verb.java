package com.mohawk.webcrawler.lang.verb;

import com.mohawk.webcrawler.lang.BaseLoopVerb;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.BaseVerb.ReturnType;

public class While_Verb extends BaseLoopVerb {

    @Override
    public int numOfParams() {
        return 1;
    }

    @Override
    public ReturnType returnType() {
        return ReturnType.VOID;
    }

    @Override
    public boolean shouldLoop() throws LanguageException {

        String eval = getExpression();
        ScriptContext pageContext = getPageContext();
        //System.out.println("shouldLoop eval>> " + eval);

        boolean result = LangCore.evaluateExpression(pageContext, eval);
        //System.out.println("result>> " + result);

        return result;

    }

    public Object run(ScriptContext pageCursor, Object ... param) {

        return null;
    }
}
