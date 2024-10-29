package com.example.smartshop;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class ReceiptModel {

    private DatabaseReference databaseReference;

    // Constructor to initialize Firebase reference
    public ReceiptModel() {
        databaseReference = FirebaseDatabase.getInstance().getReference("cart");
    }

    // Method to fetch cart items from Firebase with a default list fallback
    public void fetchCartItems(final OnCartDataReceivedListener listener) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> cartItems = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    // Firebase data found, parse it
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String itemName = snapshot.child("itemName").getValue(String.class);
                        Integer quantity = snapshot.child("quantity").getValue(Integer.class);

                        if (itemName != null && quantity != null) {
                            cartItems.add(itemName + " - Quantity: " + quantity);
                        }
                    }
                } else {
                    // Data does not exist, use default items
                    cartItems = getDefaultCartItems();
                }
                listener.onDataReceived(cartItems);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Error accessing Firebase, provide default data
                listener.onDataReceived(getDefaultCartItems());
            }
        });
    }

    // Default cart items if Firebase is not connected or data is missing
    private List<String> getDefaultCartItems() {
        List<String> defaultItems = new ArrayList<>();
        defaultItems.add("Apple - Quantity: 2");
        defaultItems.add("Banana - Quantity: 5");
        defaultItems.add("Orange - Quantity: 3");
        return defaultItems;
    }

    // Interface for data retrieval callbacks
    public interface OnCartDataReceivedListener {
        void onDataReceived(List<String> cartItems);
        void onDataFetchError(Exception e);
    }
}
