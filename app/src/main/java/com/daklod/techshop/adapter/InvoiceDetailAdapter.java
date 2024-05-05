package com.daklod.techshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daklod.techshop.R;
import com.daklod.techshop.model.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InvoiceDetailAdapter extends RecyclerView.Adapter<InvoiceDetailAdapter.ItemViewHolder> {

    private Context context;
    private List<Item> itemList;

    // Constructor của Adapter
    public InvoiceDetailAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    // Phương thức được gọi khi RecyclerView cần một ViewHolder mới
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout để tạo một ViewHolder mới
        View view = LayoutInflater.from(context).inflate(R.layout.invoice_item_detail, parent, false);
        return new ItemViewHolder(view);
    }

    // Phương thức được gọi để gắn dữ liệu vào ViewHolder để hiển thị trên màn hình
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);

        // Gắn các giá trị của Item vào các thành phần trong ViewHolder
        holder.txtProductName.setText(item.getNameProduct());
        holder.txtAmount.setText("x" + String.valueOf(item.getAmount()));
        holder.txtPrice.setText(String.format("%,.0f đ", item.getPrice())); // Format số tiền
        Picasso.get().load(item.getImgurl()).placeholder(R.drawable.b1).into(holder.imageView);
    }

    // Phương thức trả về số lượng item trong danh sách
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder để ánh xạ và lưu trữ các thành phần giao diện
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView txtProductName, txtAmount, txtPrice;
        ImageView imageView;

        // Constructor của ViewHolder
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ các thành phần từ layout invoice_item_detail
            txtProductName = itemView.findViewById(R.id.item_tenspchitiet);
            txtAmount = itemView.findViewById(R.id.item_soluongchitiet);
            txtPrice = itemView.findViewById(R.id.item_giachitiet);
            imageView = itemView.findViewById(R.id.item_imgchitiet);
        }
    }
}
