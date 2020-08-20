package qtc.project.pos_mobile.ui.views.fragment.product;

import qtc.project.pos_mobile.model.ProductModel;

public interface FragmentProductViewCallback {
    void onBackP();

    void loadMore();

    void goToDetail(ProductModel model);

    void searchProduct(String search);

    void callAllData();

    void resetPage();

    void callNav();
}
