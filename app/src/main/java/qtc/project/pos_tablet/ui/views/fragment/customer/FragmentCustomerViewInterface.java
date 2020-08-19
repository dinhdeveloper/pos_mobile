package qtc.project.pos_tablet.ui.views.fragment.customer;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos_tablet.activity.HomeActivity;
import qtc.project.pos_tablet.model.CustomerModel;

public interface FragmentCustomerViewInterface extends BaseViewInterface {
    void init(HomeActivity activity, FragmentCustomerViewCallback callback);
    void initRecyclerViewCustomer(ArrayList<CustomerModel> body);

    void resetFragment();

    void showDiaLogSucess();
}
