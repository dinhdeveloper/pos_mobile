package qtc.project.pos_mobile.ui.views.fragment.product.detail;

import qtc.project.pos_mobile.model.ProductModel;

public interface FragmentProductDetailViewCallback {
    void onBackP();

    void updateProductDetail(ProductModel productModel);

    void inBarCode(String product_name, String barcode, String status);
}
