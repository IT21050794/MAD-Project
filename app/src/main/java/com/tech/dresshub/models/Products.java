package com.tech.dresshub.models;

public class Products {

    String id;
    String imageURL;
    String type;
    String name;
    String matireal;
    String streachable;
    String size;
    String prize;

    public Products() {
    }

    public Products(String id,String imageURL, String type, String name, String matireal, String streachable, String size, String prize) {
        this.id = id;
        this.imageURL = imageURL;
        this.type = type;
        this.name = name;
        this.matireal = matireal;
        this.streachable = streachable;
        this.size = size;
        this.prize = prize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMatireal() {
        return matireal;
    }

    public void setMatireal(String matireal) {
        this.matireal = matireal;
    }

    public String getStreachable() {
        return streachable;
    }

    public void setStreachable(String streachable) {
        this.streachable = streachable;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }
}
