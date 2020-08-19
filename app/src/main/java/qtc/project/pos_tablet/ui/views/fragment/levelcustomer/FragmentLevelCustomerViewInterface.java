package qtc.project.pos_tablet.ui.views.fragment.levelcustomer;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos_tablet.activity.HomeActivity;
import qtc.project.pos_tablet.model.CustomerModel;
import qtc.project.pos_tablet.model.LevelCustomerModel;

public interface FragmentLevelCustomerViewInterface extends BaseViewInterface {
    void init(HomeActivity activity,FragmentLevelCustomerViewCallback callback);
    void initRecyclerView(ArrayList<LevelCustomerModel> body);

    void viewCountItemToDiaLog(String id);

    void setDataCusDiaLog(ArrayList<CustomerModel> list);
}
