package com.daklod.techshop.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.daklod.techshop.DTO.PRODUCT;
import com.daklod.techshop.ProductDetails;
import com.daklod.techshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartViewAdapter extends RecyclerView.Adapter<CartViewAdapter.CartViewHolder> {
    Context context;
    List<PRODUCT> CartList;
    int amount;

    public CartViewAdapter(Context context, List<PRODUCT> cartList, int amount) {
        this.context = context;
        this.CartList = cartList;
        this.amount = amount;
    }

    @NonNull
    @Override
    public CartViewAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_cart, parent, false);

        return new CartViewAdapter.CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewAdapter.CartViewHolder holder, int position) {
        holder.title.setText(CartList.get(position).getName());
        holder.price.setText(CartList.get(position).getPrice()+"");
        holder.totalPrice.setText((CartList.get(position).getPrice()*amount)+"");
        Picasso.get()
                .load("http://daklod-backend.vercel.app/image/" + CartList.get(position).getImg_url())
                .fit()
                .error(R.drawable.no_img)
                .into(holder.img);
        holder.img.setImageBitmap(CartList.get(position).getBitmap());
        holder.numberItem.setText(amount+"");

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount<=1) return;
                holder.numberItem.setText(String.valueOf(--amount));
            }
        });
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount > CartList.get(position).getAmount()) {
                    Toast.makeText(context,"Product quantity is not enough", Toast.LENGTH_SHORT).show();
                    return;
                }
                holder.numberItem.setText(String.valueOf(++amount));
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartList.remove(CartList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{
        ImageView img, delete;
        TextView title, price, totalPrice, minus, plus, numberItem;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imgOrdered);
            numberItem = itemView.findViewById(R.id.numberItem);
            delete = itemView.findViewById(R.id.imgDelete);
            title = itemView.findViewById(R.id.txtTitle);
            price = itemView.findViewById(R.id.EachItemPrice);
            totalPrice = itemView.findViewById(R.id.txtPriceTotal);
            minus = itemView.findViewById(R.id.minusItem);
            plus = itemView.findViewById(R.id.plusItem);
        }

    }
}
