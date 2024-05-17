package com.example.androidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItemList;
    private Context context;
    private OnQuantityChangedListener quantityChangedListener;

    public interface OnQuantityChangedListener {
        void onQuantityChanged();
        void onItemRemoved();

    }

    public CartAdapter(List<CartItem> cartItemList, Context context, OnQuantityChangedListener listener) {
        this.cartItemList = cartItemList;
        this.context = context;
        this.quantityChangedListener = listener;

    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItemList.get(position);
        holder.tvProductName.setText(item.getName());
        holder.tvProductQuantity.setText(String.valueOf(item.getQuantity()));
        holder.tvProductPrice.setText(String.format("$%.2f", item.getPrice()));
        holder.textViewQuantity.setText(String.valueOf(item.getQuantity()));

        holder.buttonIncreaseQuantity.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            notifyItemChanged(position);
            if (quantityChangedListener != null) quantityChangedListener.onQuantityChanged();

        });

        holder.buttonDecreaseQuantity.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                notifyItemChanged(position);
                if (quantityChangedListener != null) quantityChangedListener.onQuantityChanged();

            }
            else {
                // Remove the item when quantity reaches zero
                int indexToRemove = holder.getAdapterPosition();
                cartItemList.remove(indexToRemove);
                notifyItemRemoved(indexToRemove);
                if (quantityChangedListener != null) quantityChangedListener.onItemRemoved();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        public TextView tvProductName, tvProductQuantity, tvProductPrice;
        TextView textViewQuantity;
        Button buttonIncreaseQuantity, buttonDecreaseQuantity;

        public CartViewHolder(View view) {
            super(view);
            tvProductName = view.findViewById(R.id.tvProductName);
            tvProductQuantity = view.findViewById(R.id.tvProductQuantity);
            tvProductPrice = view.findViewById(R.id.tvProductPrice);
            textViewQuantity = view.findViewById(R.id.textViewQuantity);
            buttonIncreaseQuantity = view.findViewById(R.id.buttonIncreaseQuantity);
            buttonDecreaseQuantity = view.findViewById(R.id.buttonDecreaseQuantity);

        }
    }
}