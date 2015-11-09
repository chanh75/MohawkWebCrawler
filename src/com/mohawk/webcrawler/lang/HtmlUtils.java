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
package com.mohawk.webcrawler.lang;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

/**
 *
 * @author cnguyen
 *
 */
public class HtmlUtils {

    /**
     *
     * @param html
     * @param tag
     * @return
     */
    public static int indexOfStartTag(String html, String tag) {
        return indexOfStartTag(html, tag, 0);
    }

    /**
     *
     * @param html
     * @param regex
     * @param startFrom
     * @return
     */
    public static int indexOfStartTagWithRegex(String html, String regex, int startFrom) {
        String html0 = null;
        if (startFrom > 0) {
            html0 = html.substring(startFrom);
        } else {
            html0 = html;
        }

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(html0);
        if (m.find()) {
           return startFrom >= 0 ? startFrom + m.start() : m.start();
        } else {
            return -1;
        }
    }

    /**
     *
     * @param html
     * @param tag
     * @param startFrom
     * @return
     */
    public static int indexOfStartTag(String html, String tag, int startFrom) {

        String html0 = null;
        if (startFrom > 0) {
            html0 = html.substring(startFrom);
        } else {
            html0 = html;
        }

        final String REGEX = ".+|(?=^)";
        //final String REGEX = ".+?";
        StringBuilder sb = new StringBuilder(tag);
        sb.insert(sb.length() - 1, REGEX);

        Pattern p = Pattern.compile(sb.toString());
        Matcher m = p.matcher(html0);
        if (m.find()) {
           return startFrom > 0 ? startFrom + m.start() : m.start();
        } else {
            return -1;
        }
    }

    /**
     *
     * @param html
     * @param position
     * @return
     */
    public static boolean startsWithTag(String html, int position) {
        return html.charAt(position) == '<';
    }

    /**
     *
     * @param html
     * @param tag
     * @param position
     * @return
     */
    public static boolean startsWithTag(String html, String tag, int position) {
        String tag0 = tag.substring(0, tag.length() - 1) + ' ';
        int endPos = position + tag0.length();

        if (endPos <= html.length()) {
            if (html.charAt(endPos - 1) == '>') {
                return tag.equals(html.substring(position, endPos));
            } else {
                return tag0.equals(html.substring(position, endPos));
            }
        } else {
            return false;
        }
    }

    /**
     *
     * @param html
     * @param position
     * @return
     * @throws Exception
     */
    public static String getTagNameAtPosition(String html, int position) throws Exception {
        if (!startsWithTag(html, position)) {
            throw new Exception("String input does not start with HTML tag.");
        }

        int startTagE = html.indexOf('>', position + 1);
        String tagName = html.substring(position + 1, startTagE);
        if (tagName.indexOf(' ') != -1) {
            return tagName.substring(0,  tagName.indexOf(' '));
        } else {
            return tagName;
        }
    }

    /**
     *
     * @param html
     * @param position
     * @return
     * @throws Exception
     */
    public static String getTagBodyAtPosition(String html, int position) throws Exception {
        if (!startsWithTag(html, position)) {
            throw new Exception("String input does not start with HTML tag.");
        }

        int startTagE = html.indexOf('>', position + 1);
        String tagName = html.substring(position + 1, startTagE);
        if (tagName.indexOf(' ') != -1) {
            tagName = tagName.substring(0,  tagName.indexOf(' '));
        }

        StringBuffer sb = new StringBuffer();
        sb.append("</").append(tagName).append(">");
        String endTag = sb.toString();

        int endTagS = html.indexOf(endTag, startTagE + 1);

        return html.substring(position, endTagS + endTag.length());
    }

    /**
     *
     * @param html
     * @param position
     * @return
     */
    public static String getTextAtPosition(String html, int position) {
        int end = html.indexOf('<', position);
        return html.substring(position, end);
    }

    /**
     *
     * @param html
     * @return
     */
    public static String stripHtml(String html) {
        return Jsoup.parse(html).text().replace("\u00a0","");
    }
}
