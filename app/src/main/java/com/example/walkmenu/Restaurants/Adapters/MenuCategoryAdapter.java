package com.example.walkmenu.Restaurants.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.walkmenu.R;
import com.example.walkmenu.Restaurants.Models.MenuCategoryModel;
import com.example.walkmenu.Restaurants.ViewHolders.MenuCategoryViewHolder;

import java.util.List;

public class MenuCategoryAdapter extends RecyclerView.Adapter<MenuCategoryViewHolder> {

    private Context context;
    private List<MenuCategoryModel> menuCategoryModelList;

    public MenuCategoryAdapter(Context context, List<MenuCategoryModel> menuCategoryModelList) {
        this.context = context;
        this.menuCategoryModelList = menuCategoryModelList;
    }

    @NonNull
    @Override
    public MenuCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_category,parent,false);
        return new MenuCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuCategoryViewHolder holder, int position) {
        holder.itemName.setText(menuCategoryModelList.get(position).getItemName());
        holder.itemDesc.setText(menuCategoryModelList.get(position).getItemDesc());
        holder.itemPrice.setText(menuCategoryModelList.get(position).getItemPrice());

    }

    @Override
    public int getItemCount() {
        return (menuCategoryModelList != null ? menuCategoryModelList.size(): 0);
    }
}
