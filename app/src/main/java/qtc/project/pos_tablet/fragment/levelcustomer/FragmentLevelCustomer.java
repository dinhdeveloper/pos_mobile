package qtc.project.pos_tablet.fragment.levelcustomer;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos_tablet.activity.HomeActivity;
import qtc.project.pos_tablet.api.customer.CountCustomerRequest;
import qtc.project.pos_tablet.api.customer.CustomerRequest;
import qtc.project.pos_tablet.api.customer.LevelCustomerRequest;
import qtc.project.pos_tablet.dependency.AppProvider;
import qtc.project.pos_tablet.fragment.home.FragmentHome;
import qtc.project.pos_tablet.model.BaseResponseModel;
import qtc.project.pos_tablet.model.CountCustomerModel;
import qtc.project.pos_tablet.model.CustomerModel;
import qtc.project.pos_tablet.model.LevelCustomerModel;
import qtc.project.pos_tablet.ui.views.fragment.levelcustomer.FragmentLevelCustomerView;
import qtc.project.pos_tablet.ui.views.fragment.levelcustomer.FragmentLevelCustomerViewCallback;
import qtc.project.pos_tablet.ui.views.fragment.levelcustomer.FragmentLevelCustomerViewInterface;

public class FragmentLevelCustomer extends BaseFragment<FragmentLevelCustomerViewInterface, BaseParameters> implements FragmentLevelCustomerViewCallback {

    ArrayList<CustomerModel> list;

    @Override
    protected void initialize() {
        HomeActivity activity = (HomeActivity) getActivity();
        view.init(activity, this);
        requestDataLevelCustomer();
    }

    private void requestDataLevelCustomer() {
        showProgress();
        LevelCustomerRequest.ApiParams params = new LevelCustomerRequest.ApiParams();
        AppProvider.getApiManagement().call(LevelCustomerRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<LevelCustomerModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<LevelCustomerModel> body) {
                if (body != null) {
                    dismissProgress();
                    ArrayList<LevelCustomerModel> list = new ArrayList<>();
                    list.addAll(Arrays.asList(body.getData()));
                    view.initRecyclerView(list);
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
    protected FragmentLevelCustomerViewInterface getViewInstance() {
        return new FragmentLevelCustomerView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void callPopup(LevelCustomerModel model) {
        callDataCustomerById(model.getId());
    }

    @Override
    public void eventBackHome() {
        replaceFragment(new FragmentHome(), false);
    }

    @Override
    public void eventSearchCustomer(String toString, String id) {
        showProgress();
        CustomerRequest.ApiParams params = new CustomerRequest.ApiParams();
        if (toString != null) {
            params.level_id = id;
            params.customer_filter = toString;
        }
        AppProvider.getApiManagement().call(CustomerRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<CustomerModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<CustomerModel> body) {
                if (body != null) {
                    dismissProgress();
                    list = new ArrayList<>();
                    list.addAll(Arrays.asList(body.getData()));
                    view.setDataCusDiaLog(list);
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
    public void getgetTotalCustomer(String id_level) {
        CountCustomerRequest.ApiParams params = new CountCustomerRequest.ApiParams();
        if (id_level != null) {
            params.level_id = id_level;
        }
        AppProvider.getApiManagement().call(CountCustomerRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<CountCustomerModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<CountCustomerModel> body) {
                if (body!=null){
                    ArrayList<CountCustomerModel> list = new ArrayList<>();
                    list.addAll(Arrays.asList(body.getData()));
                    view.viewCountItemToDiaLog(list.get(0).getTotal_customer());
                }
            }

            @Override
            public void onError(ErrorApiResponse error) {

            }

            @Override
            public void onFail(ApiRequest.RequestError error) {

            }
        });
    }

    @Override
    public void getAllDataById(String id_level) {
        callDataCustomerById(id_level);
    }

    private void callDataCustomerById(String id) {
        showProgress();
        CustomerRequest.ApiParams params = new CustomerRequest.ApiParams();
        if (id != null) {
            params.level_id = id;
        }
        AppProvider.getApiManagement().call(CustomerRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<CustomerModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<CustomerModel> body) {
                if (body != null) {
                    dismissProgress();
                    list = new ArrayList<>();
                    list.addAll(Arrays.asList(body.getData()));
                    showPopup(list);
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

    private void showPopup(ArrayList<CustomerModel> list) {
        if (list.size() != 0) {
            view.setDataCusDiaLog(list);
        }
    }
}
