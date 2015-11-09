package com.mohawk.webcrawler.lang.operator;

import com.mohawk.webcrawler.lang.BaseOperator;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.Variable;

public class NotEquals_Operator implements BaseOperator {

	@Override
	public int numOfParams() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public OperReturnType returnType() {
		// TODO Auto-generated method stub
		return OperReturnType.BOOLEAN;
	}

	@Override
	public Object run(ScriptContext pageContext, Object... params)
			throws Exception {
		
		System.out.println(params[0] + "<>" + params[1]);
		Object p1 = LangCore.resolveParameter(pageContext, params[0]);
		Object p2 = LangCore.resolveParameter(pageContext, params[1]);
		Object p1Val = null;
		Object p2Val = null;
		
		if (p1 instanceof Variable) {
			p1Val = ((Variable) p1).getValue();
		} else if (p1 instanceof String) {
			p1Val = (String) p1;
		} else if (p1 instanceof Integer) {
			p1Val = (Integer) p1;
		}
		
		if (p2 instanceof Variable) {
			p2Val = ((Variable) p2).getValue();
		} else if (p2 instanceof String) {
			p2Val = (String) p2;
		} else if (p2 instanceof Integer) {
			p2Val = (Integer) p2;
		}
		
		
		if (p1Val instanceof String && p2Val instanceof String) {
			return !((String) p1Val).equals((String) p2Val);
		} else if (p1Val instanceof Boolean && p2Val instanceof Boolean) {
			return ((Boolean) p1Val) != ((Boolean) p2Val);
		} else if (p1Val instanceof Integer && p2Val instanceof Integer) {
			return ((Integer) p1Val) != ((Integer) p2Val); 
		} else if (p1Val instanceof Double && p2Val instanceof Double) {
			return ((Double) p1Val) != ((Double) p2Val);
		}
		
		throw new LanguageException("Unable to determine != operation on params>> " + p1 + ":" + p2);
	}

}
