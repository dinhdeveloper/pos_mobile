package qtc.project.pos_tablet.ui.views.fragment.product;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos_tablet.activity.HomeActivity;
import qtc.project.pos_tablet.model.CategoryModel;
import qtc.project.pos_tablet.model.ProductModel;


public interface FragmentProductViewInterface extends BaseViewInterface {
    void init(HomeActivity activity, FragmentProductViewCallback callback);
    void initRecyclerViewCaregory(ArrayList<CategoryModel> body);
    void initRecyclerViewProduct(ProductModel[] body);
    void initDataDialog(ProductModel body);

    void resetLayout();

    void showUpdateSuccess();

    void setNoMoreLoading();

    void clearListDataProduct();
}
