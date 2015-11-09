package com.mohawk.webcrawler.lang.operator;

import com.mohawk.webcrawler.lang.BaseOperator;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.ScriptContext;

public class InvertBoolean_Operator implements BaseOperator {

	@Override
	public int numOfParams() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public OperReturnType returnType() {
		// TODO Auto-generated method stub
		return OperReturnType.BOOLEAN;
	}

	@Override
	public Object run(ScriptContext pageContext, Object... params)
			throws Exception {
		// TODO Auto-generated method stub
		
		Object p1 = params[0];
		//System.out.println("InvertBool p1>> " + p1);
		
		if (p1 instanceof Boolean) {
			return !(Boolean)p1;
		}
		
		throw new LanguageException("Unable to invert value>> " + p1);
	}

}
