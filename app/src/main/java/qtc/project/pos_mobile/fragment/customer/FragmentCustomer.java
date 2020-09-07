package qtc.project.pos_mobile.fragment.customer;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos_mobile.activity.HomeActivity;
import qtc.project.pos_mobile.api.customer.CustomerRequest;
import qtc.project.pos_mobile.dependency.AppProvider;
import qtc.project.pos_mobile.fragment.home.FragmentHome;
import qtc.project.pos_mobile.model.BaseResponseModel;
import qtc.project.pos_mobile.model.CustomerModel;
import qtc.project.pos_mobile.ui.views.fragment.customer.FragmentCustomerView;
import qtc.project.pos_mobile.ui.views.fragment.customer.FragmentCustomerViewCallback;
import qtc.project.pos_mobile.ui.views.fragment.customer.FragmentCustomerViewInterface;

public class FragmentCustomer extends BaseFragment<FragmentCustomerViewInterface, BaseParameters> implements FragmentCustomerViewCallback {

    HomeActivity activity;
    @Override
    protected void initialize() {
        activity  =(HomeActivity)getActivity();
        view.init(activity,this);

        requestDataCustomer();
    }
    private void requestDataCustomer() {
        showProgress();
        CustomerRequest.ApiParams params = new CustomerRequest.ApiParams();
        params.id_business = AppProvider.getPreferences().getUserModel().getId_business();
        AppProvider.getApiManagement().call(CustomerRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<CustomerModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<CustomerModel> body) {
                if (body != null) {
                    dismissProgress();
                    ArrayList<CustomerModel> list = new ArrayList<>();
                    list.addAll(Arrays.asList(body.getData()));
                    view.initRecyclerViewCustomer(list);
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
    protected FragmentCustomerViewInterface getViewInstance() {
        return new FragmentCustomerView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void goHome() {
        if (activity!=null)
            activity.replaceFragment(new FragmentHome(),false);
    }

    @Override
    public void goDetail(CustomerModel model) {
        if (activity!=null)
            activity.replaceFragment(new FragmentCustomerDetail().newInstance(model),true);
    }

    @Override
    public void addCustomer() {
        if (activity!=null)
            activity.replaceFragment(new FragmentCustomerCreate(),true);
    }

    @Override
    public void callAllDataCustomer() {
        requestDataCustomer();
    }

    @Override
    public void callDataSearchCus(String toString) {
        showProgress();
        CustomerRequest.ApiParams params = new CustomerRequest.ApiParams();
        params.id_business = AppProvider.getPreferences().getUserModel().getId_business();
        if (toString!=null){
            params.customer_filter = toString;
        }
        AppProvider.getApiManagement().call(CustomerRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<CustomerModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<CustomerModel> body) {
                if (body != null) {
                    dismissProgress();
                    ArrayList<CustomerModel> list = new ArrayList<>();
                    list.addAll(Arrays.asList(body.getData()));
                    view.initRecyclerViewCustomer(list);
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
    public void callNav() {
        if (activity!=null)
            activity.toggleNav();
    }
}
