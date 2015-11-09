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

import java.util.ArrayList;

/**
 *
 * @author cnguyen
 *
 */
public class CommentRemover {

    private final static int NONE = 1;
    private final static int IN_STRING = 2;
    private final static int IN_BLOCK_COMMENT = 3;
    private final static int IN_LINE_COMMENT = 4;

    private int _state = NONE;


    /**
     * Removes comments within the line.  Loop this call for each line in a script file.
     *
     * @param line a line in a script or text file
     * @return
     */
    public String remove(String line) {

        if (line == null || line.length() == 0) {
            return line;
        }

        ArrayList<Integer> points = new ArrayList<Integer>();
        if (_state == IN_BLOCK_COMMENT) {
            points.add(0);
        }

        int len = line.length();

        for (int i = 0; i < len; i++) {
            char c = line.charAt(i);
            switch (c) {
                case '"':
                    if (_state != IN_BLOCK_COMMENT && i > 0 && line.charAt(i - 1) != '\\') { // not escaped double quote
                        _state = (_state == NONE) ? IN_STRING : NONE;
                    }
                    break;
                case '/':
                    if (_state == NONE) {
                        c = line.charAt(++i);
                        if (c == '/') {
                            points.add(i - 1);
                            _state = IN_LINE_COMMENT;
                        } else if (c == '*') {
                            points.add(i - 1);
                            _state = IN_BLOCK_COMMENT;
                        }
                    } else if (_state == IN_BLOCK_COMMENT) {
                        c = line.charAt(i - 1);
                        if (c == '*') {
                            points.add(i);
                            _state = NONE;
                        }
                    }
                    break;
            }
            if (_state == IN_LINE_COMMENT) {
                _state = NONE;
                break;
            }
        }

        if (points.size() % 2 != 0) {
            points.add(line.length());
        }

        StringBuffer sb = new StringBuffer(line);
        Integer[] p = points.toArray(new Integer[0]);

        int size = 0;
        for (int i = 0; i < p.length; i += 2) {
            int p1 = p[i];
            int p2 = p[i + 1] + 1;
            sb.delete(p1 - size, p2 - size);
            size += p2 - p1;
        }

        return sb.toString();
    }

    public static void main(String[] args) {

        //String s = "if (i < 0 /*|| j > i*/) then /*print \"this*/ is a go /*else if  // do nothing";
        String s1 = "var 1";
        String s2 = "//test again";
        String s3 = "//while this is a test";
        String s4 = "var 2";
        System.out.println("org>> " + s1 + ":" + s2 + ":" + s3);
        CommentRemover cr = new CommentRemover();

        System.out.println("final>> [" + cr.remove(s1) + ":" + cr.remove(s2) + ":" + cr.remove(s3) + ":" + cr.remove(s4) + "]");

    }
}
