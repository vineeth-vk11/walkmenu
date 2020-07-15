package com.example.walkmenu.Restaurants;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.example.walkmenu.R;
import com.example.walkmenu.Restaurants.Adapters.MenuAdapter;
import com.example.walkmenu.Restaurants.Interfaces.IFirebaseLoadListener;
import com.example.walkmenu.Restaurants.Models.MenuCategoryModel;
import com.example.walkmenu.Restaurants.Models.MenuModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RestaurantMenuFragment extends Fragment implements IFirebaseLoadListener {

    IFirebaseLoadListener iFirebaseLoadListener;
    FirebaseFirestore db;
    ArrayList<MenuModel> menuModelArrayList;
    ArrayList<MenuCategoryModel> menuCategoryModelArrayList;
    DatabaseReference databaseReference;

    RecyclerView recyclerView;

    View view;

    String restaurantName, restaurantDesc, restaurantNumber, restaurantType;

    String categoryName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_restaurant_menu, container, false);

        Bundle bundle = this.getArguments();
        restaurantName = bundle.getString("restaurantName").trim();
        restaurantDesc = bundle.getString("restaurantDesc").trim();
        restaurantNumber = bundle.getString("restaurantNumber").trim();
        restaurantType = bundle.getString("restaurantType").trim();

        Log.i("restaurantName",restaurantName);
        Log.i("restaurantDesc",restaurantDesc);
        Log.i("restaurantNumber",restaurantNumber);
        Log.i("restaurantType",restaurantType);

        db = FirebaseFirestore.getInstance();

        menuModelArrayList = new ArrayList<>();
        menuCategoryModelArrayList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("RestaurantData");
        iFirebaseLoadListener =  this;

        recyclerView = view.findViewById(R.id.menuRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getFirestoreData();
        return view;
    }

//    private void getFirebaseData() {
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                List<MenuModel> menuModelList = new ArrayList<>();
//                for(DataSnapshot groupSnapshot:snapshot.getChildren()){
//                    MenuModel menuModel = new MenuModel();
//                    menuModel.setCategoryName(groupSnapshot.child(restaurantNumber).child("categoryName").getValue(true).toString());
//                    GenericTypeIndicator<ArrayList<MenuCategoryModel>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<MenuCategoryModel>>() {};
//                    menuModel.setCategoryItems(groupSnapshot.child(restaurantNumber).child("categoryItems").getValue(genericTypeIndicator));
//                    menuModelList.add(menuModel);
//                }
//
//                iFirebaseLoadListener.onFirebaseLoadSuccess(menuModelList);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    private void getFirestoreData(){
        db.collection(restaurantType).document(restaurantNumber).collection("Items")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(DocumentSnapshot querySnapshot: task.getResult()) {
                    categoryName = querySnapshot.getString("categoryName");
                    getCategoryItems();
                    Log.i("Size",String.valueOf(menuCategoryModelArrayList.size()));

                    MenuModel menuModel = new MenuModel(
                            querySnapshot.getString("categoryName"),
                            menuCategoryModelArrayList
                    );
                    menuModelArrayList.add(menuModel);

                    Log.i("Size before clear",String.valueOf(menuCategoryModelArrayList.size()));
                    menuCategoryModelArrayList.clear();
                    Log.i("Size after clear",String.valueOf(menuCategoryModelArrayList.size()));

                }
                MenuAdapter menuAdapter = new MenuAdapter(getContext(),menuModelArrayList);
                recyclerView.setAdapter(menuAdapter);
            }
        });
    }

    private void getCategoryItems(){
            db.collection(restaurantType).document(restaurantNumber).collection("Items").
                    document(categoryName).collection("categoryItems").get().
                    addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for(DocumentSnapshot querySnapshot1: task.getResult()){
                                MenuCategoryModel menuCategoryModel = new MenuCategoryModel(
                                        querySnapshot1.getString("itemName"),
                                        querySnapshot1.getString("itemPrice"),
                                        querySnapshot1.getString("itemDesc"));

                                Log.i("itemName",querySnapshot1.getString("itemName"));
                                Log.i("itemPrice",querySnapshot1.getString("itemPrice"));
                                Log.i("itemDesc",querySnapshot1.getString("itemDesc"));
                                menuCategoryModelArrayList.add(menuCategoryModel);
                            }
                        }
                    });
    }

    @Override
    public void onFirebaseLoadSuccess(List<MenuModel> menuModelList) {

    }

    @Override
    public void onFirebaseLoadFailed(String message) {

    }

}