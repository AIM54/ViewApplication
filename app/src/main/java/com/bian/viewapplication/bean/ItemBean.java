package com.bian.viewapplication.bean;

public class ItemBean {

    public String message;
    public String title;

    public ItemBean(String title,String message) {
        this.title = title;
        this.message=message;
    }

    public ItemBean(String message) {
        this.message = message;
    }
}
