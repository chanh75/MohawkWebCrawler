/**
 * Copyright 2015 Chanh Nguyen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mohawk.webcrawler.lang.verb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.Variable;

public class SvgXMax_Verb implements BaseVerb {

    @Override
    public int numOfParams() {
        return 1;
    }

    @Override
    public ReturnType returnType() {
        return ReturnType.DOUBLE;
    }

    @Override
    public Object run(ScriptContext pageContext, Object ... params) throws Exception {

        Object p1 = LangCore.resolveParameter(pageContext, params[0]);
        String html = null;

        if (p1 instanceof Variable)
            html = String.valueOf(((Variable) p1).getValue());
        else if (p1 instanceof String)
            html = (String) p1;

        // parse the x attribute
        final String REGEX = "x=\"(.+?)?\"";

        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(html);

        if (m.find()) {
            String xvalues = m.group(1);
            String[] array = xvalues.split(" ");
            //System.out.println("SvgXMax max value>> " + array[array.length - 1]);
            return new Double(array[array.length - 1]);
        }
        else
            return -1d;
    }
}
