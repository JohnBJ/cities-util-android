package com.citiesutilexample;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.citiesutil.controller.CitiesApiController;
import com.citiesutil.interfaces.VolleyResponseListener;
import com.citiesutil.model.City;
import com.citiesutil.model.Mall;
import com.citiesutil.model.Shop;
import com.citiesutilexample.adapters.CitiesRecyclerViewAdapter;
import com.citiesutilexample.fragments.ViewMallListDialogFragment;
import com.citiesutilexample.fragments.ViewShopListDialogFragment;
import com.citiesutilexample.interfaces.ClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        ViewMallListDialogFragment.ViewMallListDialogFragmentListener, ViewMallListDialogFragment.ViewAllShopsButtonClickListener {
    private static String TAG = "MainActivity";
    private CitiesApiController citiesApiController;
    private RecyclerView recyclerView;
    private CitiesRecyclerViewAdapter recyclerViewAdapter;
    private List<City> cityList;
    private City selectedCity;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait.");
        progressDialog.setCancelable(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.activity_title_bar);

        recyclerView = (RecyclerView)findViewById(R.id.listOfCities);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        citiesApiController = new CitiesApiController(this);
        if(checkInternetConnection()) {
            progressDialog.show();
            citiesApiController.getListOfCities(new VolleyResponseListener() {
                @Override
                public void onResponse(ArrayList<City> cities) {
                    progressDialog.dismiss();
                    cityList = cities;
                    citiesApiController.saveData();
                    recyclerViewAdapter = new CitiesRecyclerViewAdapter(cityList);
                    recyclerViewAdapter.setOnItemClickListener(new ClickListener<City>() {
                        @Override
                        public void onItemClick(City city) {
                            selectedCity = city;
                            FragmentManager fm = getSupportFragmentManager();
                            ViewMallListDialogFragment editNameDialogFragment = ViewMallListDialogFragment.newInstance(city.getMalls());
                            editNameDialogFragment.show(fm, "fragment_view_malls");
                            Toast.makeText(MainActivity.this, city.getName(), Toast.LENGTH_SHORT).show();
                        }

                    });

                    recyclerView.setAdapter(recyclerViewAdapter);
                }

                @Override
                public void onError(String error) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Something went wrong: " + error, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            citiesApiController.getLastValidData();
            cityList = citiesApiController.getListOfCitiesFromSharedPref();
            recyclerViewAdapter = new CitiesRecyclerViewAdapter(cityList);
            recyclerViewAdapter.setOnItemClickListener(new ClickListener<City>() {
                @Override
                public void onItemClick(City city) {
                    selectedCity = city;
                    FragmentManager fm = getSupportFragmentManager();
                    ViewMallListDialogFragment editNameDialogFragment = ViewMallListDialogFragment.newInstance(city.getMalls());
                    editNameDialogFragment.show(fm, "fragment_view_malls");
                    Toast.makeText(MainActivity.this, city.getName(), Toast.LENGTH_SHORT).show();
                }

            });
            recyclerView.setAdapter(recyclerViewAdapter);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onMallSelected(Mall selectedMall) {
        FragmentManager fm = getSupportFragmentManager();
        ViewShopListDialogFragment editNameDialogFragment = ViewShopListDialogFragment.newInstance(selectedMall.getShops());
        editNameDialogFragment.show(fm, "fragment_view_shops");
    }

    @Override
    public void onViewAllShops() {
        ArrayList<Shop> shops = citiesApiController.getListShopsInACity(selectedCity.getName());
        FragmentManager fm = getSupportFragmentManager();
        ViewShopListDialogFragment editNameDialogFragment = ViewShopListDialogFragment.newInstance(shops);
        editNameDialogFragment.show(fm, "fragment_view_shops");
    }

    private boolean checkInternetConnection(){
        ConnectivityManager connectivityManager =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }
}