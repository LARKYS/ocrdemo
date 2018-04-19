package com.chinasofti.ocrdemo.bean;

import java.util.List;

public class Data {
    private String session_id;
    private List<Items> items;

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }
}
