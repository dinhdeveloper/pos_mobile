package qtc.project.pos_mobile.fragment.customer;

import android.os.Bundle;
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
import qtc.project.pos_mobile.ui.views.fragment.customer.detail.FragmentCustomerDetailView;
import qtc.project.pos_mobile.ui.views.fragment.customer.detail.FragmentCustomerDetailViewCallback;
import qtc.project.pos_mobile.ui.views.fragment.customer.detail.FragmentCustomerDetailViewInterface;

    public class FragmentCustomerDetail extends BaseFragment<FragmentCustomerDetailViewInterface, BaseParameters> implements FragmentCustomerDetailViewCallback {
   HomeActivity activity;

   public static FragmentCustomerDetail newInstance(CustomerModel model){
       FragmentCustomerDetail fm = new FragmentCustomerDetail();
       Bundle bundle = new Bundle();
       bundle.putSerializable("model",model);
       fm.setArguments(bundle);
       return  fm;
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
           CustomerModel model = (CustomerModel)bundle.get("model");
           view.initLayout(model);
       }
    }

    @Override
    protected FragmentCustomerDetailViewInterface getViewInstance() {
        return new FragmentCustomerDetailView();
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
    public void updateCustomer(CustomerModel customerModel,String id) {
        if (customerModel != null) {
            showProgress();
            CustomerAdminRequest.ApiParams params = new CustomerAdminRequest.ApiParams();

            params.id_business = AppProvider.getPreferences().getUserModel().getId_business();
            params.type_manager = "update_customer";

            params.id_customer = id;
            params.employee_id = AppProvider.getPreferences().getUserModel().getId();
            params.full_name = customerModel.getFull_name();
            params.address = customerModel.getAddress();
            params.email = customerModel.getEmail();
            params.phone_number = customerModel.getPhone_number();

            AppProvider.getApiManagement().call(CustomerAdminRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<CustomerModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<CustomerModel> body) {
                    dismissProgress();
                    if (body.getSuccess().equalsIgnoreCase("true")) {
                        view.showPopUpSuccess();
                        dismissProgress();
                    } else if(body.getSuccess().equalsIgnoreCase("false")){
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
