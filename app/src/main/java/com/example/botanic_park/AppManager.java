package com.example.botanic_park;

import com.example.botanic_park.PlantSearch.PlantBookItem;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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


    private ArrayList<PlantBookItem> list = null;
    private ArrayList<PlantBookItem> plantsToday = null;

    public ArrayList<PlantBookItem> getList() {
        return list;
    }

    public void setList(ArrayList<PlantBookItem> list) {
        this.list = list;
    }

    public ArrayList<PlantBookItem> getPlantsToday() {
        return plantsToday;
    }

    public void setPlantsToday(ArrayList<PlantBookItem> plantsToday) {
        this.plantsToday = plantsToday;
    }

    private FloatingActionButton floatingActionButton;
    public void setMenuFloatingActionButton (FloatingActionButton floatingActionButton) {this.floatingActionButton = floatingActionButton;}
    public FloatingActionButton getMenuFloatingActionButton() { return floatingActionButton; }

    private MainActivity mainActivity;
    public void setMainActivity(MainActivity mainActivity) {this.mainActivity = mainActivity; }
    public MainActivity getMainActivity(){ return  mainActivity; }

}