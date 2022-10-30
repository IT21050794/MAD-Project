package com.tech.dresshub.models;

public class Cart {

    String id;
    String imageURL;
    String type;
    String name;
    String qty;
    String size;
    String productPrice;
    String prize;
    String address;
    String contact;

    public Cart() {
    }

    public Cart(String id, String imageURL, String type, String name, String qty, String size, String productPrice, String prize, String address, String contact) {
        this.id = id;
        this.imageURL = imageURL;
        this.type = type;
        this.name = name;
        this.qty = qty;
        this.size = size;
        this.productPrice = productPrice;
        this.prize = prize;
        this.address = address;
        this.contact = contact;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
