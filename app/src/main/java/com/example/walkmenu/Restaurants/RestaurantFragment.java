package com.example.walkmenu.Restaurants;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.walkmenu.R;
import com.example.walkmenu.Restaurants.Adapters.RestaurantAdapter;
import com.example.walkmenu.Restaurants.Models.RestaurantModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RestaurantFragment extends Fragment {

    FirebaseFirestore db;
    RecyclerView restaurantRecycler;
    View view;
    ArrayList<RestaurantModel> restaurantModelArrayList;
    RestaurantAdapter restaurantAdapter;
    String info;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_restaurant, container, false);

        Bundle bundle = this.getArguments();
        info = bundle.getString("info").trim();
        Log.i("Fragment is",info);

        restaurantModelArrayList = new ArrayList<>();
        setUpRecyclerView();
        loadDataFromFirebase();



        return view;
    }

    private void loadDataFromFirebase(){

        db = FirebaseFirestore.getInstance();
        db.collection(String.valueOf(info))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot querySnapshot: task.getResult()){
                            RestaurantModel restaurantModel = new RestaurantModel(querySnapshot.getString("restaurantName"),
                                    querySnapshot.getString("restaurantDesc"),
                                    querySnapshot.getBoolean("deliveryStatus"),
                                    querySnapshot.getBoolean("dineinStatus"),
                                    querySnapshot.getBoolean("takeawayStatus"),
                                    querySnapshot.getString("restaurantNumber"));
                            restaurantModelArrayList.add(restaurantModel);
                        }
                        restaurantAdapter = new RestaurantAdapter(getContext(),restaurantModelArrayList, info);
                        restaurantRecycler.setAdapter(restaurantAdapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),"Failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void setUpRecyclerView(){
        restaurantRecycler = view.findViewById(R.id.restaurant_recycler);
        restaurantRecycler.setHasFixedSize(true);
        restaurantRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}