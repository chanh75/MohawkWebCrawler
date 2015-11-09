package com.mohawk.webcrawler.lang.verb;

import org.jsoup.nodes.Element;

import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.ScriptContext;

public class GoDivOnId_Verb implements BaseVerb {

    @Override
    public int numOfParams() {
        return 1;
    }

    @Override
    public ReturnType returnType() {
        return ReturnType.BOOLEAN;
    }

    @Override
    public Object run(ScriptContext pageContext, Object... params)
            throws Exception {

        Object idValue = LangCore.resolveParameter(pageContext, params[0]);

        if (!(idValue instanceof String)) {
            throw new LanguageException("Parameter must resolve to a string literal");
        }

        String idString = (String) idValue;

        Element element = pageContext.getDocumnet().getElementById(idString);

        /*
        int index = pageContext.indexOfFromCurrent("<div>");
        if (index == -1) {
            return false;
        } else {

            String html = pageContext.getDocumentHtml();
            //System.out.println("html>> " + html.substring(index, index + 30));

            pageContext.setCursorPosition(index);
            return true;
        }
        */
        return true;
    }

}
