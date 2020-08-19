package qtc.project.pos_tablet.adapter.product;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import qtc.project.pos_tablet.R;

import qtc.project.pos_tablet.model.PackageInfoModel;

public class ProductInfoAdapter extends RecyclerView.Adapter<ProductInfoAdapter.ViewHolder> {

    ProductInfoAdapterListener listener;

    Context context;
    List<PackageInfoModel> list;

    private onRecyclerViewItemClickListener mItemClickListener;

    // if checkedPosition = -1, there is no default selection
    // if checkedPosition = 0, 1st item is selected by default
    private int checkedPosition = -1;


    public ProductInfoAdapter(Context context, List<PackageInfoModel> list) {
        this.context = context;
        this.list = list;
    }

    public interface ProductInfoAdapterListener {
        void onItemClick(PackageInfoModel model);
    }

    public void setListener(ProductInfoAdapterListener listener) {
        this.listener = listener;
    }


    public void setOnItemClickListener(onRecyclerViewItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface onRecyclerViewItemClickListener {
        void onItemClickListener(PackageInfoModel model);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_table_row_price_detail, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView createProductDate;
        TextView priceItem;
        TextView quantity;
        TextView hethang;
        ImageView ticked;
        LinearLayout layoutRow;
        LinearLayout layout_ticked;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            createProductDate = itemView.findViewById(R.id.createProductDate);
            priceItem = itemView.findViewById(R.id.priceItem);
            quantity = itemView.findViewById(R.id.quantity);
            hethang = itemView.findViewById(R.id.hethang);
            ticked = itemView.findViewById(R.id.ticked);
            layoutRow = itemView.findViewById(R.id.layoutRow);
            layout_ticked = itemView.findViewById(R.id.layout_ticked);
        }

        void bind(final PackageInfoModel item) {
            if (checkedPosition == -1) {
                ticked.setVisibility(View.GONE);
            } else {
                if (checkedPosition == getAdapterPosition()) {
                    ticked.setVisibility(View.VISIBLE);

                } else {
                    ticked.setVisibility(View.GONE);
                }
            }
            if (Integer.parseInt(item.getQuantity_storage()) > 0) {
                createProductDate.setText(item.getManufacturing_date());
                String pattern = "###,###.###";
                DecimalFormat decimalFormat = new DecimalFormat(pattern);
                priceItem.setText(decimalFormat.format(Integer.parseInt(item.getSale_price())) + " đ");
                quantity.setText(item.getQuantity_storage());
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ticked.setVisibility(View.VISIBLE);
                        if (checkedPosition != getAdapterPosition()) {
                            notifyItemChanged(checkedPosition);
                            checkedPosition = getAdapterPosition();
                            if (mItemClickListener != null)
                                mItemClickListener.onItemClickListener(item);
                        }
                    }
                });
            } else {
                //layout_ticked.setVisibility(View.INVISIBLE);
                ticked.setVisibility(View.GONE);
                hethang.setVisibility(View.VISIBLE);
                hethang.setText("Hết hàng");
            }
        }
    }
}
