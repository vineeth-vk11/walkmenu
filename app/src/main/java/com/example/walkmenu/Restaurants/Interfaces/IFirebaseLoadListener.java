package com.example.walkmenu.Restaurants.Interfaces;

import com.example.walkmenu.Restaurants.Models.MenuModel;

import java.util.List;

public interface IFirebaseLoadListener
{
    void onFirebaseLoadSuccess(List<MenuModel> menuModelList);
    void onFirebaseLoadFailed(String message);
}
