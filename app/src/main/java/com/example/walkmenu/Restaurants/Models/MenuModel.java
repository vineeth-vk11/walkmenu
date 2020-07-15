package com.example.walkmenu.Restaurants.Models;

import java.util.ArrayList;

public class MenuModel {

    private String categoryName;
    private ArrayList<MenuCategoryModel> categoryItems;

    public MenuModel() {
    }
        public MenuModel(String categoryName, ArrayList<MenuCategoryModel> categoryItems) {
        this.categoryName = categoryName;
        this.categoryItems = categoryItems;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ArrayList<MenuCategoryModel> getCategoryItems() {
        return categoryItems;
    }

    public void setCategoryItems(ArrayList<MenuCategoryModel> categoryItems) {
        this.categoryItems = categoryItems;
    }
}
