package com.example.botanic_park;

public class PlantBookItem {
    private String img_url;
    private String name_ko;
    private String name_en;

    public PlantBookItem(String img_url, String name_ko, String name_en) {
        this.img_url = img_url;
        this.name_ko = name_ko;
        this.name_en = name_en;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getName_ko() {
        return name_ko;
    }

    public String getName_en() {
        return name_en;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
