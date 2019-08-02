package com.example.botanic_park;

import com.example.botanic_park.PlantSearch.PlantBookItem;

import java.util.ArrayList;

public class AppManager {
    private static AppManager instance = null;

    private AppManager() {
    }

    public static AppManager getInstance() {
        if (instance == null)
            instance = new AppManager();
        return instance;
    }


    private ArrayList<PlantBookItem> list;

    public ArrayList<PlantBookItem> getList() {
        return list;
    }

    public void setList(ArrayList<PlantBookItem> list) {
        this.list = list;
    }
}