package qtc.project.pos_mobile.ui.views.fragment.customer.detail;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos_mobile.activity.HomeActivity;
import qtc.project.pos_mobile.model.CustomerModel;

public interface FragmentCustomerDetailViewInterface extends BaseViewInterface {

    void init(HomeActivity activity,FragmentCustomerDetailViewCallback callback);

    void initLayout(CustomerModel model);

    void showPopUpSuccess();
}
