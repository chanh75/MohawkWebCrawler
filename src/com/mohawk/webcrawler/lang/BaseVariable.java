package com.mohawk.webcrawler.lang;

public class BaseVariable implements BaseToken {

    private String name;

    public BaseVariable(String s) {
        this.name = s;
    }

    public String getName() {
        return this.name;
    }
}
