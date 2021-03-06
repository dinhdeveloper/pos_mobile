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
import qtc.project.pos_mobile.fragment.customer.FragmentCustomerDetail;
import qtc.project.pos_mobile.fragment.home.FragmentHome;
import qtc.project.pos_mobile.model.BaseResponseModel;
import qtc.project.pos_mobile.model.CustomerModel;
import qtc.project.pos_mobile.model.LevelCustomerModel;
import qtc.project.pos_mobile.ui.views.fragment.level.detail.FragmentLevelDetailView;
import qtc.project.pos_mobile.ui.views.fragment.level.detail.FragmentLevelDetailViewCallback;
import qtc.project.pos_mobile.ui.views.fragment.level.detail.FragmentLevelDetailViewInterface;

public class FragmentLevelDetail extends BaseFragment<FragmentLevelDetailViewInterface, BaseParameters> implements FragmentLevelDetailViewCallback {
    HomeActivity activity;

    public static FragmentLevelDetail newInstance(LevelCustomerModel model) {
        FragmentLevelDetail fm = new FragmentLevelDetail();
        Bundle bundle = new Bundle();
        bundle.putSerializable("model", model);
        fm.setArguments(bundle);
        return fm;
    }

    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);
        getBundle();
    }

    private void getBundle() {
        Bundle extras = getArguments();
        if (extras != null) {
            LevelCustomerModel model = (LevelCustomerModel) extras.get("model");
            view.initLayout(model);
        }
    }

    @Override
    protected FragmentLevelDetailViewInterface getViewInstance() {
        return new FragmentLevelDetailView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void onBackP() {
        if (activity != null)
            activity.checkBack();
    }

    @Override
    public void callPopup(String name,LevelCustomerModel model) {
        callDataCustomerById(name,model.getId());
    }
    private void callDataCustomerById(String name,String id) {
        showProgress();
        CustomerRequest.ApiParams params = new CustomerRequest.ApiParams();
        params.id_business = AppProvider.getPreferences().getUserModel().getId_business();
        if (id != null) {
            params.level_id = id;
        }
        AppProvider.getApiManagement().call(CustomerRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<CustomerModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<CustomerModel> body) {
                if (body != null) {
                    dismissProgress();
                    showPopup(name,body.getData());
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

    private void showPopup(String name,CustomerModel[] list) {
        if (activity!=null){
            activity.replaceFragment(new FragmentCustomerList().newInstance(name,list),true);
        }
    }

    @Override
    public void goHome() {
        if (activity != null)
            activity.replaceFragment(new FragmentHome(), false);
    }
}
