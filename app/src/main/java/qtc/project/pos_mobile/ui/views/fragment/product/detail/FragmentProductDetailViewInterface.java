package qtc.project.pos_mobile.ui.views.fragment.product.detail;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos_mobile.activity.HomeActivity;
import qtc.project.pos_mobile.model.ProductModel;

public interface FragmentProductDetailViewInterface extends BaseViewInterface {

    void init(HomeActivity activity,FragmentProductDetailViewCallback callback);

    void initView(ProductModel model);

    void showUpdateSuccess();
}
