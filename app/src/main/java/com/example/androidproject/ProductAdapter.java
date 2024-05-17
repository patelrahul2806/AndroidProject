package com.example.androidproject;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    public static List<Product> productList;
    private static Context context;
    private OnItemClickListener listener;
    private AddToCartClickListener AddToCartClickListener;

    // Interface for click events
    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public interface AddToCartClickListener {
        void onAddToCartClick(Product product);
    }


    public ProductAdapter(List<Product> productList, Context context, OnItemClickListener listener, AddToCartClickListener listener1) {
        this.productList = productList;
        this.context = context;
        this.listener = listener;
        this.AddToCartClickListener = listener1;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view, listener, AddToCartClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewProduct;
        TextView textViewProductName;
        TextView textViewProductDescription;
        TextView textViewProductPrice;
        Button buttonAddToCart;
        RelativeLayout itemLayout;

        ProductViewHolder(@NonNull View itemView, final OnItemClickListener listener, AddToCartClickListener listener1) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageView);
            textViewProductName = itemView.findViewById(R.id.textViewName);
            textViewProductDescription = itemView.findViewById(R.id.textViewDescription);
            textViewProductPrice = itemView.findViewById(R.id.textViewPrice);
            buttonAddToCart = itemView.findViewById(R.id.addTOCartBtn);
            itemLayout = itemView.findViewById(R.id.itemLayout);

            // Set the click listener for the item layout
            itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        listener.onItemClick(productList.get(getAdapterPosition()));
                    }
                }
            });

            buttonAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Product product = productList.get(position);
                        product.setPrice(product.getPrice());
                        listener1.onAddToCartClick(product);
                    }
                }
            });
        }

        void bind(Product product) {
            textViewProductName.setText(product.getName());
            textViewProductDescription.setText(product.getDescription());
            textViewProductPrice.setText(String.valueOf(product.getPrice()));
            if (product.getImageName()!= null) {
                byte[] decodedBytes = Base64.decode(product.getImageName(), Base64.DEFAULT);
                Log.d("bytes", "onBindViewHolder: "+decodedBytes.length);
                if(decodedBytes!=null)
                   imageViewProduct.setImageBitmap(ImageUtils.byteArrayToBitmap(decodedBytes));

            }
        }
    }
}
