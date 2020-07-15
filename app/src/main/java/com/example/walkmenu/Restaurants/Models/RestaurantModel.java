package com.example.walkmenu.Restaurants.Models;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

public class RestaurantModel {

    String restaurantName;
    String restaurantDesc;
    Boolean deliveryStatus;
    Boolean dineinStatus;
    Boolean takeawayStatus;
    String restaurantNumber;

    public RestaurantModel() {
    }

    public RestaurantModel(String restaurantName, String restaurantDesc, Boolean deliveryStatus, Boolean dineinStatus, Boolean takeawayStatus, String restaurantNumber) {
        this.restaurantName = restaurantName;
        this.restaurantDesc = restaurantDesc;
        this.deliveryStatus = deliveryStatus;
        this.dineinStatus = dineinStatus;
        this.takeawayStatus = takeawayStatus;
        this.restaurantNumber = restaurantNumber;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantDesc() {
        return restaurantDesc;
    }

    public void setRestaurantDesc(String restaurantDesc) {
        this.restaurantDesc = restaurantDesc;
    }

    public Boolean getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(Boolean deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Boolean getDineinStatus() {
        return dineinStatus;
    }

    public void setDineinStatus(Boolean dineinStatus) {
        this.dineinStatus = dineinStatus;
    }

    public Boolean getTakeawayStatus() {
        return takeawayStatus;
    }

    public void setTakeawayStatus(Boolean takeawayStatus) {
        this.takeawayStatus = takeawayStatus;
    }

    public String getRestaurantNumber() {
        return restaurantNumber;
    }

    public void setRestaurantNumber(String restaurantNumber) {
        this.restaurantNumber = restaurantNumber;
    }
}
