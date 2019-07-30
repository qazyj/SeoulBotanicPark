package com.example.botanic_park.PlantSearch;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.io.Serializable;

/* 식물 정보를 긁어 와서 담는 모델  */
public class PlantBookItem implements Serializable {
    private String img_url;
    private String name_ko;     // 이름
    private String name_sc;     // 학명
    private String name_en;     // 영명
    private String type;        // 구분
    private String blossom;     // 개화기
    private String details;     // 상세설명

    public PlantBookItem(String img_url, String name_ko, String name_sc, String name_en, String type, String blossom, String details) {
        this.img_url = img_url;
        this.name_ko = name_ko;
        this.name_sc = name_sc;
        this.name_en = name_en;
        this.type = type;
        this.blossom = blossom;
        this.details = details;
    }


    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getName_ko() {
        return name_ko;
    }

    public void setName_ko(String name_ko) {
        this.name_ko = name_ko;
    }

    public String getName_sc() {
        return name_sc;
    }

    public void setName_sc(String name_sc) {
        this.name_sc = name_sc;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBlossom() {
        return blossom;
    }

    public void setBlossom(String blossom) {
        this.blossom = blossom;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
