package com.mohawk.webcrawler.lang.verb;

import java.util.ArrayList;

import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.Variable;

public abstract class CommitRow_Verb implements BaseVerb {

    //@Override
    //public int numOfParams() {
    //	return 2;
    //}

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

        int numParams = numOfParams();
        ArrayList<Object> values = new ArrayList<Object>();

        for (int i = 1; i < numParams; i++) {
            Object param = LangCore.resolveParameter(scriptContext, params[i]);
            Object value = null;
            if (param instanceof Variable) {
                value = ((Variable) param).getValue();
            } else {
                value = param;
            }
            values.add(value);
        }

        scriptContext.commitDataRow(label, values);
        return null;

    }
}
