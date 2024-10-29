package com.example.smartshop;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.test.core.app.ApplicationProvider;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ReceiptActivity extends AppCompatActivity {

    private TextView receiptTextView;
    public ReceiptModel receiptModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        receiptTextView = findViewById(R.id.receiptTextView);

        // Initialize ReceiptModel and fetch items
        receiptModel = new ReceiptModel();
        fetchAndDisplayCartItems();
    }

    public void fetchAndDisplayCartItems() {
        receiptModel.fetchCartItems(new ReceiptModel.OnCartDataReceivedListener() {
            @Override
            public void onDataReceived(List<String> cartItems) {
                StringBuilder receipt = new StringBuilder();
                for (String item : cartItems) {
                    receipt.append(item).append("\n");
                }
                receiptTextView.setText(receipt.toString());
            }

            @Override
            public void onDataFetchError(Exception e) {
                Toast.makeText(ReceiptActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
