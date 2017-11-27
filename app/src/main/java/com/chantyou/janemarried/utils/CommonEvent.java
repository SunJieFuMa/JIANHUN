package com.chantyou.janemarried.utils;

public class CommonEvent {
    private int key;
    private String value;
    private int op;

    public CommonEvent(int key) {
        this.key = key;
    }

    public CommonEvent(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public CommonEvent(int key, String value, int op) {
        this.key = key;
        this.value = value;
        this.op = op;
    }


    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }
}
