package com.example.walkmenu.Restaurants.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.walkmenu.R;

public class MenuCategoryViewHolder extends RecyclerView.ViewHolder {

    public TextView itemName;
    public TextView itemPrice;
    public TextView itemDesc;

    public MenuCategoryViewHolder(@NonNull View itemView) {
        super(itemView);

        itemName = itemView.findViewById(R.id.itemName);
        itemDesc = itemView.findViewById(R.id.itemDesc);
        itemPrice = itemView.findViewById(R.id.itemPrice);
    }
}
