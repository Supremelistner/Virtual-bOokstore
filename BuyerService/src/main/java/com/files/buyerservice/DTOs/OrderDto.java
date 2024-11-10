package com.files.buyerservice.DTOs;

import java.util.Date;

public class OrderDto {
    int id;
    Date date;
    long ChapterId;

    String Sellername;

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    String BuyerName;

Float price;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getChapterId() {
        return ChapterId;
    }

    public void setChapterId(long chapterId) {
        ChapterId = chapterId;
    }

    public String getSellername() {
        return Sellername;
    }

    public void setSellername(String sellername) {
        Sellername = sellername;
    }

    public String getBuyerName() {
        return BuyerName;
    }

    public void setBuyerName(String buyerName) {
        BuyerName = buyerName;
    }
}
