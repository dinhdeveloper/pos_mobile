package qtc.project.pos_mobile.ui.views.fragment.home;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos_mobile.activity.HomeActivity;
import qtc.project.pos_mobile.model.CustomerModel;
import qtc.project.pos_mobile.model.ListOrderModel;
import qtc.project.pos_mobile.model.ProductModel;

public interface FragmentHomeViewInterface extends BaseViewInterface {

    void init(HomeActivity activity,FragmentHomeViewCallback callback);

    void initView(CustomerModel model);

    void initViewProduct(ArrayList<ProductModel> list);

    void truyenIntent(ArrayList<ListOrderModel> arList);

    void initViewProductClick(ProductModel model);
}
