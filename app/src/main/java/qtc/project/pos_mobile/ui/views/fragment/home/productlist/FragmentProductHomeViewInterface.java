package qtc.project.pos_mobile.ui.views.fragment.home.productlist;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos_mobile.activity.HomeActivity;
import qtc.project.pos_mobile.model.ProductModel;

public interface FragmentProductHomeViewInterface extends BaseViewInterface {

    void init(HomeActivity activity,FragmentProductHomeViewCallback callback);

    void setNoMoreLoading();

    void clearListDataProduct();

    void initRecyclerViewProduct(ProductModel[] data);
}
