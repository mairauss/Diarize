package com.diarize;

/**
 * Created by y.baidiuk on 06/05/2017.
 * <p>
 * Item Bean
 */
public class Item {
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

