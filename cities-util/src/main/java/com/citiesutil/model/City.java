package com.citiesutil.model;

import java.util.ArrayList;

public class City {
    private int id;
    private String name;
    private ArrayList<Mall> malls;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Mall> getMalls() {
        return malls;
    }

    public void setMalls(ArrayList<Mall> malls) {
        this.malls = malls;
    }
}
