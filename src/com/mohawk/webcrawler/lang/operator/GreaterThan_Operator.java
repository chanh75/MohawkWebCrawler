package com.mohawk.webcrawler.lang.operator;

import com.mohawk.webcrawler.lang.BaseOperator;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.Variable;

public class GreaterThan_Operator implements BaseOperator {

	@Override
	public int numOfParams() {
		return 2;
	}

	@Override
	public OperReturnType returnType() {
		return OperReturnType.BOOLEAN;
	}

	@Override
	public Object run(ScriptContext pageContext, Object... params)
			throws Exception {
		
		Object p1 = LangCore.resolveParameter(pageContext, params[0]);
		Object p2 = LangCore.resolveParameter(pageContext, params[1]);
			
		Object p1Val = (p1 instanceof Variable) ? ((Variable) p1).getValue() : p1;
		Object p2Val = (p2 instanceof Variable) ? ((Variable) p2).getValue() : p2;
		
		
		if (p1Val instanceof Integer && p2Val instanceof Integer) {
			return (Integer) p1Val > (Integer) p2Val;
		} else if (p1Val instanceof Double && p2Val instanceof Double) {
			return (Double) p1Val > (Double) p2Val;
		} else if (p1Val instanceof String && p2Val instanceof String) {
			return ((String) p1Val).compareTo((String) p2Val) > 0;
		} else {
			throw new LanguageException("Unable to run greater than operator on values>> " + p1Val + ":" + p2Val);
		}
	}
}
