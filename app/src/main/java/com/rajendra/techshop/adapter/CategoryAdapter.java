package com.rajendra.techshop.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rajendra.techshop.DTO.CATEGORY;
import com.rajendra.techshop.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    List<CATEGORY> categoryList;

    public CategoryAdapter(Context context, List<CATEGORY> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.category_row_items, parent, false);

        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        holder.categoryImage.setImageResource(categoryList.get(position).getImgUrl());
        holder.categoryText.setText(categoryList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public  static class CategoryViewHolder extends RecyclerView.ViewHolder{

        ImageView categoryImage;
        TextView categoryText;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryImage = itemView.findViewById(R.id.categoryImage);
            categoryText = itemView.findViewById(R.id.categoryText);

        }
    }

}

// lets import all the category images