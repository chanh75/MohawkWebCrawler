package com.mohawk.webcrawler.lang;

import java.util.LinkedList;


public abstract class BaseConditionalVerb implements BaseVerb {

    private String expression;
    private LinkedList scope;

    public abstract boolean shouldRunIf(ScriptContext scriptContext) throws LanguageException;

    public void setExpression(String eval) {
        this.expression = eval;
    }

    protected String getExpression() {
        return this.expression;
    }

    public LinkedList createScope() {
        this.scope = new LinkedList();
        return this.scope;
    }

    public LinkedList getScope() {
        return this.scope;
    }

}
