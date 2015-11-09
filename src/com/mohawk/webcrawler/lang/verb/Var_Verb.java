package com.mohawk.webcrawler.lang.verb;

import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.BaseVerb.ReturnType;

public class Var_Verb implements BaseVerb {

    @Override
    public int numOfParams() {
        return 1;
    }

    @Override
    public ReturnType returnType() {
        return ReturnType.VOID;
    }

    @Override
    public Object run(ScriptContext pageContext, Object ... params) throws Exception {

        String variableName = (String) params[0];
        if (LangCore.isVerb(variableName)) {
            throw new Exception("Variable name is a reserved verb.");
        }

        //System.out.println("Defining local variable>> " + variableName);
        pageContext.defineLocalVariable(variableName);

        return null;
    }
}
