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

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.jsoup.nodes.Document;

/**
 * Class that holds configuration and execution context of the script.
 *
 * @author cnguyen
 *
 */
public class ScriptContext {

    /**
     *
     * @author cnguyen
     *
     */
    public static class Config {

        private boolean debug;
        private String scriptFilename;
        private String environmentVars;
        private String cacheDirectory;

        public boolean getDebug() {
            return this.debug;
        }
        public Config setDebug(boolean b) {
            this.debug = b;
            return this;
        }
        public String getScriptFilename() {
            return this.scriptFilename;
        }
        public Config setScriptFilename(String f) {
            this.scriptFilename = f;
            return this;
        }
        public String getEnvironmentVariables() {
            return this.environmentVars;
        }
        public Config setEnvironmentVariables(String s) {
            this.environmentVars = s;
            return this;
        }
        public String getCacheDirectory() {
            return this.cacheDirectory;
        }
        public Config setCacheDirectory(String d) {
            this.cacheDirectory = d;
            return this;
        }
    }

    /**
     *
     * @author cnguyen
     *
     */
    public static class DataSet {

        private String label;
        private Object value;
        private ArrayList<Object> row;

        public DataSet(String label, Object value, ArrayList<Object> row) {
            this.label = label;
            this.value = value;
            this.row = row;
        }

        public String getLabel() {
            return this.label;
        }

        public Object[] getValue() {
            if (this.value != null) {
                return new Object[] { value };
            } else if (this.row != null) {
                return this.row.toArray(new Object[0]);
            } else {
                return null;
            }
        }
    }

    /**
     *
     * @author cnguyen
     *
     */
    public static class TableContext {

        private String _tableHtml;
        private String _tableBodyHtml;
        private String _rowHtml;
        private int _tablePositionInDoc;
        private int _tableBodyPositionInTable;
        private int _startRowPositionInTable;
        private int _endRowPositionInTable;
        private int _columnPositionInRow;

        public TableContext(String html, int posInDoc) {
            setTableHtml(html);
            setTablePositionInDocument(posInDoc);
        }
        public String getTableHtml() {
            return _tableHtml;
        }
        public void setTableHtml(String _tableHtml) {
            this._tableHtml = _tableHtml;
        }
        public String getTableBodyHtml() {
            return _tableBodyHtml;
        }
        public void setTableBodyHtml(String _tableBodyHtml) {
            this._tableBodyHtml = _tableBodyHtml;
        }
        public String getRowHtml() {
            return _rowHtml;
        }
        public void setRowHtml(String _rowHtml) {
            this._rowHtml = _rowHtml;
        }
        public int getTablePositionInDocument() {
            return _tablePositionInDoc;
        }
        public void setTablePositionInDocument(int pos) {
            _tablePositionInDoc = pos;
        }
        public int getTableBodyPositionInTable() {
            return _tableBodyPositionInTable;
        }
        public void setTableBodyPositionInTable(int _cursorPosition) {
            this._tableBodyPositionInTable = _cursorPosition;
        }
        public int getStartRowPositionInTable() {
            return _startRowPositionInTable;
        }
        public void setStartRowPositionInTable(int _cursorPosition) {
            this._startRowPositionInTable = _cursorPosition;
        }
        public int getEndRowPositionInTable() {
            return _endRowPositionInTable;
        }
        public void setEndRowPositionInTable(int _cursorPosition) {
            this._endRowPositionInTable = _cursorPosition;
        }
        public int getColumnPositionInRow() {
            return _columnPositionInRow;
        }
        public void setColumnPositionInRow(int columnPosition) {
            this._columnPositionInRow = columnPosition;
        }
    }

    /**
     *
     * @author cnguyen
     *
     */
    public static class SvgContext {
        private String svgHtml;
        private int posInDoc;
        private int curPos = 0;

        private String gHtml;
        private int gPositionInSvg;

        private String textHtml;
        private int textPosInG;

        public SvgContext(String html, int posInDoc) {
            this.svgHtml = html;
            this.posInDoc = posInDoc;
        }
        public String getSvgHtml() {
            return this.svgHtml;
        }
        public int getPositionInDocument() {
            return this.posInDoc;
        }
        public int getCursorPosition() {
            return this.curPos;
        }
        public void setCursorPosition(int p) {
            this.curPos = p;
        }
        public void setGHtml(String gTagHtml) {
            this.gHtml = gTagHtml;
        }
        public String getGHtml() {
            return this.gHtml;
        }
        public void setGPositionInSvg(int pos) {
            this.gPositionInSvg = pos;
        }
        public int getGPositionInSvg() {
            return this.gPositionInSvg;
        }
        public String getTextHtml() {
            return this.textHtml;
        }
        public void setTextHtml(String text) {
            this.textHtml = text;
        }
        public int getTextPositionInG() {
            return this.textPosInG;
        }
        public int indexOf(String text) {
            return svgHtml.indexOf(text);
        }
    }

