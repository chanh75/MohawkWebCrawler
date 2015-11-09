package com.mohawk.webcrawler.lang;


public interface BaseOperator {

    public enum OperReturnType {
        VOID,
        BOOLEAN
    }

    public int numOfParams();
    public OperReturnType returnType();
    public Object run(ScriptContext scriptContext, Object ... params) throws Exception;

}
