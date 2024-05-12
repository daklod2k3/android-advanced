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

import com.daklod.techshop.DTO.INVOICE_DETAIL;
import com.daklod.techshop.DTO.PRODUCT;
import com.daklod.techshop.ProductDetails;
import com.daklod.techshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartViewAdapter extends RecyclerView.Adapter<CartViewAdapter.CartViewHolder> {
    Context context;
    List<PRODUCT> productList;
    List<INVOICE_DETAIL> invoiceDetail;
    int amount;

    public CartViewAdapter(Context context, List<PRODUCT> productList, List<INVOICE_DETAIL> invoiceDetail) {
        this.context = context;
        this.productList = productList;
        this.invoiceDetail = invoiceDetail;
    }

    @NonNull
    @Override
    public CartViewAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_cart, parent, false);

        return new CartViewAdapter.CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewAdapter.CartViewHolder holder, final int position) {
        for (INVOICE_DETAIL invoice : invoiceDetail) {
            for (PRODUCT product: productList) {
                if(invoice.getProduct_id() == product.getProduct_id()) {
                    holder.title.setText(product.getName());
                    holder.price.setText(product.getPrice()+"");
                    holder.totalPrice.setText((product.getPrice()*invoice.getAmount())+"");
                    Picasso.get()
                            .load("http://daklod-backend.vercel.app/image/" + product.getImg_url())
                            .fit()
                            .error(R.drawable.no_img)
                            .into(holder.img);
                    holder.img.setImageBitmap(product.getBitmap());
                    holder.numberItem.setText(invoice.getAmount()+"");

                    holder.minus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(invoice.getAmount()<=1) return;
                            invoice.setAmount(invoice.getAmount()-1);
                            holder.numberItem.setText(invoice.getAmount()+"");
                            holder.totalPrice.setText(invoice.getAmount()*product.getPrice()+"");
                        }
                    });
                    holder.plus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(invoice.getAmount() > product.getAmount()) {
                                Toast.makeText(context,"Product quantity is not enough", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            invoice.setAmount(invoice.getAmount()+1);
                            holder.numberItem.setText(String.valueOf(invoice.getAmount()));
                            holder.totalPrice.setText(invoice.getAmount()*product.getPrice()+"");
                        }
                    });
                    holder.delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            CartList.remove(CartList.get(position));
                        }
                    });
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return invoiceDetail.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder{
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
