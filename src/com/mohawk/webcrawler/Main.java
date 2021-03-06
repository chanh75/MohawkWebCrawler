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
package com.mohawk.webcrawler;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import com.mohawk.webcrawler.lang.ScriptContext;

/**
*
* @author cnguyen
*
*/
public class Main {

    /**
     * Object to parse and hold parameter values.
     *
     * @author cnguyen
     *
     */
    private static class ParamContext {

        private boolean debug;
        private String scriptName;
        private String constants;
        private String cacheDir;

        public ParamContext(String[] args) throws Exception {

            for (int i = 0; i < args.length; i++) {
                if ("-d".equals(args[i])) {  // no database inserts/updates
                    this.debug = true;
                }
                else if ("-p".equals(args[i])) { // batch number of scripts

                }
                else if ("-s".equals(args[i])) { // name of script
                    this.scriptName = args[++i];
                }
                else if ("-v".equals(args[i])) { // JSON list of constants/variables
                    this.constants = args[++i];
                }
                else if ("-c".equals(args[i])) {
                    this.cacheDir = args[++i];
                }
            }
        }
        public boolean debug() { return this.debug; }
        public String getScriptName() { return this.scriptName; }
        public String getConstants() { return this.constants; }
        public String getCacheDirectory() { return this.cacheDir; }
    }

    public static void main(String[] args) {

        try {
            ParamContext arg = new ParamContext(args);
            //ScriptContext.Config config = ;

            ScriptContext scriptContext = new ScriptContext(
                    (new ScriptContext.Config())
                        .setScriptFilename(arg.getScriptName())
                        .setEnvironmentVariables(arg.getConstants())
                        .setCacheDirectory(arg.getCacheDirectory())
                        .setDebug(arg.debug())
                        );

            String scriptFile = arg.getScriptName();


            // execute the script
            System.out.println("Executing script>> " + scriptFile);
            ScriptOutput out = (new ScriptCompiler())
                .compile(new File(scriptFile))
                .executeWithContext(scriptContext);

            // print dataset
            ArrayList<ScriptContext.DataSet> dataSet = /*scriptContext*/out.getDataSet();
            System.out.println("\nCommited:");
            for (ScriptContext.DataSet row : dataSet) {
                if (row.getValue().length == 1)
                    System.out.printf("row>> %s : %s\r\n", row.getLabel(), row.getValue()[0]);
                else
                    System.out.printf("row>> %s : %s\r\n", row.getLabel(), Arrays.toString(row.getValue()));
            }
            System.out.println("WebCrawler done!");
        }
        catch (Exception e) {
            // log error in the database
            e.printStackTrace();
        }

    }

}
