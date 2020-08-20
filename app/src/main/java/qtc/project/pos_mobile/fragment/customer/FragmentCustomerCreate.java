package qtc.project.pos_mobile.fragment.customer;

import android.util.Log;
import android.widget.Toast;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos_mobile.activity.HomeActivity;
import qtc.project.pos_mobile.api.customer.CustomerAdminRequest;
import qtc.project.pos_mobile.dependency.AppProvider;
import qtc.project.pos_mobile.model.BaseResponseModel;
import qtc.project.pos_mobile.model.CustomerModel;
import qtc.project.pos_mobile.ui.views.fragment.customer.create.FragmentCustomerCreateView;
import qtc.project.pos_mobile.ui.views.fragment.customer.create.FragmentCustomerCreateViewCallback;
import qtc.project.pos_mobile.ui.views.fragment.customer.create.FragmentCustomerCreateViewInterface;

public class FragmentCustomerCreate extends BaseFragment<FragmentCustomerCreateViewInterface, BaseParameters> implements FragmentCustomerCreateViewCallback {
    HomeActivity activity;
    @Override
    protected void initialize() {
        activity =(HomeActivity)getActivity();
        view.init(activity,this);
    }

    @Override
    protected FragmentCustomerCreateViewInterface getViewInstance() {
        return new FragmentCustomerCreateView();
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
                    if (body.getSuccess().equalsIgnoreCase("true")) {
                        view.showDiaLogSucess();
                    } else if (body.getSuccess().equalsIgnoreCase("false")){
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
}
