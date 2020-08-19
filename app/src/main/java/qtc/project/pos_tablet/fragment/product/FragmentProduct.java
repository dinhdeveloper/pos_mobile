package qtc.project.pos_tablet.fragment.product;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;

import b.laixuantam.myaarlibrary.widgets.dialog.alert.KAlertDialog;
import qtc.project.pos_tablet.R;
import qtc.project.pos_tablet.activity.BarCodeActivity;
import qtc.project.pos_tablet.activity.HomeActivity;
import qtc.project.pos_tablet.api.home.category.CategoryRequest;
import qtc.project.pos_tablet.api.home.product.ProductRequest;
import qtc.project.pos_tablet.api.product.ProductAdminRequest;
import qtc.project.pos_tablet.dependency.AppProvider;
import qtc.project.pos_tablet.fragment.home.FragmentHome;
import qtc.project.pos_tablet.model.BaseResponseModel;
import qtc.project.pos_tablet.model.CategoryModel;
import qtc.project.pos_tablet.model.ProductModel;
import qtc.project.pos_tablet.ui.views.fragment.product.FragmentProductView;
import qtc.project.pos_tablet.ui.views.fragment.product.FragmentProductViewCallback;
import qtc.project.pos_tablet.ui.views.fragment.product.FragmentProductViewInterface;

import android.widget.Toast;

public class FragmentProduct extends BaseFragment<FragmentProductViewInterface, BaseParameters> implements FragmentProductViewCallback {

    private String cateId = null;
    private String price = null;

    PopupWindow popUp;
    int page =1;
    private int totalPage = 0;
    boolean click = true;

