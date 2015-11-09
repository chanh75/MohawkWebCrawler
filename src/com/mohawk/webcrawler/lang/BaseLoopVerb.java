package com.mohawk.webcrawler.lang;

import java.util.LinkedList;
import java.util.Queue;

public abstract class BaseLoopVerb extends LinkedList implements BaseVerb {

	private String _expression = null;
	private ScriptContext _scriptContext = null;
	
	public void setExpression(String expression) {
		_expression = expression;
	}
	
	public String getExpression() {
		return _expression;
	}
	
	public void setPageContext(ScriptContext scriptContext) {
		_scriptContext = scriptContext;
	}
	
	public ScriptContext getPageContext() {
		return _scriptContext;
	}
	
	public abstract boolean shouldLoop() throws LanguageException;
	
}
