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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.citiesutil.model.Mall;
import com.citiesutilexample.R;
import com.citiesutilexample.adapters.MallsRecyclerViewAdapter;
import com.citiesutilexample.interfaces.ClickListener;

import java.util.ArrayList;

public class ViewMallListDialogFragment extends DialogFragment{

    public static String TAG = "ViewMallListDialogFragment";
    RecyclerView mallsRecyclerView;
    TextView title;
    Button btnViewAllShops;
    private MallsRecyclerViewAdapter recyclerViewAdapter;

    public interface ViewMallListDialogFragmentListener {
        void onMallSelected(Mall selectedMall);
    }

    public interface ViewAllShopsButtonClickListener {
        void onViewAllShops();
    }

    public ViewMallListDialogFragment() {
    }

    public static ViewMallListDialogFragment newInstance(ArrayList<Mall> malls) {
        ViewMallListDialogFragment frag = new ViewMallListDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("malls", malls);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mallsdialogfragment_layout, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mallsRecyclerView = (RecyclerView) view.findViewById(R.id.listOfMalls);
        title = (TextView) view.findViewById(R.id.lblTitle);
        btnViewAllShops = (Button) view.findViewById(R.id.btnViewAllShops);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mallsRecyclerView.setLayoutManager(layoutManager);
        // Fetch arguments from bundle and set title
        ArrayList<Mall> listOfMalls = (ArrayList<Mall>) getArguments().getSerializable("malls");
        title.setText("Malls");

        recyclerViewAdapter = new MallsRecyclerViewAdapter(listOfMalls);
        recyclerViewAdapter.setOnItemClickListener(new ClickListener<Mall>() {
            @Override
            public void onItemClick(Mall mall) {
                ViewMallListDialogFragmentListener listener = (ViewMallListDialogFragmentListener) getActivity();
                listener.onMallSelected(mall);
                Toast.makeText(getActivity(), mall.getName(), Toast.LENGTH_SHORT).show();
                dismiss();
            }

        });
        btnViewAllShops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewAllShopsButtonClickListener listener = (ViewAllShopsButtonClickListener) getActivity();
                listener.onViewAllShops();
                dismiss();
            }
        });

        mallsRecyclerView.setAdapter(recyclerViewAdapter);

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
