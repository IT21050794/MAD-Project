package com.tech.dresshub.models;

public class CardDetails {

    String id;
    String owner;
    String cardNumber;
    String expireDate;
    String Csv;
    String bankName;

    public CardDetails() {
    }

    public CardDetails(String id, String owner, String cardNumber, String expireDate, String csv, String bankName) {
        this.id = id;
        this.owner = owner;
        this.cardNumber = cardNumber;
        this.expireDate = expireDate;
        this.Csv = csv;
        this.bankName = bankName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getCsv() {
        return Csv;
    }

    public void setCsv(String csv) {
        Csv = csv;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
