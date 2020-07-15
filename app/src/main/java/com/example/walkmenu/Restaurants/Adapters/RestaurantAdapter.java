package com.example.walkmenu.Restaurants.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.walkmenu.R;
import com.example.walkmenu.Restaurants.Models.RestaurantModel;
import com.example.walkmenu.Restaurants.RestaurantMenuFragment;
import com.example.walkmenu.Restaurants.ViewHolders.RestaurantViewHolder;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantViewHolder> {
    Context context;
    ArrayList<RestaurantModel> restaurantArrayList;
    String restaurantType;

    public RestaurantAdapter(Context context, ArrayList<RestaurantModel> restaurantArrayList, String restaurantType) {
        this.context = context;
        this.restaurantArrayList = restaurantArrayList;
        this.restaurantType = restaurantType;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_restaurant,parent,false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        holder.restaurantName.setText(restaurantArrayList.get(position).getRestaurantName());
        holder.restaurantDesc.setText(restaurantArrayList.get(position).getRestaurantDesc());

        if(!restaurantArrayList.get(position).getDeliveryStatus()){
            holder.deliveryChip.setVisibility(View.INVISIBLE);
        }
        else {
            holder.deliveryChip.setVisibility(View.VISIBLE);
        }

        if(!restaurantArrayList.get(position).getDineinStatus()){
            holder.dineinChip.setVisibility(View.INVISIBLE);
        }
        else {
            holder.dineinChip.setVisibility(View.VISIBLE);
        }

        if (!restaurantArrayList.get(position).getTakeawayStatus()){
            holder.takeawayChip.setVisibility(View.INVISIBLE);
        }
        else {
            holder.takeawayChip.setVisibility(View.VISIBLE);
        }

        holder.restaurantItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestaurantMenuFragment restaurantMenuFragment = new RestaurantMenuFragment();
                Bundle data = new Bundle();
                data.putString("restaurantName",restaurantArrayList.get(position).getRestaurantName());
                data.putString("restaurantDesc",restaurantArrayList.get(position).getRestaurantDesc());
                data.putString("restaurantNumber",restaurantArrayList.get(position).getRestaurantNumber());
                data.putString("restaurantType", restaurantType);
                restaurantMenuFragment.setArguments(data);

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, restaurantMenuFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantArrayList.size();
    }
}
