package qtc.project.pos_tablet.ui.views.fragment.order;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos_tablet.activity.HomeActivity;
import qtc.project.pos_tablet.model.OrderModel;

public interface FragmentOrderViewInterface extends BaseViewInterface {
    void init(HomeActivity activity, FragmentOrderViewCallback callback);
    void initRecyclerViewOrder(ArrayList<OrderModel> body);

    void initDataFilter(ArrayList<OrderModel> body,String dates,String id_order);

    void showSuccess();
}
