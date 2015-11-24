package com.mohawk.webcrawler;

import java.util.ArrayList;

import com.mohawk.webcrawler.lang.ScriptContext.DataSet;

public class ScriptOutput {

    private ArrayList<DataSet> _dataSet;
    private String _consoleOutput;

    public ScriptOutput setDataSet(ArrayList<DataSet> list) {
        _dataSet = list;
        return this;
    }

    public ScriptOutput setConsoleOutput(String s) {
        _consoleOutput = s;
        return this;
    }

    public ArrayList<DataSet> getDataSet() {
        return _dataSet;
    }

    public String getConsoleOutput() {
        return _consoleOutput;
    }

    public void outputTo(StringBuffer output) {
        output.delete(0, output.length());
        output.append(_consoleOutput);
    }
}
