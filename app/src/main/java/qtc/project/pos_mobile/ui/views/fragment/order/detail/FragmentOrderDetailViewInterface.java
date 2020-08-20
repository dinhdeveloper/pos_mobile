package qtc.project.pos_mobile.ui.views.fragment.order.detail;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos_mobile.activity.HomeActivity;
import qtc.project.pos_mobile.model.OrderModel;

public interface FragmentOrderDetailViewInterface extends BaseViewInterface {

    void init(HomeActivity activity,FragmentOrderDetailViewCallback callback);

    void initView(OrderModel model);

    void showSuccess();
}
