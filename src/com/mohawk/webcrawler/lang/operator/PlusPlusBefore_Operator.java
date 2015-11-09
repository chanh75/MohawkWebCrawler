package com.mohawk.webcrawler.lang.operator;

import com.mohawk.webcrawler.lang.BaseOperator;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.Variable;

public class PlusPlusBefore_Operator implements BaseOperator {

	@Override
	public int numOfParams() {
		return 2;
	}

	@Override
	public OperReturnType returnType() {
		return OperReturnType.VOID;
	}

	@Override
	public Object run(ScriptContext pageContext, Object... params)
			throws Exception {
		
		Object p = LangCore.resolveParameter(pageContext, params[0]);
		//Object pVal = (p instanceof Variable) ? ((Variable) p).getValue() : p;
		
		if (p instanceof Variable) {
			
			Variable pVar = (Variable) p;
			Object val = ((Variable) p).getValue();
			if (val instanceof Integer) {
				int intVal = ((Integer) val) + 1;
				pageContext.setLocalVariable(pVar.getName(), intVal);
				return intVal;
			}
		}
		
		throw new LanguageException("++ can only be performed on integer variables>> " + p);
	}
}
