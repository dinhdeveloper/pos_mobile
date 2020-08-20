package qtc.project.pos_mobile.ui.views.fragment.home.productlist;

import qtc.project.pos_mobile.model.ProductModel;

public interface FragmentProductHomeViewCallback {
    void onBackP();

    void loadMore();

    void goToDetail(ProductModel model);

    void searchProduct(String search);

    void callAllData();

    void resetPage();
}
