package qtc.project.pos_mobile.ui.views.fragment.level;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos_mobile.activity.HomeActivity;
import qtc.project.pos_mobile.model.LevelCustomerModel;

public interface FragmentLevelCustomerViewInterface extends BaseViewInterface {

    void init(HomeActivity activity,FragmentLevelCustomerViewCallback callback);

    void initRecyclerView(ArrayList<LevelCustomerModel> list);
}
