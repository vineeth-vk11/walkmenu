package com.example.walkmenu.Restaurants.Models;

public class MenuCategoryModel {

    private String itemName;
    private String itemPrice;
    private String itemDesc;

    public MenuCategoryModel() {
    }

    public MenuCategoryModel(String itemName, String itemPrice, String itemDesc) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemDesc = itemDesc;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }
}
