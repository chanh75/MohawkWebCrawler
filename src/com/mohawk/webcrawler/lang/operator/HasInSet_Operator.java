package com.mohawk.webcrawler.lang.operator;

import com.mohawk.webcrawler.lang.BaseOperator;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.Variable;

public class HasInSet_Operator implements BaseOperator {

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
        Object p1Val = null;
        Object p2 = LangCore.resolveParameter(pageContext, params[1]);

        if (p1 instanceof Variable) {
            p1Val = ((Variable) p1).getValue();
        }

        //System.out.println("varValue>> " + params[0] + ":" + varValue);

        if (p1Val instanceof String) {
            String strValue = (String) p1Val; //System.out.println("hasInSet>> " + strValue);
            Object[] set = (Object[]) p2;
            for (Object s : set) {
                if (strValue.indexOf((String) s) != -1) {
                    return true;
                }
            }
        } else if (p1Val instanceof Number) {
            Number numValue = (Number) p1Val;
            Object[] set = (Object[]) p2;
            for (Object s : set) {
                if (numValue == ((Number) s)) {
                    return true;
                }
            }
        }

        return false;
    }

}
