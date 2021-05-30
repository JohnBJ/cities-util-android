package com.citiesutil.interfaces;

import com.citiesutil.model.City;

import java.util.ArrayList;

public interface VolleyResponseListener {
    void onResponse(ArrayList<City> cities);
    void onError(String error);
}
