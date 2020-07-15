package com.example.walkmenu.Restaurants.ViewHolders;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.walkmenu.R;
import com.google.android.material.chip.Chip;

public class RestaurantViewHolder extends RecyclerView.ViewHolder {

    public TextView restaurantName, restaurantDesc;
    public TextView deliveryChip, dineinChip, takeawayChip;
    public CardView restaurantItem;

    public RestaurantViewHolder(@NonNull View itemView) {
        super(itemView);

        restaurantName = itemView.findViewById(R.id.textView9);
        restaurantDesc = itemView.findViewById(R.id.textView10);
        deliveryChip = itemView.findViewById(R.id.chip6);
        dineinChip = itemView.findViewById(R.id.chip5);
        takeawayChip = itemView.findViewById(R.id.chip4);
        restaurantItem = itemView.findViewById(R.id.restaurantItem);
    }
}
