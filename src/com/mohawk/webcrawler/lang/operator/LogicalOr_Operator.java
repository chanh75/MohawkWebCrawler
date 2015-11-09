package com.mohawk.webcrawler.lang.operator;

import com.mohawk.webcrawler.lang.BaseOperator;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.ScriptContext;

public class LogicalOr_Operator implements BaseOperator {

    @Override
    public int numOfParams() {
        return 2;
    }

    @Override
    public OperReturnType returnType() {
        return OperReturnType.BOOLEAN;
    }

    @Override
    public Object run(ScriptContext pageContext, Object... params) throws LanguageException {

        Object p1 = LangCore.resolveParameter(pageContext, params[0]);
        if (!(p1 instanceof Boolean)) {
            throw new LanguageException("First parameter must be of type Boolean>> " + p1);
        } else {
            Boolean p1Val = (Boolean) p1;
            if (p1Val) {
                return true;
            } else if (params.length > 1) {
                Object p2 = LangCore.resolveParameter(pageContext, params[1]);
                if (p2 instanceof Boolean) {
                    return p2;
                }
            }

        }

        return false;
    }
}
