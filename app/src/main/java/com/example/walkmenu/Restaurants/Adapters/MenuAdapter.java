package com.example.walkmenu.Restaurants.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.walkmenu.R;
import com.example.walkmenu.Restaurants.Models.MenuCategoryModel;
import com.example.walkmenu.Restaurants.Models.MenuModel;
import com.example.walkmenu.Restaurants.ViewHolders.MenuViewHolder;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder>{
    private Context context;
    private List<MenuModel> menuModelList;

    public MenuAdapter(Context context, List<MenuModel> menuModelList) {
        this.context = context;
        this.menuModelList = menuModelList;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_menu,parent,false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        holder.categoryName.setText(menuModelList.get(position).getCategoryName());
        List<MenuCategoryModel> menuCategoryModelList = menuModelList.get(position).getCategoryItems();
        MenuCategoryAdapter menuCategoryAdapter = new MenuCategoryAdapter(context,menuCategoryModelList);
        holder.categoryItemsRecyclerView.setHasFixedSize(true);
        holder.categoryItemsRecyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        holder.categoryItemsRecyclerView.setAdapter(menuCategoryAdapter);
        holder.categoryItemsRecyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public int getItemCount() {
        return (menuModelList != null ? menuModelList.size(): 0);
    }
}
