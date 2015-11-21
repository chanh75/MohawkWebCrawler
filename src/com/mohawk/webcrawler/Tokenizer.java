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

import java.util.LinkedList;

/**
 *
 * @author cnguyen
 *
 */
public class Tokenizer {

    private enum State {
        NONE,
        STRING,
        EXPRESSION,
        BLOCK_COMMENT,
        INLINE_COMMENT,
        SET
    }

    private State _state = State.NONE;

    public LinkedList<String> tokenize(String s) {

        LinkedList<String> tokens = new LinkedList<>();
        StringBuffer sb = new StringBuffer();

        int len = s.length();
        for(int i = 0; i < len; i++) {
            char c = s.charAt(i);

            switch (_state) {
                case NONE:
                    if (c == ' ') {
                        if (sb.length() > 0) {
                            addAndReset(tokens, sb);
                        }
                    } else if (c == '\"') {
                        sb.append(c);
                        if (sb.length() == 1) {
                            _state = State.STRING;
                        }
                    } else if (c == '(') {
                        sb.append(c);
                        _state = State.EXPRESSION;
                    } else if (c == '[') {
                        sb.append(c);
                        _state = State.SET;
                    } else {
                        sb.append(c);
                        if (i == len - 1) {
                            addAndReset(tokens, sb);
                        }
                    }
                    break;
                case STRING:
                    if (c == '\"') {
                        sb.append(c);
                        addAndReset(tokens, sb);
                        _state = State.NONE;
                    } else {
                        sb.append(c);
                    }
                    break;
                case EXPRESSION:
                    if (c == ')') {
                        sb.append(c);
                        addAndReset(tokens, sb);
                        _state = State.NONE;
                    } else {
                        sb.append(c);
                    }
                    break;
                case SET:
                    if (c == ']') {
                        sb.append(c);
                        addAndReset(tokens, sb);
                        _state = State.NONE;
                    } else {
                        sb.append(c);
                    }
                    break;
                case BLOCK_COMMENT:
                    break;
                case INLINE_COMMENT:

            }
        }

        return tokens;
    }

    private void addAndReset(LinkedList<String> tokens, StringBuffer sb) {
        tokens.add(sb.toString());
        sb.delete(0, sb.length());
    }

    public static void main(String[] args) {

        String s1 = "nextsvgg";
        String s2 = "this is a test \"query keyboard all the\" (head1 == \"font-family\") ~has [\"a\",\"b\",\"c\"]";
        String s3 = "nextsvggf \"url(#clip{pageNum}-{cellNum})\"";
        Tokenizer t = new Tokenizer();
        System.out.println("output>> " + t.tokenize(s3));
    }
}
