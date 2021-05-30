package com.citiesutilexample.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.citiesutil.model.Mall;
import com.citiesutil.model.Shop;
import com.citiesutilexample.R;
import com.citiesutilexample.interfaces.ClickListener;

import java.util.List;

public class ShopsRecyclerViewAdapter extends RecyclerView.Adapter<ShopsRecyclerViewAdapter.MyViewHolder> {

    private List<Shop> shopList;
    private ClickListener<Shop> clickListener;

    public ShopsRecyclerViewAdapter(List<Shop> shopList) {
        this.shopList = shopList;
    }

    @NonNull
    @Override
    public ShopsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopsRecyclerViewAdapter.MyViewHolder holder, int position) {
        final Shop shop = shopList.get(position);

        holder.shop.setText(shop.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(shop);
            }
        });
    }

    public void setOnItemClickListener(ClickListener<Shop> cityClickListener) {
        this.clickListener = cityClickListener;
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView shop;
        private CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            shop = itemView.findViewById(R.id.lblName);
            cardView = itemView.findViewById(R.id.carView);
        }
    }
}
