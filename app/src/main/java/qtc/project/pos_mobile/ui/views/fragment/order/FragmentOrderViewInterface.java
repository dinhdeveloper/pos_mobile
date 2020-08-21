package qtc.project.pos_mobile.ui.views.fragment.order;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos_mobile.activity.HomeActivity;
import qtc.project.pos_mobile.model.OrderModel;

public interface FragmentOrderViewInterface extends BaseViewInterface {

    void init(HomeActivity activity,FragmentOrderViewCallback callback);

    void initRecyclerViewOrder(OrderModel[] list);

    void setNoMoreLoading();

    void clearnData();

    void setLayoutNull();

    void initRecyclerView(OrderModel[] list);
}
