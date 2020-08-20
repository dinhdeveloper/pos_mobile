package qtc.project.pos_mobile.ui.views.fragment.customer.filter;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos_mobile.activity.HomeActivity;
import qtc.project.pos_mobile.model.CustomerModel;

public interface FragmentCustomerFilterViewInterface extends BaseViewInterface {

    void init(HomeActivity activity,FragmentCustomerFilterViewCallback callback);

    void initCustomer(ArrayList<CustomerModel> list);
}
