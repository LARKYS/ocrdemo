package com.chinasofti.ocrdemo.bean;

import java.util.List;

public class Lines {
    private String boundingBox;
    private List<Words> words;
    private String itemstring;
    private int pageNume;
    private ItemCoord itemcoord;

    public String getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(String boundingBox) {
        this.boundingBox = boundingBox;
    }

    public List<Words> getWords() {
        return words;
    }

    public void setWords(List<Words> words) {
        this.words = words;
    }

    public String getItemstring() {
        return itemstring;
    }

    public void setItemstring(String itemstring) {
        this.itemstring = itemstring;
    }

    public int getPageNume() {
        return pageNume;
    }

    public void setPageNume(int pageNume) {
        this.pageNume = pageNume;
    }

    public ItemCoord getItemcoord() {
        return itemcoord;
    }

    public void setItemcoord(ItemCoord itemcoord) {
        this.itemcoord = itemcoord;
    }
}
