package com.mohawk.webcrawler.lang;

public class Variable {

    private String name;
    private Object value;

    public Variable(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public Object getValue() {
        return this.value;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("Variable:[name=");
        sb.append(this.name).append(", value=").append(this.value).append(']');
        return sb.toString();
    }
}
