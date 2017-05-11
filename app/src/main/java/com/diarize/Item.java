package com.diarize;

/**
 * Created by yskorge on 11/05/17.
 */



class Item{
    String text;
    String data;

    public Item() {
    }

    public Item(String text, String data) {
        this.text = text;
        this.data = data;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

