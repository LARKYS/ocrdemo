package com.chinasofti.ocrdemo.bean;
import java.util.List;

/**
 * Auto-generated: 2018-04-11 11:1:44
 *
 * @author jb51.net (i@jb51.net)
 * @website http://tools.jb51.net/code/json2javabean
 */
public class Projects {

    private String startDate;
    private String title;
    private String endDate;
    private String campanyName;
    private List<String> summary;
    public void setStartDate(String startDate) {
         this.startDate = startDate;
     }
     public String getStartDate() {
         return startDate;
     }

    public void setTitle(String title) {
         this.title = title;
     }
     public String getTitle() {
         return title;
     }

    public void setEndDate(String endDate) {
         this.endDate = endDate;
     }
     public String getEndDate() {
         return endDate;
     }

    public void setCampanyName(String campanyName) {
         this.campanyName = campanyName;
     }
     public String getCampanyName() {
         return campanyName;
     }

    public void setSummary(List<String> summary) {
         this.summary = summary;
     }
     public List<String> getSummary() {
         return summary;
     }

}