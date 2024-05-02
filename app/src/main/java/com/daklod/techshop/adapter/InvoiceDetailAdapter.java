import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rajendra.techshop.R;
import com.rajendra.techshop.adapter.InvoiceDetailAdapter;
import com.rajendra.techshop.model.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InvoiceDetailAdapter extends RecyclerView.Adapter<InvoiceDetailAdapter.InvoiceDetailViewHolder> {

    private Context context;
    private List<Item> itemList;

    public detail(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public InvoiceDetailAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_invoice_detail, parent, false);
        return new InvoiceDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceDetailViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.txtProductName.setText(item.getNameProduct());
        holder.txtQuantity.setText("Quantity: " + item.getAmount());
        holder.txtPrice.setText("Price: $" + item.getPrice());

        // Load hình ảnh sản phẩm bằng Picasso
        Picasso.get().load(item.getImgurl()).placeholder(R.drawable.b1).into(holder.imgProduct);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class InvoiceDetailViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtProductName, txtQuantity, txtPrice;

        public InvoiceDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.img_product);
            txtProductName = itemView.findViewById(R.id.txt_product_name);
            txtQuantity = itemView.findViewById(R.id.txt_quantity);
            txtPrice = itemView.findViewById(R.id.txt_price);
        }
    }
}