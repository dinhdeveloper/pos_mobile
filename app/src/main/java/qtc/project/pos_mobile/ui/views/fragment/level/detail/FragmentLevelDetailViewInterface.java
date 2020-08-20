package qtc.project.pos_mobile.ui.views.fragment.level.detail;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos_mobile.activity.HomeActivity;
import qtc.project.pos_mobile.model.CustomerModel;
import qtc.project.pos_mobile.model.LevelCustomerModel;

public interface FragmentLevelDetailViewInterface extends BaseViewInterface {

    void init(HomeActivity activity,FragmentLevelDetailViewCallback callback);

    void initLayout(LevelCustomerModel model);

    void showPopupListCustomer(ArrayList<CustomerModel> list);
}
