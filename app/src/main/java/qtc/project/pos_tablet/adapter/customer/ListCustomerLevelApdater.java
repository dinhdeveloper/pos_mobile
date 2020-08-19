package qtc.project.pos_tablet.adapter.customer;

import android.content.Context;
import android.widget.TextView;

import java.util.List;

import b.laixuantam.myaarlibrary.widgets.superadapter.SuperAdapter;
import b.laixuantam.myaarlibrary.widgets.superadapter.SuperViewHolder;
import qtc.project.pos_tablet.R;
import qtc.project.pos_tablet.model.CustomerModel;

public class ListCustomerLevelApdater extends SuperAdapter<CustomerModel> {

    public ListCustomerLevelApdater(Context context, List<CustomerModel> items) {
        super(context, items, R.layout.custom_item_customer_level);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, CustomerModel item) {
        TextView nameCustomer = holder.findViewById(R.id.nameCustomer);
        TextView addressCustomer = holder.findViewById(R.id.addressCustomer);
        TextView phoneCustomer = holder.findViewById(R.id.phoneCustomer);

        nameCustomer.setText(item.getFull_name());
        addressCustomer.setText(item.getAddress());
        phoneCustomer.setText(item.getPhone_number());
    }
}
