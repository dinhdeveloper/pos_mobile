package qtc.project.pos_mobile.fragment.order;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import b.laixuantam.myaarlibrary.helper.KeyboardUtils;
import qtc.project.pos_mobile.activity.HomeActivity;
import qtc.project.pos_mobile.api.order.OrderRequest;
import qtc.project.pos_mobile.dependency.AppProvider;
import qtc.project.pos_mobile.model.BaseResponseModel;
import qtc.project.pos_mobile.model.OrderModel;
import qtc.project.pos_mobile.ui.views.fragment.order.filter.FragmentFilterOrderView;
import qtc.project.pos_mobile.ui.views.fragment.order.filter.FragmentFilterOrderViewCallback;
import qtc.project.pos_mobile.ui.views.fragment.order.filter.FragmentFilterOrderViewInterface;

public class FragmentFilterOrder extends BaseFragment<FragmentFilterOrderViewInterface, BaseParameters> implements FragmentFilterOrderViewCallback {
    HomeActivity activity;
    @Override
    protected void initialize() {
        activity =(HomeActivity)getActivity();
        view.init(activity,this);
        KeyboardUtils.setupUI(getView(),activity);
    }

    @Override
    protected FragmentFilterOrderViewInterface getViewInstance() {
        return new FragmentFilterOrderView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void goBackP() {
        if (activity!=null)
            activity.checkBack();
    }

    @Override
    public void filterData(String dates, String id_order) {
        showProgress();
        OrderRequest.ApiParams params = new OrderRequest.ApiParams();
        params.id_business = AppProvider.getPreferences().getUserModel().getId_business();
        if (dates != null || id_order != null) {
            params.time_filter = dates;
            params.order_code = id_order;
        }
        AppProvider.getApiManagement().call(OrderRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<OrderModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<OrderModel> body) {
                dismissProgress();
                if (activity != null) {
                    activity.checkBack();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            activity.setDataOrder(body.getData());
                        }
                    }, 100);
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
