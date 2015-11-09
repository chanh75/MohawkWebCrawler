package com.mohawk.webcrawler.lang.verb;

import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.Variable;

public class SetPrice_Verb implements BaseVerb {

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
        //System.out.println("SetPrice param>> " + price);
        String price = null;

        if (p1 instanceof String) {

            price = (String) p1;

        } else if (p1 instanceof Variable) {

            price = String.valueOf(((Variable) p1).getValue());

        } else {
            throw new LanguageException("Invalid parameter for SetPrice verb>> " + p1);
        }

        //System.out.println("SetPrice>>[" + value + "]");
        pageContext.setServicePrice(price);

        return null;
    }
}
