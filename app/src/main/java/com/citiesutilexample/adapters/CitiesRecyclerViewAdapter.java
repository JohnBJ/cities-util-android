package com.citiesutilexample.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.citiesutil.model.City;
import com.citiesutilexample.R;
import com.citiesutilexample.interfaces.ClickListener;

import java.util.List;

public class CitiesRecyclerViewAdapter extends RecyclerView.Adapter<CitiesRecyclerViewAdapter.MyViewHolder> {

    private List<City> cityList;
    private ClickListener<City> clickListener;

    public CitiesRecyclerViewAdapter(List<City> cityList) {
        this.cityList = cityList;
    }

    @NonNull
    @Override
    public CitiesRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CitiesRecyclerViewAdapter.MyViewHolder holder, int position) {
        final City city = cityList.get(position);

        holder.city.setText(city.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(city);
            }
        });
    }

    public void setOnItemClickListener(ClickListener<City> cityClickListener) {
        this.clickListener = cityClickListener;
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView city;
        private CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            city = itemView.findViewById(R.id.lblName);
            cardView = itemView.findViewById(R.id.carView);
        }
    }
}
