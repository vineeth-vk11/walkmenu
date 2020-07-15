package com.example.walkmenu.Restaurants.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.walkmenu.R;

public class MenuViewHolder extends RecyclerView.ViewHolder {
    public TextView categoryName;
    public RecyclerView categoryItemsRecyclerView;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);

        categoryName = itemView.findViewById(R.id.categoryName);
        categoryItemsRecyclerView = itemView.findViewById(R.id.categoryItemsRecyclerView);
    }
}
