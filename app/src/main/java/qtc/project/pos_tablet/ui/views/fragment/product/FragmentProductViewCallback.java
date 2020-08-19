package qtc.project.pos_tablet.ui.views.fragment.product;

import qtc.project.pos_tablet.activity.HomeActivity;
import qtc.project.pos_tablet.model.CategoryModel;
import qtc.project.pos_tablet.model.ProductModel;

public interface FragmentProductViewCallback {
    void  onChangToCategoryDetail(CategoryModel model);

    void callPopup(HomeActivity activity,ProductModel model);

    void updateProductDetail(ProductModel productModel);

    void requestDataCategory();

    void searchProduct(String search);

    void callAllData();

    void loadMore();

    void resetPage();

    void goHome();

    void inBarCode(String name,String barcode, String status);

    void connetUSB();
}
