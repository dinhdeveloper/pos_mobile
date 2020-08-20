package qtc.project.pos_mobile.fragment.level;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos_mobile.activity.HomeActivity;
import qtc.project.pos_mobile.api.customer.LevelCustomerRequest;
import qtc.project.pos_mobile.dependency.AppProvider;
import qtc.project.pos_mobile.fragment.home.FragmentHome;
import qtc.project.pos_mobile.model.BaseResponseModel;
import qtc.project.pos_mobile.model.LevelCustomerModel;
import qtc.project.pos_mobile.ui.views.fragment.level.FragmentLevelCustomerView;
import qtc.project.pos_mobile.ui.views.fragment.level.FragmentLevelCustomerViewCallback;
import qtc.project.pos_mobile.ui.views.fragment.level.FragmentLevelCustomerViewInterface;

public class FragmentLevelCustomer extends BaseFragment<FragmentLevelCustomerViewInterface, BaseParameters> implements FragmentLevelCustomerViewCallback {
    HomeActivity activity;

    @Override
    protected void initialize() {
        activity = (HomeActivity)getActivity();
        view.init(activity,this);
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
    public void goToLevelDetail(LevelCustomerModel model) {
        if (activity!=null)
            activity.replaceFragment(new FragmentLevelDetail().newInstance(model),true);
    }

    @Override
    public void goHome() {
        if (activity!=null)
            activity.replaceFragment(new FragmentHome(), false);
    }

    @Override
    public void callNav() {
        if (activity!=null)
            activity.toggleNav();
    }
}
