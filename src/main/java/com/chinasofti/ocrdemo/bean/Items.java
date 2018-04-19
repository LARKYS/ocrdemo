package com.chinasofti.ocrdemo.bean;


import java.io.Serializable;
import java.util.List;

public class Items implements Serializable{
    private ItemCoord itemcoord;
    private List<Words> words;
    private String itemstring;
    private String key;
    private int pageNum;
    private int sort;
    private String category;

    public ItemCoord getItemcoord() {
        return itemcoord;
    }

    public void setItemcoord(ItemCoord itemcoord) {
        this.itemcoord = itemcoord;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
