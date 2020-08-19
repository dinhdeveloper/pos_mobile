package qtc.project.pos_tablet.ui.views.fragment.home;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos_tablet.activity.HomeActivity;
import qtc.project.pos_tablet.model.CategoryModel;
import qtc.project.pos_tablet.model.CustomerModel;
import qtc.project.pos_tablet.model.ListOrderModel;
import qtc.project.pos_tablet.model.ProductModel;

public interface FragmentHomeViewInterface extends BaseViewInterface {
    void init(HomeActivity activity, FragmentHomeViewCallback callback);
    void initRecyclerViewCaregory(ArrayList<CategoryModel> body);
    void initRecyclerViewProduct(ProductModel[] body, int status);
    void initCustomer(ArrayList<CustomerModel> list);
    void setNoMoreLoading();

    void clearListDataProduct();

    void truyenIntent(ArrayList<ListOrderModel> arList);

    void loadMore();
}
