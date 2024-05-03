package com.daklod.techshop.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.daklod.techshop.DTO.BANNER;
import com.daklod.techshop.R;
import com.daklod.techshop.controller.Api;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import android.view.LayoutInflater;
import android.widget.ImageView;


import java.util.List;

public class BannerViewAdapter extends SliderViewAdapter<BannerViewAdapter.SliderAdapterVH> {

    private Context context;
    private List<BANNER> mSliderItems;

    public BannerViewAdapter(List<BANNER> bannerList) {
        this.mSliderItems = bannerList;
    }

    public void renewItems(List<BANNER> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(BANNER sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_item_view, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        BANNER sliderItem = mSliderItems.get(position);
        Picasso.get()
                .load(Api.url + "/image/" + sliderItem.getImage_url())
                        .fit()
                                .into(viewHolder.imageViewBackground);


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;


        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.bannerImage);
            this.itemView = itemView;
        }
    }

}