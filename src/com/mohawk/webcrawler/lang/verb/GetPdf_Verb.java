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
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.mohawk.webcrawler.lang.BaseVerb;
import com.mohawk.webcrawler.lang.LangCore;
import com.mohawk.webcrawler.lang.ScriptContext;
import com.mohawk.webcrawler.lang.BaseVerb.ReturnType;
import com.mohawk.webcrawler.lang.ScriptContext.Config;

public class GetPdf_Verb extends BaseVerb {

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

        String dataDirectory  = System.getenv("OPENSHIFT_DATA_DIR");
        String cacheDirectory = null;

        if (dataDirectory != null)
            cacheDirectory = dataDirectory + "webcrawler/cache";
        else
            cacheDirectory = "C:\\Users\\cnguyen\\Projects\\ProjectMohawk\\Scripts\\cache";

        Object param = LangCore.resolveParameter(pageContext, params[0]);

        Document doc = null;
        Config config = pageContext.getConfig();

        if (config.getCacheDirectory() != null) { // pull the HTML from cache

            String prefix = null; //config.getProviderId() + "_";
            Collection<File> files = FileUtils.listFiles(new File(cacheDirectory), null, false);

            for (File file : files) {
                String filename = file.getName();
                if (file.getName().startsWith(prefix) && filename.endsWith(".html")) {
                    doc = (Document) Jsoup.parse(file, "UTF-8");
                    break;
                }
            }

            if (doc == null)
                throw new Exception("Unable to find cached file for script>> " + config.getScriptFilename());
        }
        else {
            // get it from the web
            System.out.println("URL>> " + param);
        }

        // clear out any other contexts
        pageContext.setTableContext(null);
        pageContext.setDocument(doc);
        pageContext.setDocumentHtml(doc.html());
        pageContext.setCursorPosition(0);

        return null;
    }
}
