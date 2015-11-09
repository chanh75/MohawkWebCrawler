package com.mohawk.webcrawler.lang.verb;

import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.Variable;

public class Commit_Verb implements BaseVerb {

    @Override
    public int numOfParams() {
        return 2;
    }

    @Override
    public ReturnType returnType() {
        return ReturnType.VOID;
    }

    @Override
    public Object run(ScriptContext scriptContext, Object ... params) throws Exception {

        if (!(params[0] instanceof String)) {
            throw new LanguageException("First parameter must be of string literal.");
        }

        String label = (String) LangCore.resolveParameter(scriptContext, params[0]);
        Object param = LangCore.resolveParameter(scriptContext, params[1]);
        Object value = null;

        if (param instanceof Variable) {
            value = ((Variable) param).getValue();
        } else {
            value = param;
        }

        scriptContext.commitData(label, value);
        return null;
    }
}
