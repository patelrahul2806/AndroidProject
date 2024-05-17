package com.example.androidproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;



public class DetailActivity extends AppCompatActivity {
    private int quantity = 1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView textViewQuantity = findViewById(R.id.textViewQuantity);
        Button buttonDecrease = findViewById(R.id.buttonDecrease);
        Button buttonIncrease = findViewById(R.id.buttonIncrease);


        if (getIntent()!=null){
            Product product = (Product) getIntent().getParcelableExtra("productInfo");

            ImageView productImg = findViewById(R.id.imageViewProduct);
            TextView textViewName = findViewById(R.id.textViewName);
            TextView textViewDescription = findViewById(R.id.textViewDescription);
            TextView textViewPrice = findViewById(R.id.textViewPrice);

            textViewName.setText(product.getName());
            textViewDescription.setText(product.getDescription());
            textViewPrice.setText(String.valueOf(product.getPrice()));
            if (product.getImageName()!= null) {
                byte[] decodedBytes = Base64.decode(product.getImageName(), Base64.DEFAULT);
                Log.d("bytes", "onBindViewHolder: "+decodedBytes.length);
                if(decodedBytes!=null)
                    productImg.setImageBitmap(ImageUtils.byteArrayToBitmap(decodedBytes));

            }


            Button addToCartBtn = findViewById(R.id.buttonAddToCart);
            addToCartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartItem item = new CartItem(product.getName(), quantity, product.getPrice());
                    CartManager.getInstance().addItemToCart(item);
                    Toast.makeText(DetailActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();

                }
            });
            Button goToCartBtn = findViewById(R.id.buttonGoToCart);
            goToCartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Intent intent = new Intent(DetailActivity.this,CartActivity.class);
                   startActivity(intent);
                }
            });

        }

        buttonDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1) {
                    quantity--;
                    textViewQuantity.setText(String.valueOf(quantity));
                }
            }
        });

        buttonIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                textViewQuantity.setText(String.valueOf(quantity));
            }
        });
    }
}
