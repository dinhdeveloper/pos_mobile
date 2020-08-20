package qtc.project.pos_mobile.fragment.order;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos_mobile.activity.HomeActivity;
import qtc.project.pos_mobile.api.order.CancelOrderRequest;
import qtc.project.pos_mobile.api.order.OrderRequest;
import qtc.project.pos_mobile.dependency.AppProvider;
import qtc.project.pos_mobile.model.BaseResponseModel;
import qtc.project.pos_mobile.model.OrderModel;
import qtc.project.pos_mobile.ui.views.fragment.order.detail.FragmentOrderDetailView;
import qtc.project.pos_mobile.ui.views.fragment.order.detail.FragmentOrderDetailViewCallback;
import qtc.project.pos_mobile.ui.views.fragment.order.detail.FragmentOrderDetailViewInterface;

public class FragmentOrderDetail extends BaseFragment<FragmentOrderDetailViewInterface, BaseParameters> implements FragmentOrderDetailViewCallback {
    HomeActivity activity;

    public static  FragmentOrderDetail newInstance(OrderModel model){
        FragmentOrderDetail fm = new FragmentOrderDetail();
        Bundle bundle = new Bundle();
        bundle.putSerializable("model",model);
        fm.setArguments(bundle);

        return fm;
    }
    @Override
    protected void initialize() {
        activity =(HomeActivity)getActivity();
        view.init(activity,this);
        
        getBundle();
    }

    private void getBundle() {
        Bundle bundle = getArguments();
        if (bundle!=null){
            OrderModel model = (OrderModel)bundle.get("model");
            view.initView(model);
        }
    }

    @Override
    protected FragmentOrderDetailViewInterface getViewInstance() {
        return new FragmentOrderDetailView();
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
    public void cancelOrder(String id_order) {
        showProgress();
        CancelOrderRequest.ApiParams params = new CancelOrderRequest.ApiParams();
        if (id_order != null) {
            params.id_order = id_order;
        }
        AppProvider.getApiManagement().call(CancelOrderRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel>() {
            @Override
            public void onSuccess(BaseResponseModel body) {
                if (body.getSuccess().equalsIgnoreCase("true")){
                    dismissProgress();
                    view.showSuccess();
                }else if (body.getSuccess().equalsIgnoreCase("false")){
                    Toast.makeText(activity, ""+body.getMessage(), Toast.LENGTH_SHORT).show();
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
    public void reQuestData() {
//        showProgress();
//        OrderRequest.ApiParams params = new OrderRequest.ApiParams();
//        AppProvider.getApiManagement().call(OrderRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<OrderModel>>() {
//            @Override
//            public void onSuccess(BaseResponseModel<OrderModel> body) {
//                if (body != null) {
//                    dismissProgress();
//                    ArrayList<OrderModel> list = new ArrayList<>();
//                    list.addAll(Arrays.asList(body.getData()));
//                    view.initView(list);
//                }
//            }
//
//            @Override
//            public void onError(ErrorApiResponse error) {
//                dismissProgress();
//                Log.e("onError", error.message);
//            }
//
//            @Override
//            public void onFail(ApiRequest.RequestError error) {
//                dismissProgress();
//                Log.e("onFail", error.name());
//            }
//        });
    }
}
