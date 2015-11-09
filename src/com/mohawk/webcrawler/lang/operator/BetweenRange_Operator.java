package com.mohawk.webcrawler.lang.operator;

import com.mohawk.webcrawler.lang.BaseOperator;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.Variable;

public class BetweenRange_Operator implements BaseOperator {

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
		
		Object p1 = LangCore.resolveParameter(pageContext, params[0]);
		Object p2 = LangCore.resolveParameter(pageContext, params[1]);
		Object p1Val = null;
		//Object p2Val = null;
		
		if (p1 instanceof Variable) {
			p1Val = ((Variable) p1).getValue();
		} else if (p1 instanceof Integer) {
			p1Val = (Integer) p1;
		}
		/*
		if (p2 instanceof Variable) {
			p2Val = ((Variable) p2).getValue();
		} else if (p2 instanceof Integer) {
			p2Val = (Integer) p2;
		}
		*/
		if (p1Val instanceof String) {
			String strValue = (String) p1Val;
			Object[] set = (Object[]) p2;
			for (Object s : set) {
				if (strValue.indexOf((String) s) != -1) {
					return true;
				}
			}
			return false;
		} else if (p1Val instanceof Number) {
			int numValue = (Integer) p1Val;
			Object[] set = (Object[]) p2;
			return numValue >= (int) set[0] && numValue <= (int) set[1];
		}
		
		throw new LanguageException("Unable to determine in-between for '" + p1 + "' in range: " + p2);
	}

}