    private Config _config;
    private Document _document;
    private String _documentHtml;
    private int _cursorPosition;
    private TableContext _tableContext;
    private SvgContext _svgContext;
    private HashMap<String, Object> _localVariables = new HashMap<String, Object>();
    private StringBuffer _consoleOutput = new StringBuffer();

    private String _holdServiceHead1;
    private String _holdServiceHead2;
    private String _holdServiceDesc;
    private String _holdServicePrice;

    private ArrayList<DataSet> _dataSet = new ArrayList<DataSet>();

    public static ScriptContext createWithDefaultConfig() {
        return new ScriptContext(new Config());
    }

    public ScriptContext (Config config) {
        _config = config;

        // set the local variables from the config
        String jsonString = _config.getEnvironmentVariables();

        if (jsonString != null) {
            JSONObject jsonObj = new JSONObject(jsonString);
            for (String key : jsonObj.keySet()) {
                _localVariables.put(key, jsonObj.get(key));
            }
        }
    }

    public Config getConfig() {
        return _config;
    }

    private void checkDocument() throws NotSetException {
        if (_document == null || _documentHtml == null) {
            throw new NotSetException("Document not set.");
        }
    }

    public void setDocument(Document document) {
        _document = document;
    }

    public Document getDocumnet() {
        return _document;
    }

    public void setDocumentHtml(String html) {
        _documentHtml = html;
    }

    public String getDocumentHtml() {
        return _documentHtml;
    }

    public void setCursorPosition(int i) {
        _cursorPosition = i;
    }

    public int getCursorPosition() {
        return _cursorPosition;
    }

    public int indexOfText(String text) throws NotSetException {
        checkDocument();
        return _documentHtml.indexOf(text);
    }

    public int indexOfTextFromCurrent(String text) throws NotSetException {
        checkDocument();
        return _documentHtml.indexOf(text, _cursorPosition);
    }

    public int indexOfTag(String text) throws NotSetException {
        checkDocument();
        return HtmlUtils.indexOfStartTag(_documentHtml, text, 0);
    }

    public int indexOfTagFromCurrent(String text) throws NotSetException {
        checkDocument();
        return HtmlUtils.indexOfStartTag(_documentHtml, text, _cursorPosition);
    }

    public void setServiceHead1(String value) {
        _holdServiceHead1 = value.trim();
    }

    public String getServiceHead1() {
        return _holdServiceHead1;
    }

    public void resetServiceHead1() {
        _holdServiceHead1 = null;
    }

    public void setServiceHead2(String value) {
        _holdServiceHead2 = value.trim();
    }
    /*
    public String getHead2() {
        return _head2;
    }
    */
    public void resetServiceHead2() {
        _holdServiceHead2 = null;
    }

    public void setTableContext(TableContext table) {
        _tableContext = table;
    }

    public TableContext getTableContext() {
        return _tableContext;
    }

    public void setSvgContext(SvgContext g) {
        _svgContext = g;
    }

    public SvgContext getSvgContext() {
        return _svgContext;
    }

    public void defineLocalVariable(String name) {
        _localVariables.put(name, null);
    }

    public void setLocalVariable(String name, Object value) throws Exception {
        if (!_localVariables.containsKey(name)) {
            throw new Exception("Variable '" + name + "' has not been defined. Dump=[" + _localVariables + "]");
        }

        _localVariables.put(name, value);
    }

    public boolean hasLocalVariable(String name) {
        return _localVariables.containsKey(name);
    }

    public Variable getLocalVariable(String name) throws LanguageException {
        if (!_localVariables.containsKey(name)) {
            throw new LanguageException("Variable '" + name + "' undefined.");
        }

        return new Variable(name, _localVariables.get(name));
    }

    public void setServiceDesc(String desc) {
        _holdServiceDesc = desc.trim();
    }

    public String getServiceDesc() {
        return _holdServiceDesc;
    }

    public void setServicePrice(String price) {
        _holdServicePrice = price.trim();
    }

    public String getServicePrice() {
        return _holdServicePrice;
    }

    /*
    public void addService(String desc, String price) {
        _services.add(new Service(desc, price));
    }
    */
    public void commitData(String label, Object value) throws LanguageException {
        if (label == null || value == null || label.length() == 0) {
            throw new LanguageException("Label or value cannt be null or empty.");
        } else {
            _dataSet.add(new DataSet(label, value, null));
        }
    }

    public void commitDataRow(String label, ArrayList<Object> values) throws LanguageException {
        if (label == null || values == null || label.length() == 0) {
            throw new LanguageException("Label or values cannt be null or empty.");
        } else {
            _dataSet.add(new DataSet(label, null, values));
        }
    }

    public ArrayList<DataSet> getDataSet() {
        return _dataSet;
    }

    public void addConsoleOutput(String s) {
        _consoleOutput.append(s);
    }

    public String getConsoleOutput() {
        return _consoleOutput.toString();
    }
}
