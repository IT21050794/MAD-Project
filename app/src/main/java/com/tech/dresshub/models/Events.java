package com.tech.dresshub.models;

public class Events {

    String id;
    String image;
    String startDate;
    String endDate;
    String description;

    public Events() {
    }

    public Events(String id, String image, String startDate, String endDate, String description) {
        this.id = id;
        this.image = image;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
