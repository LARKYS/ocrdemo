package com.chinasofti.ocrdemo.bean;

import java.util.List;

public class Regions {
    private String boundingBox;
    private List<Lines> lines;

    public String getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(String boundingBox) {
        this.boundingBox = boundingBox;
    }

    public List<Lines> getLines() {
        return lines;
    }

    public void setLines(List<Lines> lines) {
        this.lines = lines;
    }

}
