package com.mohawk.webcrawler.lang.verb;

import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.ScriptContext.SvgContext;

public class GoText_Verb implements BaseVerb {

    @Override
    public int numOfParams() {
        return 1;
    }

    @Override
    public ReturnType returnType() {
        return ReturnType.BOOLEAN;
    }

    @Override
    public Object run(ScriptContext pageContext, Object ... params) throws Exception {

        Object p1 = LangCore.resolveParameter(pageContext, params[0]);

        if (!(p1 instanceof String)) {
            throw new LanguageException("String literal parameter required for GoText verb>> " + p1);
        }

        String text = (String) p1;

        int i = -1;
        if (pageContext.getSvgContext() != null) {
            SvgContext svgContext = pageContext.getSvgContext();
            int foundPos = svgContext.indexOf(text);
            if (foundPos != -1) {
                svgContext.setCursorPosition(foundPos);
                i = svgContext.getPositionInDocument() + foundPos;
            }
        } else {
            i = pageContext.indexOfText(text);
        }

        if (i == -1) {
            throw new Exception("Unable to find text within html document>> [" + text + "]");
            //return false;
        } else {
            pageContext.setCursorPosition(i);
            return true;
        }
    }
}
