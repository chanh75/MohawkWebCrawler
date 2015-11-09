package com.mohawk.webcrawler.lang.operator;

import com.mohawk.webcrawler.lang.BaseOperator;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.Variable;

public class Assign_Operator implements BaseOperator {

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

        //Object p1 = LangCore.resolveParameter(pageContext, params[0]);
        String varName = ((Variable) params[0]).getName();
        Object value = LangCore.resolveParameter(pageContext, params[1]);

        pageContext.setLocalVariable(varName, value);

        return null;
    }

}
