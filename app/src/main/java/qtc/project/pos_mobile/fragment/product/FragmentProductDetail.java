package qtc.project.pos_mobile.fragment.product;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos_mobile.activity.BarCodeActivity;
import qtc.project.pos_mobile.activity.HomeActivity;
import qtc.project.pos_mobile.api.product.ProductAdminRequest;
import qtc.project.pos_mobile.dependency.AppProvider;
import qtc.project.pos_mobile.model.BaseResponseModel;
import qtc.project.pos_mobile.model.ProductModel;
import qtc.project.pos_mobile.ui.views.fragment.product.detail.FragmentProductDetailView;
import qtc.project.pos_mobile.ui.views.fragment.product.detail.FragmentProductDetailViewCallback;
import qtc.project.pos_mobile.ui.views.fragment.product.detail.FragmentProductDetailViewInterface;

public class FragmentProductDetail extends BaseFragment<FragmentProductDetailViewInterface, BaseParameters> implements FragmentProductDetailViewCallback {
    HomeActivity activity;

    public  static  FragmentProductDetail newInstance(ProductModel model){
        FragmentProductDetail fm = new FragmentProductDetail();
        Bundle bundle = new Bundle();
        bundle.putSerializable("model",model);
        fm.setArguments(bundle);

        return fm;
    }
    @Override
    protected void initialize() {
        activity = (HomeActivity)getActivity();
        view.init(activity,this);
        
        getBundle();
    }

    private void getBundle() {
        Bundle bundle = getArguments();
        if (bundle!=null){
            ProductModel model = (ProductModel)bundle.get("model");
            view.initView(model);
        }

    }

    @Override
    protected FragmentProductDetailViewInterface getViewInstance() {
        return new FragmentProductDetailView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void onBackP() {
        if (activity!=null)
            activity.checkBack();
    }

    @Override
    public void updateProductDetail(ProductModel productModel) {
        if (productModel !=null){
            showProgress();
            ProductAdminRequest.ApiParams params = new ProductAdminRequest.ApiParams();
            params.id_business = AppProvider.getPreferences().getUserModel().getId_business();
            params.type_manager = "update_product";
            params.id_product = productModel.getId();
            params.name = productModel.getName();
            params.description = productModel.getDescription();
            params.quantity_safetystock = productModel.getQuantity_safetystock();

            AppProvider.getApiManagement().call(ProductAdminRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<ProductModel> body) {
                    dismissProgress();
                    if (body.getSuccess().equals("true")) {
                        view.showUpdateSuccess();
                    } else {
                        Toast.makeText(getActivity(), "" + body.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(ErrorApiResponse error) {
                    dismissProgress();
                    Log.e("onError", error.message);
                }

                @Override
                public void onFail(ApiRequest.RequestError error) {
                    dismissProgress();
                    Log.e("onFail", error.name());
                }
            });
        }
    }

    @Override
    public void inBarCode(String product_name, String barcode, String status) {
        if (activity!=null){
            Intent intent = new Intent(activity, BarCodeActivity.class);
            intent.putExtra("BARCODE",barcode);
            intent.putExtra("status",status);
            intent.putExtra("PRODUCT_NAME",product_name);
            activity.startActivity(intent);
        }
    }
}
