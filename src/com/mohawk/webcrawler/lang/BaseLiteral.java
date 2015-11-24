package com.mohawk.webcrawler.lang;

public class BaseLiteral<T> extends BaseToken {

    private T value;

    public BaseLiteral(T val) {
        this.value = val;
    }

    public T getValue() {
        return this.value;
    }
}
