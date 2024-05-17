package com.example.androidproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnQuantityChangedListener{
    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private ArrayList<CartItem> cartItems;
    private Button btnClearCart, btnCheckout;
    double totalPrice = 0;
    int totalItems = 0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartpage);

        recyclerView = findViewById(R.id.cartRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnClearCart = findViewById(R.id.btnClearCart);
        btnCheckout = findViewById(R.id.btnCheckout);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Cart Details");

        }


        cartItems = CartManager.getInstance().getCartItems();
        adapter = new CartAdapter(cartItems, this,this);
        recyclerView.setAdapter(adapter);

        btnClearCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the cart items
                CartManager.getInstance().clearCart();
                cartItems.clear();
                adapter.notifyDataSetChanged();
                updateCartTotal(); // Update the cart total text
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Proceed to CheckoutActivity
                if ((cartItems.size() !=0)){
                    Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(CartActivity.this, "Please add items to cart.!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        updateCartTotal();
    }
    private void updateCartTotal() {
        List<CartItem> items = cartItems;

        totalPrice=0;
        totalItems=0;
        for (CartItem item : items) {
            totalPrice += item.getPrice() * item.getQuantity();
            totalItems += item.getQuantity();
        }

        if(items.size() == 0){
            totalPrice = 0.00;
            totalItems = 0;
        }
        double taxAmount = totalPrice * 0.13; // Calculate 13% tax
        double grandTotal = totalPrice + taxAmount;

        String totalText = String.format("Total: %d items - $%.2f  (incl. tax)", totalItems, grandTotal);
        TextView tvCartTotal = findViewById(R.id.tvCartTotal);
        tvCartTotal.setText(totalText);
    }

    @Override
    public boolean onSupportNavigateUp() {
        // This method is called when the Up button is pressed. Just finish the activity.
        finish();
        return true;
    }

    @Override
    public void onQuantityChanged() {
        updateCartTotal();
    }

    @Override
    public void onItemRemoved() {
        updateCartTotal();
    }

}

