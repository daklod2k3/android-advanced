package com.daklod.techshop.adapter;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daklod.techshop.DTO.CATEGORY;
import com.daklod.techshop.R;
import com.daklod.techshop.ResultActivity;

import java.util.ArrayList;
import java.util.List;

public class ResultViewAdapter extends RecyclerView.Adapter<ResultViewAdapter.ResultViewHolder> {

    Context context;
    List<CATEGORY> categoryList;

    public ResultViewAdapter(Context context, List<CATEGORY> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_row_item_result, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        holder.categoryText.setText(categoryList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
    public class ResultViewHolder extends RecyclerView.ViewHolder{
        TextView categoryText;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryText = itemView.findViewById(R.id.resultItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Drawable[] drawables = categoryText.getCompoundDrawables();
                        Drawable leftDrawable = drawables[0];
                        if (leftDrawable != null) {
                            categoryText.setCompoundDrawablesWithIntrinsicBounds(null, null, drawables[2], null);
                            categoryText.setBackgroundResource(R.drawable.result_category);
                            if (ResultActivity.getListChecked().contains(position + 1)) {
                                ResultActivity.getListChecked().remove(Integer.valueOf(position + 1));
                            }
                            ResultActivity.checkedCategory();
                        } else {
                            categoryText.setBackgroundResource(R.drawable.result_category_bg);
                            categoryText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checked_category, 0, 0, 0);
                            ResultActivity.getListChecked().add(position + 1);
                            ResultActivity.checkedCategory();
                        }
                    }
                }
            });
        }
    }
}