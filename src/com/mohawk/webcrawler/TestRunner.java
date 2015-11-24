package com.mohawk.webcrawler;

public class TestRunner {

    public static void main(String[] args) {

        String[] params = {
            "-d",
            "-s",
            "C:\\temp\\yahoo_finance_stocks.txt",
            //"c:\\temp\\test3.txt",
            "-v", "{SYMBOL:\"GOOG\"}"
        };

        Main.main(params);
    }
}
