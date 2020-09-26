package qtc.project.pos_mobile.fragment.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.util.Objects;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import b.laixuantam.myaarlibrary.widgets.dialog.alert.KAlertDialog;
import qtc.project.pos_mobile.activity.HomeActivity;
import qtc.project.pos_mobile.api.home.product.ProductRequest;
import qtc.project.pos_mobile.dependency.AppProvider;
import qtc.project.pos_mobile.model.BaseResponseModel;
import qtc.project.pos_mobile.model.ProductModel;
import qtc.project.pos_mobile.ui.views.fragment.home.productlist.FragmentProductHomeView;
import qtc.project.pos_mobile.ui.views.fragment.home.productlist.FragmentProductHomeViewCallback;
import qtc.project.pos_mobile.ui.views.fragment.home.productlist.FragmentProductHomeViewInterface;

public class FragmentProductHome extends BaseFragment<FragmentProductHomeViewInterface, BaseParameters> implements FragmentProductHomeViewCallback {

    HomeActivity activity;
    int page =1;
    private int totalPage = 0;
    private String cateId = null;
    private String price = null;
    public static FragmentProductHome newInstance(String search){
        FragmentProductHome fm = new FragmentProductHome();
        Bundle bundle = new Bundle();
        bundle.putString("search",search);
        fm.setArguments(bundle);

        return fm;
    }
    @Override
    protected void initialize() {
        activity = (HomeActivity)getActivity();
        view.init(activity,this);
        getData();
    }

    private void getData() {
        Bundle bundle = getArguments();
        String search = (String)bundle.get("search");
        if (search!=null){
            ProductRequest.ApiParams params = new ProductRequest.ApiParams();
            params.id_business = AppProvider.getPreferences().getUserModel().getId_business();
            params.product = search;
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
    }

    @Override
    protected FragmentProductHomeViewInterface getViewInstance() {
        return new FragmentProductHomeView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void onBackP() {
        if (activity!=null)
            activity.replaceFragment(new FragmentHome(),false);
    }

    @Override
    public void loadMore() {
        ++page;

        if (totalPage > 0 && page <= totalPage) {
            getData();
        } else {
            view.setNoMoreLoading();
        }
    }

    @Override
    public void goToDetail(ProductModel model) {
        if (activity != null) {
            activity.checkBack();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    activity.setDataHome(model);
                }
            }, 100);
        }
    }

    @Override
    public void searchProduct(String search) {
        showProgress();
        ProductRequest.ApiParams params = new ProductRequest.ApiParams();
        params.id_business = AppProvider.getPreferences().getUserModel().getId_business();
        params.product = search;
        AppProvider.getApiManagement().call(ProductRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<ProductModel> result) {
                dismissProgress();
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
        params.id_business = AppProvider.getPreferences().getUserModel().getId_business();
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
    public void resetPage() {
        page =1;
        totalPage = 0;
        cateId =null;
    }
}
