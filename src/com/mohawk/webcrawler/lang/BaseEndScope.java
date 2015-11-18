package com.mohawk.webcrawler.lang;

public class BaseEndScope implements BaseToken {

    private String name;

    public BaseEndScope(String s) {
        this.name = s;
    }

    public String getName() {
        return this.name;
    }
}
