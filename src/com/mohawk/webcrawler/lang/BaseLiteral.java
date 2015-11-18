package com.mohawk.webcrawler.lang;

public class BaseLiteral implements BaseToken {

    private Object value;

    public BaseLiteral(Object val) {
        this.value = val;
    }

    public Object getValue() {
        return this.value;
    }
}
