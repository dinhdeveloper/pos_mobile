package qtc.project.pos_mobile.ui.views.fragment.customer;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos_mobile.activity.HomeActivity;
import qtc.project.pos_mobile.model.CustomerModel;

public interface FragmentCustomerViewInterface extends BaseViewInterface {

    void init(HomeActivity activity,FragmentCustomerViewCallback callback);

    void initRecyclerViewCustomer(ArrayList<CustomerModel> list);
}
