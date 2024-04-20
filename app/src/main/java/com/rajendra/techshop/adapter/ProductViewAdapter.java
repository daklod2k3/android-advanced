package com.rajendra.techshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rajendra.techshop.DTO.PRODUCT;
import com.rajendra.techshop.ProductDetails;
import com.rajendra.techshop.R;
import com.squareup.picasso.Picasso;


import java.util.List;

public class ProductViewAdapter extends RecyclerView.Adapter<ProductViewAdapter.RecentlyViewedViewHolder> {

    Context context;
    List<PRODUCT> recentlyViewedList;

    public ProductViewAdapter(Context context, List<PRODUCT> recentlyViewedList) {
        this.context = context;
        this.recentlyViewedList = recentlyViewedList;
    }

    @NonNull
    @Override
    public RecentlyViewedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recently_viewed_items, parent, false);

        return new RecentlyViewedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentlyViewedViewHolder holder, final int position) {

        holder.name.setText(recentlyViewedList.get(position).getName());
//        holder.description.setText(recentlyViewedList.get(position).getDescription());
        holder.price.setText(String.valueOf(recentlyViewedList.get(position).getPrice()));
        holder.qty.setText(String.valueOf(recentlyViewedList.get(position).getAmount()));
//        holder.unit.setText(recentlyViewedList.get(position).get());
//        holder.bg.setBackgroundResource(R.drawable.no_img);
//        Log.d("img","http://localhost:3000/image/" + recentlyViewedList.get(position).getImg_url());
        try {
            Picasso.get()
                    .load("http://daklod-backend.vercel.app/image/" + recentlyViewedList.get(position).getImg_url())
                    .fit()
                    .error(R.drawable.no_img)
                    .into(holder.bg);

        }catch (Exception e){
            Log.d("img", e.toString());
        }
        holder.bg.setImageBitmap(recentlyViewedList.get(position).getBitmap());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(context, ProductDetails.class);

                i.putExtra("id", String.valueOf(recentlyViewedList.get(position).getProduct_id()));
//                i.putExtra("name", String.valueOf(recentlyViewedList.get(position).getName()));
//                i.putExtra("name", recentlyViewedList.get(position).getName());
//                i.putExtra("image", String.valueOf(recentlyViewedList.get(position).getImg_url()));
//
////                BitmapDrawable bitmapDrawable = ((BitmapDrawable) );
////                Bitmap bitmap = bitmapDrawable .getBitmap();
////                ByteArrayOutputStream stream = new ByteArrayOutputStream();
////                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
////                byte[] imageInByte = stream.toByteArray();
////                ByteArrayInputStream bis = new ByteArrayInputStream(imageInByte);
//
//                i.putExtra("price",String.valueOf(recentlyViewedList.get(position).getPrice()));
//                i.putExtra("desc",recentlyViewedList.get(position).getDescription());
//                i.putExtra("qty",recentlyViewedList.get(position).getAmount());
//                i.putExtra("unit",recentlyViewedList.get(position).getUnit());

                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return recentlyViewedList.size();
    }

    public  static class RecentlyViewedViewHolder extends RecyclerView.ViewHolder{

        TextView name, description, price, qty, unit;
        ImageView bg;

        public RecentlyViewedViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.product_name);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            qty = itemView.findViewById(R.id.qty);
            unit = itemView.findViewById(R.id.unit);
            bg = itemView.findViewById(R.id.product_img);

        }
    }

}
