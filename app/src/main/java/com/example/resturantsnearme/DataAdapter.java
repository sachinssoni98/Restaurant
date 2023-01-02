package com.example.resturantsnearme;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.resturantsnearme.model.Business;

import java.util.ArrayList;
//Adapter for rendering data in recyclerView
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    ArrayList<Business> businessArrayList;
    String status = "Currently OPEN";

    public DataAdapter(ArrayList<Business> businessArrayList) {
        this.businessArrayList = businessArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rows, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (businessArrayList.get(position).getIsClosed()) {
            status = "Currently CLOSED";
        }
        holder.rest_title.setText(businessArrayList.get(position).getName());
        holder.rest_dist.setText(String.format("%sm", (int) Math.round(businessArrayList.get(position).getDistance())));
        holder.rest_add.setText(businessArrayList.get(position).getLocation().getAddress1());
        holder.rest_status.setText(status);
        holder.rest_rating.setText(String.format("%s", businessArrayList.get(position).getRating()));
        Glide.with(holder.rest_image).load(businessArrayList.get(position).getImageUrl()).into(holder.rest_image);
        Log.d("Message", "Data loaded Successfully.");
    }

    @Override
    public int getItemCount() {
        return businessArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView rest_title, rest_dist, rest_add, rest_status, rest_rating;
        ImageView rest_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rest_title = itemView.findViewById(R.id.rest_title);
            rest_dist = itemView.findViewById(R.id.rest_dist);
            rest_add = itemView.findViewById(R.id.rest_add);
            rest_status = itemView.findViewById(R.id.rest_status);
            rest_rating = itemView.findViewById(R.id.rest_rating);
            rest_image = itemView.findViewById(R.id.rest_image);
        }
    }
}
