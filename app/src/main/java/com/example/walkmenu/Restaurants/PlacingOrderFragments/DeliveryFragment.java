package com.example.walkmenu.Restaurants;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.walkmenu.ProfileUi.ManageAddressFragment;
import com.example.walkmenu.R;
import com.example.walkmenu.Restaurants.Adapters.CartAdapter;
import com.example.walkmenu.Restaurants.Models.CartModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class DeliveryFragment extends Fragment implements PaymentResultListener {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;

    int totalItemCost, totalDeliveryCost, totalOffer, totalCost;
    TextView itemTotal, txtDeliveryCost, txtOffer, txtTotal;

    Button payment;

    ArrayList<CartModel> cartModelList = new ArrayList<>();
    String Uid;

    String address, apartmentNumber, landMark;
    Double latitude, longitude;

    Button addAddress;
    TextView txtDeliveryAddress;

    TextView txtRestaurantName, txtRestaurantDesc;
    String restaurantName, restaurantDesc, restaurantNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delivery, container, false);

        txtDeliveryAddress = view.findViewById(R.id.delivery_address);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            address = bundle.getString("address");
            apartmentNumber = bundle.getString("apartmentNumber");
            landMark = bundle.getString("landMark");
            latitude = bundle.getDouble("latitude");
            longitude = bundle.getDouble("longitude");
        }

        if(address != null){
            txtDeliveryAddress.setText(address);
        }

        Bundle bundle1 = this.getArguments();
        restaurantName = bundle1.getString("restaurantName");
        restaurantDesc = bundle1.getString("restaurantDesc");
        restaurantNumber = bundle1.getString("restaurantNumber");

        txtRestaurantName = view.findViewById(R.id.restaurantName);
        txtRestaurantDesc = view.findViewById(R.id.restaurantDesc);

        txtRestaurantName.setText(restaurantName);
        txtRestaurantDesc.setText(restaurantDesc);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Uid = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Carts").child(Uid).child("cart");

        recyclerView = view.findViewById(R.id.cartRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        itemTotal = view.findViewById(R.id.item_total);
        txtDeliveryCost = view.findViewById(R.id.delivery_fee);
        txtOffer = view.findViewById(R.id.discount);
        txtTotal = view.findViewById(R.id.total_amount);

        totalDeliveryCost = Integer.parseInt(txtDeliveryCost.getText().toString());
        totalOffer = Integer.parseInt(txtOffer.getText().toString());


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Carts").child(Uid).child("cart");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getFirebaseData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        payment = view.findViewById(R.id.button_for_payment);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });

        addAddress = view.findViewById(R.id.add_address);
        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManageAddressFragment manageAddressFragment = new ManageAddressFragment();

                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_frame,manageAddressFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    private void getFirebaseData () {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Carts").child(Uid).child("cart");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartModelList.clear();
                for (DataSnapshot groupSnapshot : snapshot.getChildren()) {
                    CartModel cartModel = new CartModel();
                    cartModel.setItemName(groupSnapshot.child("itemName").getValue(true).toString());
                    cartModel.setItemprice(groupSnapshot.child("itemprice").getValue(true).toString());
                    cartModel.setItemQuantity(groupSnapshot.child("itemQuantity").getValue(true).toString());
                    cartModelList.add(cartModel);

                    Log.i("name", groupSnapshot.child("itemName").getValue(true).toString());
                    Log.i("price", groupSnapshot.child("itemprice").getValue(true).toString());
                    Log.i("quantity", groupSnapshot.child("itemQuantity").getValue(true).toString());

                }

                CartAdapter cartAdapter = new CartAdapter(getContext(), cartModelList);
                totalItemCost = 0;
                for (int i = 0; i < cartModelList.size(); i++) {
                    totalItemCost += Integer.parseInt(cartModelList.get(i).getItemprice()) * Integer.parseInt(cartModelList.get(i).getItemQuantity());
                }
                itemTotal.setText(String.valueOf(totalItemCost));
                totalCost = totalItemCost + totalOffer + totalDeliveryCost;
                txtTotal.setText(String.valueOf(totalCost));
                recyclerView.setAdapter(cartAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void startPayment() {
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();

        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.logo1);

        /**
         * Reference to current activity
         */
        final Activity activity = getActivity();

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            /**
             * Merchant Name
             * eg: ACME Corp || HasGeek etc.
             */
            options.put("name", "WalkMenu");

            /**
             * Description can be anything
             * eg: Reference No. #123123 - This order number is passed by you for your internal reference. This is not the `razorpay_order_id`.
             *     Invoice Payment
             *     etc.
             */
            options.put("description", "Delivery Order");
            options.put("currency", "INR");

            /**
             * Amount is always passed in currency subunits
             * Eg: "500" = INR 5.00
             */
            options.put("amount", String.valueOf(totalCost*100));

            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {

    }

    @Override
    public void onPaymentError(int i, String s) {

    }
}