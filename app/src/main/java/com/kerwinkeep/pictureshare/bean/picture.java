package com.kerwinkeep.pictureshare.bean;

import android.graphics.Bitmap;

public class picture {

    String id;

    String uId;

    String likeNum;

    String title;

    Bitmap picture;

    public picture() {
    }

    public picture(String id, String uId, String likeNum, String title, Bitmap picture) {
        this.id = id;
        this.uId = uId;
        this.likeNum = likeNum;
        this.title = title;
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
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

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }
}
