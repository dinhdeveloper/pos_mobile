package qtc.project.pos_tablet.fragment.order;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos_tablet.activity.HomeActivity;
import qtc.project.pos_tablet.api.order.CancelOrderRequest;
import qtc.project.pos_tablet.api.order.OrderRequest;
import qtc.project.pos_tablet.dependency.AppProvider;
import qtc.project.pos_tablet.fragment.home.FragmentHome;
import qtc.project.pos_tablet.model.BaseResponseModel;
import qtc.project.pos_tablet.model.OrderModel;
import qtc.project.pos_tablet.ui.views.fragment.order.FragmentOrderView;
import qtc.project.pos_tablet.ui.views.fragment.order.FragmentOrderViewCallback;
import qtc.project.pos_tablet.ui.views.fragment.order.FragmentOrderViewInterface;

public class FragmentOrder extends BaseFragment<FragmentOrderViewInterface, BaseParameters> implements FragmentOrderViewCallback {
    HomeActivity activity;
    @Override
    protected void initialize() {
         activity = (HomeActivity) getActivity();
        view.init(activity, this);

        requestData();
    }

    private void requestData() {
        showProgress();
        OrderRequest.ApiParams params = new OrderRequest.ApiParams();
        AppProvider.getApiManagement().call(OrderRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<OrderModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<OrderModel> body) {
                if (body != null) {
                    dismissProgress();
                    ArrayList<OrderModel> list = new ArrayList<>();
                    list.addAll(Arrays.asList(body.getData()));
                    view.initRecyclerViewOrder(list);
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
    protected FragmentOrderViewInterface getViewInstance() {
        return new FragmentOrderView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
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
    public void filterData(String dates, String id_order) {
        showProgress();
        OrderRequest.ApiParams params = new OrderRequest.ApiParams();
        if (dates != null || id_order != null) {
            params.time_filter = dates;
            params.order_code = id_order;
        }
        AppProvider.getApiManagement().call(OrderRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<OrderModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<OrderModel> body) {
                dismissProgress();
                ArrayList<OrderModel> list = new ArrayList<>();
                list.addAll(Arrays.asList(body.getData()));
                view.initRecyclerViewOrder(list);
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
        showProgress();
        OrderRequest.ApiParams params = new OrderRequest.ApiParams();
        AppProvider.getApiManagement().call(OrderRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<OrderModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<OrderModel> body) {
                if (body != null) {
                    dismissProgress();
                    ArrayList<OrderModel> list = new ArrayList<>();
                    list.addAll(Arrays.asList(body.getData()));
                    view.initRecyclerViewOrder(list);
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
    public void searchOrder(String search) {
        showProgress();
        OrderRequest.ApiParams params = new OrderRequest.ApiParams();
        params.order_code = search;

        AppProvider.getApiManagement().call(OrderRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<OrderModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<OrderModel> body) {
                if (body != null) {
                    dismissProgress();
                    ArrayList<OrderModel> list = new ArrayList<>();
                    list.addAll(Arrays.asList(body.getData()));
                    view.initRecyclerViewOrder(list);
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
        if (activity!=null)
            activity.replaceFragment(new FragmentHome(),false);
    }
}
