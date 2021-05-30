package com.citiesutilexample.fragments;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.citiesutil.model.Mall;
import com.citiesutil.model.Shop;
import com.citiesutilexample.R;
import com.citiesutilexample.adapters.MallsRecyclerViewAdapter;
import com.citiesutilexample.adapters.ShopsRecyclerViewAdapter;
import com.citiesutilexample.interfaces.ClickListener;

import java.util.ArrayList;

public class ViewShopListDialogFragment extends DialogFragment {

    RecyclerView shopsRecyclerView;
    TextView title;
    private ShopsRecyclerViewAdapter recyclerViewAdapter;

    public ViewShopListDialogFragment() {
    }

    public static ViewShopListDialogFragment newInstance(ArrayList<Shop> shops) {
        ViewShopListDialogFragment frag = new ViewShopListDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("shops", shops);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shopsdialogfragment_layout, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        shopsRecyclerView = (RecyclerView) view.findViewById(R.id.listOfMalls);
        title = (TextView) view.findViewById(R.id.lblTitle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        shopsRecyclerView.setLayoutManager(layoutManager);
        // Fetch arguments from bundle and set title
        ArrayList<Shop> listOfMalls = (ArrayList<Shop>) getArguments().getSerializable("shops");
        title.setText("Shops");

        recyclerViewAdapter = new ShopsRecyclerViewAdapter(listOfMalls);
        recyclerViewAdapter.setOnItemClickListener(new ClickListener<Shop>() {
            @Override
            public void onItemClick(Shop shop) {
                Toast.makeText(getActivity(), shop.getName(), Toast.LENGTH_SHORT).show();
            }

        });

        shopsRecyclerView.setAdapter(recyclerViewAdapter);

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public void onResume() {
        // Store access variables for window and blank point
        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 75% of the screen width
        window.setLayout((int) (size.x * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
        super.onResume();
    }
}
