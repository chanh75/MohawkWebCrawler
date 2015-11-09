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

import java.io.File;
import java.net.URL;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.LanguageException;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.ScriptContext.Config;

public class GetUrl_Verb implements BaseVerb {

    @Override
    public int numOfParams() {
        return 1;
    }

    @Override
    public ReturnType returnType() {
        return ReturnType.VOID;
    }

    @Override
    public Object run(ScriptContext pageContext, Object ... params) throws Exception {

        Object p1 = LangCore.resolveParameter(pageContext, params[0]);
        //System.out.println("GetUrl param>> " + p1);

        if (!(p1 instanceof String)) {
            throw new LanguageException("String literal required as parameter>> " + p1);
        }

        String url = (String) p1;
        Document doc = null;

        Config config = pageContext.getConfig();

        if (config.getCacheDirectory() != null) { // pull the HTML from cache

            String prefix = null; //pageContext.getConfig().getProviderId() + "_";
            Collection<File> files = FileUtils.listFiles(new File("C:\\Users\\cnguyen.MITEK\\Projects\\ProjectMohawk\\Scripts\\cache"), null, false);

            for (File file : files) {
                if (file.getName().startsWith(prefix)) {
                    doc = Jsoup.parse(file, "UTF-8");
                    break;
                }
            }

        } else {
            int MINS_2 = 2 * 60 * 1000;
            doc = (Document) Jsoup.parse(new URL(url), MINS_2);
        }

        // clear out any other contexts
        pageContext.setTableContext(null);

        pageContext.setDocument(doc);
        pageContext.setDocumentHtml(doc.html());
        pageContext.setCursorPosition(0);

        //System.out.println("doc length>> " + doc.html());

        return null;

    }
}
