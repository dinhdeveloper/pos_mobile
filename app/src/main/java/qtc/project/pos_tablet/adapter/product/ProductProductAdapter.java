package qtc.project.pos_tablet.adapter.product;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import b.laixuantam.myaarlibrary.dependency.AppProvider;
import b.laixuantam.myaarlibrary.widgets.superadapter.SuperAdapter;
import b.laixuantam.myaarlibrary.widgets.superadapter.SuperViewHolder;
import qtc.project.pos_tablet.R;
import qtc.project.pos_tablet.helper.Consts;
import qtc.project.pos_tablet.model.ProductModel;

public class ProductProductAdapter extends SuperAdapter<ProductModel> {

    ProductProductAdapterListener listener;
    boolean isChecked = true;
    public ProductProductAdapter(Context context, List<ProductModel> items) {
        super(context, items, R.layout.custom_item_product);
    }

    public interface ProductProductAdapterListener {
        void onItemClick(ProductModel model);

        void onRequestLoadMoreProduct();
    }

    public void setListener(ProductProductAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, ProductModel item) {
        ImageView imageProduct = holder.findViewById(R.id.imageProduct);
        ImageView checkedProduct = holder.findViewById(R.id.checkedProduct);
        TextView nameProduct = holder.findViewById(R.id.nameProduct);
        LinearLayout layoutHetHang = holder.findViewById(R.id.layoutHetHang);
        LinearLayout layoutConHang = holder.findViewById(R.id.layoutConHang);
        TextView priceProduct = holder.findViewById(R.id.priceProduct);
        TextView warehouseProduct = holder.findViewById(R.id.warehouseProduct);
        RelativeLayout item_product = holder.findViewById(R.id.item_product);

        if (!item.getListDataProduct().isEmpty() && Integer.valueOf(item.getTotal_stock())>0){
            layoutConHang.setVisibility(View.VISIBLE);
            layoutHetHang.setVisibility(View.GONE);
            AppProvider.getImageHelper().displayImage(Consts.HOST_API + item.getImage(), imageProduct, null, R.drawable.imageloading);
            nameProduct.setText(item.getName().toString());
            String pattern = "###,###.###";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);
            if (!item.getSale_price().isEmpty()){
                priceProduct.setText(decimalFormat.format(Integer.parseInt(item.getSale_price())) + " đ");
            }
            warehouseProduct.setText(decimalFormat.format(Integer.valueOf(item.getTotal_stock())));

            item_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isChecked) {
                        if (listener != null) {
                            listener.onItemClick(item);
                        }
                        //checkedProduct.setVisibility(View.VISIBLE);
                    } else {
                        if (listener != null) {
                            listener.onItemClick(item);
                        }
                        //checkedProduct.setVisibility(View.GONE);
                    }
                }
            });

        }
        else {
            AppProvider.getImageHelper().displayImage(Consts.HOST_API + item.getImage(), imageProduct, null, R.drawable.imageloading);
            nameProduct.setText(item.getName().toString());
            String pattern = "###,###.###";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);
            if (!item.getSale_price().isEmpty()){
                priceProduct.setText(decimalFormat.format(Integer.parseInt(item.getSale_price())) + " đ");
            }
            warehouseProduct.setText(decimalFormat.format(Integer.valueOf(item.getTotal_stock())));

            layoutConHang.setVisibility(View.GONE);
            layoutHetHang.setVisibility(View.VISIBLE);

            item_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isChecked) {
                        if (listener != null) {
                            listener.onItemClick(item);
                        }
                        //checkedProduct.setVisibility(View.VISIBLE);
                    } else {
                        if (listener != null) {
                            listener.onItemClick(item);
                        }
                        //checkedProduct.setVisibility(View.GONE);
                    }
                }
            });
        }
        if (layoutPosition == getCount() - 1) {
            if (listener != null)
                listener.onRequestLoadMoreProduct();
        }
    }
}
