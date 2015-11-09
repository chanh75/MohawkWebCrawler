package com.mohawk.webcrawler.lang;


public interface BaseVerb {
	
	public enum ReturnType {
		VOID,
		BOOLEAN,
		INTEGER,
		DOUBLE,
		STRING
	}
	
	public int numOfParams();
	public ReturnType returnType();
	public Object run(ScriptContext scriptContext, Object ... params) throws Exception;
	
}
