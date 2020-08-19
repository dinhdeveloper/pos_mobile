package qtc.project.pos_tablet.fragment.customer;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos_tablet.activity.HomeActivity;
import qtc.project.pos_tablet.api.customer.CustomerAdminRequest;
import qtc.project.pos_tablet.api.customer.CustomerRequest;
import qtc.project.pos_tablet.dependency.AppProvider;
import qtc.project.pos_tablet.fragment.home.FragmentHome;
import qtc.project.pos_tablet.model.BaseResponseModel;
import qtc.project.pos_tablet.model.CustomerModel;
import qtc.project.pos_tablet.ui.views.fragment.customer.FragmentCustomerView;
import qtc.project.pos_tablet.ui.views.fragment.customer.FragmentCustomerViewCallback;
import qtc.project.pos_tablet.ui.views.fragment.customer.FragmentCustomerViewInterface;

public class FragmentCustomer extends BaseFragment<FragmentCustomerViewInterface, BaseParameters> implements FragmentCustomerViewCallback {

    HomeActivity activity;

    @Override
    protected void initialize() {
        //HomeActivity activity = (HomeActivity) getActivity();
        this.activity = (HomeActivity) getActivity();
        view.init(activity, this);

        requestDataCustomer();
    }

    private void requestDataCustomer() {
        showProgress();
        CustomerRequest.ApiParams params = new CustomerRequest.ApiParams();
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
    public void updateCustomer(CustomerModel customerModel) {
        if (customerModel != null) {
            showProgress();
            CustomerAdminRequest.ApiParams params = new CustomerAdminRequest.ApiParams();

            params.type_manager = "update_customer";

            params.id_customer = customerModel.getId();
            params.employee_id = String.valueOf(1);
            params.full_name = customerModel.getFull_name();
            params.address = customerModel.getAddress();
            params.email = customerModel.getEmail();
            params.phone_number = customerModel.getPhone_number();

            AppProvider.getApiManagement().call(CustomerAdminRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<CustomerModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<CustomerModel> body) {
                    dismissProgress();
                    if (body.getSuccess().equals("true")) {
                        requestDataCustomer();
                        view.resetFragment();
                        dismissProgress();
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
    public void createCustomer(CustomerModel customerModel) {
        if (customerModel !=null){
            showProgress();
            CustomerAdminRequest.ApiParams params = new CustomerAdminRequest.ApiParams();

            params.type_manager = "create_customer";
            params.employee_id = String.valueOf(1);
            params.full_name = customerModel.getFull_name();
            params.address = customerModel.getAddress();
            params.email = customerModel.getEmail();
            params.phone_number = customerModel.getPhone_number();

            AppProvider.getApiManagement().call(CustomerAdminRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<CustomerModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<CustomerModel> body) {
                    dismissProgress();
                    if (body.getSuccess().equals("true")) {
                        view.showDiaLogSucess();
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
    public void callDataSearchCus(String toString) {
        showProgress();
        CustomerRequest.ApiParams params = new CustomerRequest.ApiParams();
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
    public void callAllDataCustomer() {
        showProgress();
        CustomerRequest.ApiParams params = new CustomerRequest.ApiParams();
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
    public void goHome() {
        if (activity!=null){
            activity.replaceFragment(new FragmentHome(),false);
        }
    }
}
