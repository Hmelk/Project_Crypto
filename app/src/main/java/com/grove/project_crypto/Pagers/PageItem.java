package com.grove.project_crypto.Pagers;

public class PageItem {

    private String title;
    private String value;

    public PageItem(String title, String value){
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
