package com.example.polly.models;

import java.util.UUID;

public class Poll {

    int id = -1;
    String title;
    String desc;
    String locationString;
    Double latitude;
    Double longitude;
    Boolean status = null;
    int yes = 0;
    int no = 0;
    String userName;

    public Poll(String title, String desc, String locationString) {
        this.title = title;
        this.desc = desc;
        this.locationString = locationString;
    }

    public Poll(int id, String title, String desc, String locationString) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.locationString = locationString;
    }

    public Poll() {
    }

    public int getId() {
        return id;
    }

    public String getIdString(){
        return id+"";
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLocationString() {
        return locationString;
    }

    public void setLocationString(String locationString) {
        this.locationString = locationString;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Boolean isVoted() {
        return status;
    }

    public Boolean getStatus(){
        return status;
    }

    public void setIsVoted(Boolean status) {
        this.status = status;

        if (status){
            setYes(1);
            setNo(0);
        }
        else {
            setNo(1);
            setYes(0);
        }
    }

    public int getYes() {
        return yes;
    }

    public void setYes(int yes) {
        this.yes = yes;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
