package qtc.project.pos_mobile.ui.views.fragment.customer.create;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos_mobile.activity.HomeActivity;

public interface FragmentCustomerCreateViewInterface extends BaseViewInterface {

    void init(HomeActivity activity,FragmentCustomerCreateViewCallback callback);

    void showDiaLogSucess();
}
