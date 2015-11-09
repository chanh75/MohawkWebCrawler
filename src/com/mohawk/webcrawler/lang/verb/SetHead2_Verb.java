package com.mohawk.webcrawler.lang.verb;

import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.NotSetException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.Variable;
import com.mohawk.webcrawler.lang.BaseVerb.ReturnType;

public class SetHead2_Verb implements BaseVerb {

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
        Object p1Val = null;

        if (p1 instanceof Variable) {
            p1Val = ((Variable) p1).getValue();
        } else {
            p1Val = p1;
        }

        pageContext.setServiceHead2(String.valueOf(p1Val));
        return true;

        /*
        String param = (String) params[0];

        if (pageContext.getServiceHead1() == null) {
            throw new NotSetException("Head1 not set.  Must be set before setting Head2.");
        }

        if (LangCore.isStringLiteral(param)) {

            // string value
            pageContext.setServiceHead2(LangCore.makeStringLiteral(param));

        } else if (LangCore.isVerb((String) params[0])) {

            // execute verb

        } else if (pageContext.hasLocalVariable(param)) {

            pageContext.setServiceHead2((String) pageContext.getLocalVariable(param).getValue());

        } else {
            throw new LanguageException("Unable to resolve parameter>> " + param);
        }

        return null;
        */
    }
}
