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
import qtc.project.pos_mobile.model.BaseResponseModel;
import qtc.project.pos_mobile.model.CustomerModel;
import qtc.project.pos_mobile.ui.views.fragment.customer.filter.FragmentCustomerFilterView;
import qtc.project.pos_mobile.ui.views.fragment.customer.filter.FragmentCustomerFilterViewCallback;
import qtc.project.pos_mobile.ui.views.fragment.customer.filter.FragmentCustomerFilterViewInterface;

public class FragmentCustomerFilter extends BaseFragment<FragmentCustomerFilterViewInterface, BaseParameters> implements FragmentCustomerFilterViewCallback {

    HomeActivity activity;

    @Override
    protected void initialize() {
        activity = (HomeActivity)getActivity();
        view.init(activity,this);

        callDataCustomer();
    }

    private void callDataCustomer() {
        showProgress();
        CustomerRequest.ApiParams params = new CustomerRequest.ApiParams();
        AppProvider.getApiManagement().call(CustomerRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<CustomerModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<CustomerModel> body) {
                if (body != null) {
                    dismissProgress();
                   ArrayList<CustomerModel> list = new ArrayList<>();
                    list.addAll(Arrays.asList(body.getData()));
                    view.initCustomer(list);
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
    protected FragmentCustomerFilterViewInterface getViewInstance() {
        return new FragmentCustomerFilterView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void setCustomerToHome(CustomerModel model) {
        if (activity != null) {
            activity.checkBack();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    activity.setDataCustomer(model);
                }
            }, 100);
        }
    }

    @Override
    public void onBackP() {
        if (activity!=null)
            activity.checkBack();
    }
}
