package qtc.project.pos_tablet.adapter.customer;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import b.laixuantam.myaarlibrary.widgets.superadapter.SuperAdapter;
import b.laixuantam.myaarlibrary.widgets.superadapter.SuperViewHolder;
import qtc.project.pos_tablet.R;

import qtc.project.pos_tablet.model.CustomerModel;

public class CustomerAdapter extends SuperAdapter<CustomerModel> {

    private CustomerAdapterListener listener;

    public CustomerAdapter(Context context, List<CustomerModel> items) {
        super(context, items, R.layout.custom_item_customer_profile);
    }
    public interface CustomerAdapterListener{
        void onItemClick(CustomerModel model);
    }

    public void setListener(CustomerAdapter.CustomerAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, CustomerModel item) {
        TextView nameCustomer = holder.findViewById(R.id.nameCustomer);
        TextView phoneCustomer = holder.findViewById(R.id.phoneCustomer);
        TextView addressCustomer = holder.findViewById(R.id.addressCustomer);
        LinearLayout btn_detail_Customer = holder.findViewById(R.id.btn_detail_Customer);
        LinearLayout layout_customer = holder.findViewById(R.id.layout_customer);


        nameCustomer.setText(item.getFull_name());
        phoneCustomer.setText(item.getPhone_number());
        addressCustomer.setText(item.getAddress());

        layout_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener !=null){
                    listener.onItemClick(item);
                }
            }
        });
    }
}
