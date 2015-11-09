package com.mohawk.webcrawler.lang.verb;

import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.HtmlUtils;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.NotSetException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.Variable;

public class GetTag_Verb implements BaseVerb {

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

        if (!(p1 instanceof Variable)) {
            throw new LanguageException("Variable expected as parameter>> " + params[0]);
        }
        Variable var = (Variable) p1;

        String html = pageContext.getDocumentHtml();
        int curPos = pageContext.getCursorPosition();
        //System.out.println("GetTag html>> " + html.substring(curPos, curPos + 30));

        if (!HtmlUtils.startsWithTag(html, curPos)) {
            throw new NotSetException("Current position in document is not a tag.");
        }

        String rawTagText = HtmlUtils.getTagBodyAtPosition(html, curPos);
        //System.out.println("GetTag raw>> " + rawTagText);

        pageContext.setLocalVariable(var.getName(), rawTagText);

        return null;
    }
}
