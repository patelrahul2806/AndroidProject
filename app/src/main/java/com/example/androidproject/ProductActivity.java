package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity implements ProductAdapter.OnItemClickListener,ProductAdapter.AddToCartClickListener{

    private RecyclerView recyclerViewProduct;
    private ProductAdapter productAdapter;
    private List<Product> productList= new ArrayList<Product>();

    private ProductDao productDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productpage);
        productDao= new ProductDao(this);
        recyclerViewProduct = findViewById(R.id.recyclerViewProduct);

        productList = productDao.getAllProducts();
        if(productList.size()>0){
        productAdapter = new ProductAdapter(productList, ProductActivity.this, ProductActivity.this, (ProductAdapter.AddToCartClickListener) ProductActivity.this);
        recyclerViewProduct.setLayoutManager(new LinearLayoutManager(ProductActivity.this));
        recyclerViewProduct.setAdapter(productAdapter);
        }
    }

    @Override
    public void onItemClick(Product product) {
        Intent detailIntent = new Intent(ProductActivity.this, DetailActivity.class);
        detailIntent.putExtra("productInfo", product);
        startActivity(detailIntent);
    }

    @Override
    public void onAddToCartClick(Product product) {
        CartItem item = new CartItem(product.getName(), 1, product.getPrice());
        CartManager.getInstance().addItemToCart(item);
        Toast.makeText(ProductActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();

    }

}