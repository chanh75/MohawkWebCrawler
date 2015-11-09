package com.mohawk.webcrawler.lang.verb;

import java.util.ArrayList;

import com.mohawk.webcrawler.lang.BaseConditionalVerb;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.ScriptContext;

public class If_Verb extends BaseConditionalVerb {

    private ArrayList<ElseIf_Verb> elseIfVerbs;
    private Else_Verb elseVerb;

    @Override
    public int numOfParams() {
        return 0;
    }

    @Override
    public ReturnType returnType() {
        return ReturnType.VOID;
    }

    @Override
    public Object run(ScriptContext pageContext, Object ... params) throws Exception {
        return true;
    }

    @Override
    public boolean shouldRunIf(ScriptContext pageContext) throws LanguageException {

        String eval = getExpression();
        boolean result = LangCore.evaluateExpression(pageContext, eval);

        return result;
    }

    public void addElseIf(ElseIf_Verb verb) {
        if (this.elseIfVerbs == null) {
            this.elseIfVerbs = new ArrayList<ElseIf_Verb>();
        }
        this.elseIfVerbs.add(verb);
    }

    public boolean hasElseIf() {
        return (this.elseIfVerbs != null && this.elseIfVerbs.size() > 0);
    }

    public ArrayList<ElseIf_Verb> getElseIfVerbs() {
        return this.elseIfVerbs;
    }

    public void setElse(Else_Verb verb) {
        this.elseVerb = verb;
    }

    public boolean hasElse() {
        return this.elseVerb != null;
    }

    public Else_Verb getElseVerb() {
        return this.elseVerb;
    }

    public String toString() {
        return super.getScope().toString();
    }
}
