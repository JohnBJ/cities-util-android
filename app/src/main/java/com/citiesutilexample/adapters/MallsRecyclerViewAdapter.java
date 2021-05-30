package com.citiesutilexample.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.citiesutil.model.Mall;
import com.citiesutilexample.R;
import com.citiesutilexample.interfaces.ClickListener;

import java.util.List;

public class MallsRecyclerViewAdapter extends RecyclerView.Adapter<MallsRecyclerViewAdapter.MyViewHolder> {

    private List<Mall> mallList;
    private ClickListener<Mall> clickListener;

    public MallsRecyclerViewAdapter(List<Mall> mallList) {
        this.mallList = mallList;
    }

    @NonNull
    @Override
    public MallsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MallsRecyclerViewAdapter.MyViewHolder holder, int position) {
        final Mall mall = mallList.get(position);

        holder.mall.setText(mall.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(mall);
            }
        });

    }

    public void setOnItemClickListener(ClickListener<Mall> cityClickListener) {
        this.clickListener = cityClickListener;
    }

    @Override
    public int getItemCount() {
        return mallList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mall;
        private CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mall = itemView.findViewById(R.id.lblName);
            cardView = itemView.findViewById(R.id.carView);
        }
    }
}
