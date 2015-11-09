package com.mohawk.webcrawler.lang.verb;

import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.ScriptContext;

public class VarDef_Verb implements BaseVerb {

    @Override
    public int numOfParams() {
        return 2;
    }

    @Override
    public ReturnType returnType() {
        return ReturnType.VOID;
    }

    @Override
    public Object run(ScriptContext pageContext, Object ... params) throws Exception {

        String varName = (String) params[0];
        if (LangCore.isVerb(varName)) {
            throw new LanguageException("Variable name cannot be a reserved verb.");
        }

        String value = (String) params[1];
        if (!LangCore.isLiteral(value)) {
            throw new LanguageException("Value must be a literal number or string>> " + value);
        }

        pageContext.defineLocalVariable(varName);
        pageContext.setLocalVariable(varName, LangCore.createLiteral(value));

        return null;
    }
}
