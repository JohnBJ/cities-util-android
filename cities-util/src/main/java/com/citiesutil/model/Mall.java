package com.citiesutil.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Mall implements Serializable {
    private int id;
    private String name;
    private ArrayList<Shop> shops;

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

    public ArrayList<Shop> getShops() {
        return shops;
    }

    public void setShops(ArrayList<Shop> shops) {
        this.shops = shops;
    }
}
