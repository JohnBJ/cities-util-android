package com.citiesutil.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.citiesutil.interfaces.VolleyResponseListener;
import com.citiesutil.model.City;
import com.citiesutil.model.Mall;
import com.citiesutil.model.Shop;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CitiesApiController {
    private RequestQueue requestQueue;
    private Context context;
    private ArrayList<City> cities;
    private final String URL = "https://www.mocky.io/v2/5b7e8bc03000005c0084c210";

    public CitiesApiController(Context context){
        this.context = context;
        initiateRequest();
    }

    private void initiateRequest(){
        requestQueue = Volley.newRequestQueue(context);
    }

    /**
     * Get a list of cities
     * @return list of cities retrieved from shared pref
     */
    public ArrayList<City> getListOfCitiesFromSharedPref(){
        return cities;
    }

    /**
     * Get a list of cities from an endpoint
     * @param volleyResponseListener to be used for implementation of callback methods for api call
     */
    public void getListOfCities(VolleyResponseListener volleyResponseListener){
        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,URL,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<City>>(){}.getType();
                try {
                    cities = gson.fromJson(response.getJSONArray("cities").toString(), type);
                    volleyResponseListener.onResponse(cities);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListener.onError(error.getMessage());
            }
        });

        // Add the request to the RequestQueue.
        requestQueue.add(jsonObjectRequest);
    };

    /**
     * Get a city using a city name
     * @param cityName name of the city to retrieve
     * @return City
     */
    public City getCity(String cityName){
        City city = null;
        for(City city1:cities){
            if(city1.getName().equalsIgnoreCase(cityName)){
                city = city1;
                break;
            }
        }
        return city;
    }

    /**
     * Get all malls in a city
     * @param cityName name of the city where we want to get the malls for
     * @return list of mall in a city
     */
    public ArrayList<Mall> getMallsInACity(String cityName){
        ArrayList<Mall> malls = new ArrayList<>();
        for(City city1:cities){
            if(city1.getName().equalsIgnoreCase(cityName)){
                malls = city1.getMalls();
                break;
            }
        }
        return malls;
    }

    /**
     * Get a specific mall in a city
     * @param cityName city to get a mall in
     * @param mallName name of the mall to get from the city
     * @return Mall
     */
    public Mall getMallInACity(String cityName, String mallName){
        Mall mall = null;
        for(City city:cities){
            if(city.getName().equalsIgnoreCase(cityName)){
                ArrayList<Mall> malls = city.getMalls();
                for(Mall mall1 : malls){
                    if(mall1.getName().equalsIgnoreCase(mallName)){
                        mall = mall1;
                        break;
                    }
                }
                break;
            }
        }
        return mall;
    }

    /**
     * Get a list of shops in a mall
     * @param cityName city to get the shops in
     * @param mallName mall in a city to get the shops in
     * @return list of shops
     */
    public ArrayList<Shop> getListShopsInAMall(String cityName, String mallName){
        ArrayList<Shop> shops = null;
        for(City city:cities){
            if(city.getName().equalsIgnoreCase(cityName)){
                ArrayList<Mall> malls = city.getMalls();
                for(Mall mall1 : malls){
                    if(mall1.getName().equalsIgnoreCase(mallName)){
                        shops = mall1.getShops();
                        break;
                    }
                }
                break;
            }
        }
        return shops;
    }

    /**
     * Get a list of shops in a city
     * @param cityName name of the city to get the shops in
     * @return list of all the shops in a city
     */
    public ArrayList<Shop> getListShopsInACity(String cityName){
        ArrayList<Shop> shops = new ArrayList<>();
        for(City city:cities){
            if(city.getName().equalsIgnoreCase(cityName)){
                ArrayList<Mall> malls = city.getMalls();
                for(Mall mall1 : malls){
                    shops.addAll(mall1.getShops());
                }
                break;
            }
        }
        return shops;
    }

    /**
     * Save data in shared prefs to enable offline mode
     */
    public void saveData(){
        SharedPreferences sharedPref = context.getSharedPreferences(
                "cities", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        editor.putString("cities",gson.toJson(cities));
        editor.apply();
    }

    /**
     * Get data from shared prefs
     */
    public void getLastValidData(){
        Gson gson = new Gson();
        Type type = new TypeToken<List<City>>(){}.getType();
        SharedPreferences sharedPref = context.getSharedPreferences("cities",Context.MODE_PRIVATE);
        cities = gson.fromJson(sharedPref.getString("cities",""), type);
    }
}
