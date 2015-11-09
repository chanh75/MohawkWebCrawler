package com.mohawk.webcrawler.lang.verb;

import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.Variable;

public class SetDesc_Verb implements BaseVerb {

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

        Object p1 = LangCore.resolveParameter(pageContext, params[0]);
        String desc = null;

        //System.out.println("SetDesc param>> " + param);
        if (p1 instanceof String) {
            desc = (String) p1;
        } else if (p1 instanceof Variable) {
            desc = String.valueOf(((Variable) p1).getValue());
        } else {
            throw new LanguageException("Invalid parameter for SetDesc verb>> " + p1);
        }

        //Object value = pageContext.getLocalVariable(param);
        //System.out.println("SetDesc value>> " + value);

        pageContext.setServiceDesc(desc);

        return null;
    }
}