    //    public static FragmentProduct newIntance(ProductModel item) {
//        FragmentProduct frag = new FragmentProduct();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("model", item);
//        frag.setArguments(bundle);
//
//        return frag;
//
//    }
    HomeActivity activity;
    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);

        requestDataCategory();
        requestDataProduct();
    }

    private void requestDataProduct() {
        ProductRequest.ApiParams params = new ProductRequest.ApiParams();
        if (cateId != null) {
            params.id_category = cateId;
        }
        params.page = String.valueOf(page);
        showProgress();
        AppProvider.getApiManagement().call(ProductRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<ProductModel> result) {
                dismissProgress();
                if (!TextUtils.isEmpty(result.getSuccess()) && Objects.requireNonNull(result.getSuccess()).equalsIgnoreCase("true")) {
                    if (!TextUtils.isEmpty(result.getTotal_page())) {
                        totalPage = Integer.valueOf(result.getTotal_page());
                        if (page == totalPage) {
                            view.setNoMoreLoading();
                        }
                    } else {
                        view.setNoMoreLoading();
                    }
                    view.initRecyclerViewProduct(result.getData());
                } else {
                    if (!TextUtils.isEmpty(result.getMessage()))
                        showAlert(result.getMessage(), KAlertDialog.ERROR_TYPE);
                    else
                        showAlert("Không thể tải dữ liệu.", KAlertDialog.ERROR_TYPE);
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
    @Override
    public void requestDataCategory() {
        CategoryRequest.ApiParams params = new CategoryRequest.ApiParams();
        showProgress();
        AppProvider.getApiManagement().call(CategoryRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<CategoryModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<CategoryModel> body) {
                dismissProgress();
                if (body != null) {
                    ArrayList<CategoryModel> dataBeans = new ArrayList<>();
                    if (body.getData() != null && body.getData().length > 0)
                        dataBeans.addAll(Arrays.asList(body.getData()));
                    view.initRecyclerViewCaregory(dataBeans);
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

    @Override
    public void searchProduct(String search) {
        showProgress();
        ProductRequest.ApiParams params = new ProductRequest.ApiParams();
        params.product = search;
        AppProvider.getApiManagement().call(ProductRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<ProductModel> result) {
                dismissProgress();
//                if (!TextUtils.isEmpty(result.getSuccess()) && Objects.requireNonNull(result.getSuccess()).equalsIgnoreCase("true")) {
//                    if (!TextUtils.isEmpty(result.getTotal_page())) {
//                        totalPage = Integer.valueOf(result.getTotal_page());
//                        if (page == totalPage) {
//                            view.setNoMoreLoading();
//                        }
//                    } else {
//                        view.setNoMoreLoading();
//                    }
//                    view.initRecyclerViewProduct(result.getData());
//                } else {
//                    if (!TextUtils.isEmpty(result.getMessage()))
//                        showAlert(result.getMessage(), KAlertDialog.ERROR_TYPE);
//                    else
//                        showAlert("Không thể tải dữ liệu.", KAlertDialog.ERROR_TYPE);
//                }
                view.clearListDataProduct();
                view.setNoMoreLoading();
                view.initRecyclerViewProduct(result.getData());
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

    @Override
    public void callAllData() {
        ProductRequest.ApiParams params = new ProductRequest.ApiParams();
        resetPage();
        params.page = String.valueOf(page);
        showProgress();
        AppProvider.getApiManagement().call(ProductRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<ProductModel> result) {
                dismissProgress();
                if (!TextUtils.isEmpty(result.getSuccess()) && Objects.requireNonNull(result.getSuccess()).equalsIgnoreCase("true")) {
                    if (!TextUtils.isEmpty(result.getTotal_page())) {
                        totalPage = Integer.valueOf(result.getTotal_page());
                        if (page == totalPage) {
                            view.setNoMoreLoading();
                        }
                    } else {
                        view.setNoMoreLoading();
                    }
                    view.initRecyclerViewProduct(result.getData());
                } else {
                    if (!TextUtils.isEmpty(result.getMessage()))
                        showAlert(result.getMessage(), KAlertDialog.ERROR_TYPE);
                    else
                        showAlert("Không thể tải dữ liệu.", KAlertDialog.ERROR_TYPE);
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

    @Override
    public void loadMore() {
        ++page;

        if (totalPage > 0 && page <= totalPage) {
            requestDataProduct();
        } else {
            view.setNoMoreLoading();
        }
    }

    @Override
    public void resetPage() {
        page =1;
        totalPage = 0;
        cateId =null;
    }

    @Override
    public void goHome() {
        if (activity!=null)
            activity.replaceFragment(new FragmentHome(),false);
    }

    @Override
    public void inBarCode(String name,String barcode, String status) {
        if (activity!=null){
            Intent intent = new Intent(activity, BarCodeActivity.class);
            intent.putExtra("BARCODE",barcode);
            intent.putExtra("status",status);
            intent.putExtra("PRODUCT_NAME",name);
            activity.startActivity(intent);
        }
    }

    private void getAllCategory() {
        ProductRequest.ApiParams params = new ProductRequest.ApiParams();
        showProgress();
        AppProvider.getApiManagement().call(ProductRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<ProductModel> body) {
                dismissProgress();
                if (body != null) {
                    view.initRecyclerViewProduct(body.getData());
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

    @Override
    protected FragmentProductViewInterface getViewInstance() {
        return new FragmentProductView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void onChangToCategoryDetail(CategoryModel model) {
        cateId = model.getId();
        ProductRequest.ApiParams params = new ProductRequest.ApiParams();
        if (cateId != null) {
            params.id_category = cateId;
        }
        showProgress();
        AppProvider.getApiManagement().call(ProductRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<ProductModel> result) {
                dismissProgress();
                if (!TextUtils.isEmpty(result.getSuccess()) && Objects.requireNonNull(result.getSuccess()).equalsIgnoreCase("true")) {
                    if (!TextUtils.isEmpty(result.getTotal_page())) {
                        totalPage = Integer.valueOf(result.getTotal_page());
                        if (page == totalPage) {
                            view.setNoMoreLoading();
                        }
                    } else {
                        view.setNoMoreLoading();
                    }
                    view.clearListDataProduct();
                    view.initRecyclerViewProduct(result.getData());
                } else {
                    if (!TextUtils.isEmpty(result.getMessage()))
                        showAlert(result.getMessage(), KAlertDialog.ERROR_TYPE);
                    else
                        showAlert("Không thể tải dữ liệu.", KAlertDialog.ERROR_TYPE);
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

    @Override
    public void callPopup(HomeActivity activity,ProductModel model) {
        if (model.getListDataProduct().size() != 0) {
            view.initDataDialog(model);
        }
    }

    @Override
    public void updateProductDetail(ProductModel productModel) {
            if (productModel !=null){
                showProgress();
                ProductAdminRequest.ApiParams params = new ProductAdminRequest.ApiParams();
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
                            view.resetLayout();
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
    public void connetUSB() {

    }

}
