package qtc.project.pos_mobile.fragment.level;

import android.os.Bundle;
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
import qtc.project.pos_mobile.model.BaseResponseModel;
import qtc.project.pos_mobile.model.CustomerModel;
import qtc.project.pos_mobile.ui.views.fragment.level.customerById.FragmentCustomerListView;
import qtc.project.pos_mobile.ui.views.fragment.level.customerById.FragmentCustomerListViewCallback;
import qtc.project.pos_mobile.ui.views.fragment.level.customerById.FragmentCustomerListViewInterface;

public class FragmentCustomerList extends BaseFragment<FragmentCustomerListViewInterface, BaseParameters> implements FragmentCustomerListViewCallback {

    HomeActivity activity;
    String name;
    public  static FragmentCustomerList newInstance(String name,CustomerModel[] list){
        FragmentCustomerList fm = new FragmentCustomerList();
        Bundle bundle = new Bundle();
        bundle.putSerializable("model",list);
        bundle.putString("name",name);
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
            CustomerModel[] list = (CustomerModel[])bundle.get("model");
            name = (String)bundle.get("name");
            view.initView(name,list);
        }
    }

    @Override
    protected FragmentCustomerListViewInterface getViewInstance() {
        return new FragmentCustomerListView();
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
    public void getAllData() {
        view.resetData();
        getBundle();
    }

    @Override
    public void seachCustomer(String search,String level_id) {
        showProgress();
        CustomerRequest.ApiParams params = new CustomerRequest.ApiParams();
        params.id_business = AppProvider.getPreferences().getUserModel().getId_business();
        if (search!=null){
            params.customer_filter = search;
        }
        params.level_id = level_id;
        AppProvider.getApiManagement().call(CustomerRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<CustomerModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<CustomerModel> body) {
                if (body != null) {
                    dismissProgress();
                    view.clearnData();
                    view.initView(name,body.getData());
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
