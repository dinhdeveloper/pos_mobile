package qtc.project.pos_mobile.ui.views.fragment.product;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos_mobile.activity.HomeActivity;
import qtc.project.pos_mobile.model.ProductModel;

public interface FragmentProductViewInterface extends BaseViewInterface {

    void init(HomeActivity activity,FragmentProductViewCallback callback);

    void setNoMoreLoading();

    void initRecyclerViewProduct(ProductModel[] data);

    void clearListDataProduct();
}
