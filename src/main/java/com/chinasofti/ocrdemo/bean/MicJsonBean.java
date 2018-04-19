package com.chinasofti.ocrdemo.bean;

import java.util.List;

public class MicJsonBean {
    private String textAngle;
    private String orientation;
    private String language;
    private List<Regions> regions;

    public String getTextAngle() {
        return textAngle;
    }

    public void setTextAngle(String textAngle) {
        this.textAngle = textAngle;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<Regions> getRegions() {
        return regions;
    }

    public void setRegions(List<Regions> regions) {
        this.regions = regions;
    }
}
