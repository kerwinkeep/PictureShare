package com.kerwinkeep.pictureshare.bean;

import android.graphics.Bitmap;

public class Picture {

    String id;

    String userId;

    String likeNum;

    String title;

    Bitmap pictureData;

    String author;

    public Picture() {
    }

    public Picture(String id, String userId, String likeNum, String title, Bitmap pictureData, String author) {
        this.id = id;
        this.userId = userId;
        this.likeNum = likeNum;
        this.title = title;
        this.pictureData = pictureData;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(String likeNum) {
        this.likeNum = likeNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getPictureData() {
        return pictureData;
    }

    public void setPictureData(Bitmap pictureData) {
        this.pictureData = pictureData;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
