package qtc.project.pos_mobile.ui.views.fragment.level.customerById;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos_mobile.activity.HomeActivity;
import qtc.project.pos_mobile.model.CustomerModel;

public interface FragmentCustomerListViewInterface extends BaseViewInterface {

    void init(HomeActivity activity,FragmentCustomerListViewCallback callback);

    void initView(String name,CustomerModel[] list);

    void resetData();

    void clearnData();
}
