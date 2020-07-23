package com.example.walkmenu.Restaurants;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.walkmenu.MainActivity;
import com.example.walkmenu.MainUi.OrdersFragment;
import com.example.walkmenu.R;
import com.example.walkmenu.Restaurants.Adapters.CartAdapter;
import com.example.walkmenu.Restaurants.Models.CartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class TakeAwayActivity extends AppCompatActivity implements PaymentResultListener {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    int totalItemCost, totalPackageFee, totalOffer, totalCost;

    Button timeSelector, dateSelector;

    TextView itemTotal,txtPackageFee, txtOffer, txtTotalCost;
    TextView txttime,txtdate;

    Button payment;

    ArrayList<CartModel> cartModelList = new ArrayList<>();
    String Uid;

    TextView txtRestaurantName, txtRestaurantDesc;
    String restaurantName, restaurantDesc, restaurantNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_take_away);

        Intent intent = getIntent();
        restaurantName = intent.getStringExtra("restaurantName");
        restaurantDesc = intent.getStringExtra("restaurantDesc");
        restaurantNumber = intent.getStringExtra("restaurantNumber");

        txtRestaurantName = findViewById(R.id.restaurantName);
        txtRestaurantDesc = findViewById(R.id.restaurantDesc);

        txtRestaurantName.setText(restaurantName);
        txtRestaurantDesc.setText(restaurantDesc);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Uid = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Carts").child(Uid).child("cart");

        recyclerView = findViewById(R.id.cartRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemTotal = findViewById(R.id.item_total);
        txtPackageFee = findViewById(R.id.package_fee);
        txtOffer = findViewById(R.id.discount);
        txtTotalCost = findViewById(R.id.total_amount);

        totalPackageFee = Integer.parseInt(txtPackageFee.getText().toString());
        totalOffer = Integer.parseInt(txtOffer.getText().toString());
        totalCost = Integer.parseInt(txtTotalCost.getText().toString());

        timeSelector = findViewById(R.id.button_for_time);
        dateSelector = findViewById(R.id.button_for_date);

        txttime = findViewById(R.id.time);
        txtdate = findViewById(R.id.date);

        timeSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTimeButton();
            }
        });

        dateSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDateButton();
            }
        });

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


        payment = findViewById(R.id.button_for_payment);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });

    }


    private void handleTimeButton(){
        Calendar calendar = Calendar.getInstance();
        int Hour = calendar.get(Calendar.HOUR);
        int Minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = hourOfDay + ":" + minute;
                txttime.setText(time);
            }
        },Hour,Minute,false);
        timePickerDialog.show();
    }

    private void handleDateButton(){

        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int date = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth+ "/" +  month + "/" + year;
                txtdate.setText(date);
            }
        }, year, month, date);

        datePickerDialog.show();
    }


    private void getFirebaseData() {
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

                CartAdapter cartAdapter = new CartAdapter(getApplicationContext(), cartModelList);
                totalItemCost = 0;
                for(int i=0; i<cartModelList.size();i++){
                    totalItemCost += Integer.parseInt(cartModelList.get(i).getItemprice()) * Integer.parseInt(cartModelList.get(i).getItemQuantity());
                }

                totalCost = totalItemCost + totalOffer + totalPackageFee;
                txtTotalCost.setText(String.valueOf(totalCost));
                itemTotal.setText(String.valueOf(totalItemCost));
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
        final Activity activity = this;

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
            options.put("description", "TakeAway Order");
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

        Log.i("Payment is successful","Order is being placed");
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();
        FirebaseDatabase db;
        db = FirebaseDatabase.getInstance();
        Map<String, Object> order = new HashMap<>();
        order.put("restaurantName",restaurantName);
        order.put("restaurantNumber",restaurantNumber);
        order.put("totalAmount",totalCost);
        order.put("timeOfDelivery",txttime.getText().toString());
        order.put("dateOfDelivery",txtdate.getText().toString());
        order.put("orderType","TakeAway");
        order.put("orderStatus","Waiting for confirmation");

        db.getReference().child("UserData").child(uid).child("CurrentOrders").child(restaurantNumber).setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                for(int i =0; i<cartModelList.size(); i++){
                    Map<String, Object> orderItem = new HashMap<>();
                    orderItem.clear();
                    orderItem.put("itemName",cartModelList.get(i).getItemName());
                    orderItem.put("itemQuantity",cartModelList.get(i).getItemQuantity());
                    db.getReference().child("UserData").child(uid).child("CurrentOrders").child(restaurantNumber).child("Items").push().setValue(orderItem).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            db.getReference().child("Carts").child(uid).removeValue();
                            Intent intent = new Intent(TakeAwayActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
        Log.i("Error",s);
    }
}
