package qtc.project.pos_mobile.activity;


import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseFragmentActivity;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import b.laixuantam.myaarlibrary.widgets.dialog.alert.KAlertDialog;
import qtc.project.pos_mobile.R;
import qtc.project.pos_mobile.api.emplooyee.EmployeeRequest;
import qtc.project.pos_mobile.dependency.AppProvider;
import qtc.project.pos_mobile.event.FroceSignoutEvent;
import qtc.project.pos_mobile.event.StatusEmployeeEvent;
import qtc.project.pos_mobile.fragment.home.FragmentHome;
import qtc.project.pos_mobile.fragment.order.FragmentFilterOrder;
import qtc.project.pos_mobile.fragment.order.FragmentOrder;
import qtc.project.pos_mobile.model.BaseResponseModel;
import qtc.project.pos_mobile.model.CustomerModel;
import qtc.project.pos_mobile.model.EmployeeModel;
import qtc.project.pos_mobile.model.OrderModel;
import qtc.project.pos_mobile.model.ProductModel;
import qtc.project.pos_mobile.ui.views.action_bar.base_main_actionbar.BaseMainActionbarViewInterface;
import qtc.project.pos_mobile.ui.views.activity.home_activity.HomeActivityView;
import qtc.project.pos_mobile.ui.views.activity.home_activity.HomeActivityViewCallback;
import qtc.project.pos_mobile.ui.views.activity.home_activity.HomeActivityViewInterface;

public class HomeActivity extends BaseFragmentActivity<HomeActivityViewInterface, BaseMainActionbarViewInterface, BaseParameters> implements HomeActivityViewCallback {

    @Override
    protected void initialize(Bundle savedInstanceState) {
        view.init(this,this);
        setLayoutMain();

        FullScreencall();

//        BarcodeDetector detector =
//                new BarcodeDetector.Builder(getApplicationContext())
//                        .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
//                        .build();
//        if(!detector.isOperational()){
//            Toast.makeText(getApplicationContext(), "khong thiet lap may do", Toast.LENGTH_SHORT).show();
//            return;
//        }
    }


    @Override
    protected void onResume() {
        super.onResume();
//        EmployeeRequest.ApiParams params = new EmployeeRequest.ApiParams();
//        params.type_manager = "check_status";
//        params.id_employee = AppProvider.getPreferences().getUserModel().getId();
//
//        AppProvider.getApiManagement().call(EmployeeRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<EmployeeModel>>() {
//            @Override
//            public void onSuccess(BaseResponseModel<EmployeeModel> body) {
//                if (body != null) {
//                    List<EmployeeModel> modelList = new ArrayList<>();
//                    modelList.addAll(Arrays.asList(body.getData()));
//                    if (modelList.get(0).getStatus().equals("N")) {
//
//                        LayoutInflater layoutInflater = getLayoutInflater();
//                        View popupView = layoutInflater.inflate(R.layout.alert_dialog_canhbao, null);
//                        TextView title_text = popupView.findViewById(R.id.title_text);
//                        TextView content_text = popupView.findViewById(R.id.content_text);
//                        Button cancel_button = popupView.findViewById(R.id.cancel_button);
//                        Button custom_confirm_button = popupView.findViewById(R.id.custom_confirm_button);
//
//                        title_text.setVisibility(View.GONE);
//                        content_text.setText("Tài khoản đã bị khóa.");
//                        //title_text.setText("Cảnh báo");
//                        //content_text.setText("Bạn có muốn xóa khách hàng này không?");
//
//                        AlertDialog.Builder alert = new AlertDialog.Builder(HomeActivity.this);
//                        alert.setView(popupView);
//                        AlertDialog dialog = alert.create();
//                        dialog.setCanceledOnTouchOutside(false);
//                        dialog.show();
//
//                        custom_confirm_button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                EmployeeModel employeeModel = AppProvider.getPreferences().getUserModel();
//                                if (employeeModel != null) {
//                                    FirebaseMessaging.getInstance().unsubscribeFromTopic("pos_notifycation_employee_" + employeeModel.getId());
//                                    FirebaseMessaging.getInstance().unsubscribeFromTopic("pos_notifycation_app_admin");
//                                }
//                                AppProvider.getPreferences().saveUserModel(null);
//                                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
//                                dialog.dismiss();
//                                finish();
//
//                            }
//                        });
//
//                    }
//                }
//            }
//
//            @Override
//            public void onError(ErrorApiResponse error) {
//
//            }
//
//            @Override
//            public void onFail(ApiRequest.RequestError error) {
//
//            }
//        });
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onKeyboardDissmiss(StatusEmployeeEvent event) {
//        if (view != null) {
//            LayoutInflater layoutInflater = getLayoutInflater();
//            View popupView = layoutInflater.inflate(R.layout.alert_dialog_canhbao, null);
//            TextView title_text = popupView.findViewById(R.id.title_text);
//            TextView content_text = popupView.findViewById(R.id.content_text);
//            Button cancel_button = popupView.findViewById(R.id.cancel_button);
//            Button custom_confirm_button = popupView.findViewById(R.id.custom_confirm_button);
//
//            title_text.setVisibility(View.GONE);
//            content_text.setText("Tài khoản đã bị khóa.");
//            //title_text.setText("Cảnh báo");
//            //content_text.setText("Bạn có muốn xóa khách hàng này không?");
//
//            AlertDialog.Builder alert = new AlertDialog.Builder(HomeActivity.this);
//            alert.setView(popupView);
//            AlertDialog dialog = alert.create();
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();
//
//
//            custom_confirm_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    EmployeeModel employeeModel = AppProvider.getPreferences().getUserModel();
//                    if (employeeModel != null) {
//                        FirebaseMessaging.getInstance().unsubscribeFromTopic("pos_notifycation_employee_" + employeeModel.getId());
//                    }
//                    AppProvider.getPreferences().saveUserModel(null);
//                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
//                    dialog.dismiss();
//                    finish();
//
//                }
//            });
//        }
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onKeyboardForcSignout(FroceSignoutEvent event) {
//        if (view != null) {
//            LayoutInflater layoutInflater = getLayoutInflater();
//            View popupView = layoutInflater.inflate(R.layout.alert_dialog_canhbao, null);
//            TextView title_text = popupView.findViewById(R.id.title_text);
//            TextView content_text = popupView.findViewById(R.id.content_text);
//            Button cancel_button = popupView.findViewById(R.id.cancel_button);
//            Button custom_confirm_button = popupView.findViewById(R.id.custom_confirm_button);
//
//            title_text.setVisibility(View.GONE);
//            content_text.setText("Tài khoản đã bị khóa.");
//            //title_text.setText("Cảnh báo");
//            //content_text.setText("Bạn có muốn xóa khách hàng này không?");
//
//            AlertDialog.Builder alert = new AlertDialog.Builder(HomeActivity.this);
//            alert.setView(popupView);
//            AlertDialog dialog = alert.create();
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();
//
//
//            custom_confirm_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    EmployeeModel employeeModel = AppProvider.getPreferences().getUserModel();
//                    if (employeeModel != null) {
//                        FirebaseMessaging.getInstance().unsubscribeFromTopic("pos_notifycation_app_admin");
//                    }
//                    AppProvider.getPreferences().saveUserModel(null);
//                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
//                    dialog.dismiss();
//                    finish();
//
//                }
//            });
//        }
    }



    private void setLayoutMain() {
        FullScreencall();
        replaceFragment(new FragmentHome(),false,Animation.SLIDE_IN_OUT);
    }

    @Override
    protected HomeActivityViewInterface getViewInstance() {
        return new HomeActivityView();
    }

    @Override
    protected BaseMainActionbarViewInterface getActionbarInstance() {
        return null;
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.content_frame;
    }

    @Override
    public void onClickItemNav(Fragment fragment) {
        replaceFragment((BaseFragment<?, ?>) fragment,false,Animation.SLIDE_IN_OUT);
    }

    @Override
    public void logOut() {
        AppProvider.getPreferences().clear();
        finish();
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
    }

    public void FullScreencall() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    public void onClickBottomBarMenuHome() {

    }

    @Override
    public void onClickBottomBarMenuCategory() {

    }

    @Override
    public void onClickBottomBarMenuAccount() {

    }

    @Override
    public void onClickBottomBarMenuShoppingCart() {

    }

    @Override
    public void onClickBottomBarMenuTransaction() {

    }

    @Override
    public void onClickBottomBarMenuOrder() {

    }

    public void toggleNav(){
        view.toggleNav();
    }

    private KAlertDialog mCustomAlert;

    public void showAlert(String title, String message, int type) {

        if (mCustomAlert == null) {
            mCustomAlert = new KAlertDialog(this);
            mCustomAlert.setCancelable(false);
            mCustomAlert.setCanceledOnTouchOutside(false);
        }
        mCustomAlert.showCancelButton(false);

        mCustomAlert.setTitleText(title);

        mCustomAlert
                .setContentText(message)
                .setConfirmText("OK")
                .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog kAlertDialog) {
                        if (mCustomAlert != null)
                            mCustomAlert.dismiss();
                    }
                }).changeAlertType(type);
        mCustomAlert.show();
    }

    public void showAlert(String message) {
        showAlert("", message, 0);
    }

    public void showConfirmAlert(String title, String mess, KAlertDialog.KAlertClickListener actionConfirm, int type) {
        showConfirmAlert(title, mess, "", "", actionConfirm, null, type);
    }

    public void showConfirmAlert(String title, String mess, KAlertDialog.KAlertClickListener actionConfirm, KAlertDialog.KAlertClickListener actionCancel, int type) {
        showConfirmAlert(title, mess, "", "", actionConfirm, actionCancel, type);
    }

    public void showConfirmAlert(String title, String mess, String titleButtonConfirm, String titleButtonCancel, KAlertDialog.KAlertClickListener actionConfirm, KAlertDialog.KAlertClickListener actionCancel, int type) {
        if (mCustomAlert == null) {
            mCustomAlert = new KAlertDialog(this);
            mCustomAlert.setCancelable(false);
            mCustomAlert.setCanceledOnTouchOutside(false);
        }

        mCustomAlert.setConfirmText(getString(R.string.KAlert_confirm_button_text));

        mCustomAlert.setTitleText(Html.fromHtml(title).toString());

        mCustomAlert.setContentText(Html.fromHtml(mess).toString());

        if (!TextUtils.isEmpty(titleButtonConfirm)) {
            mCustomAlert.setConfirmText(titleButtonConfirm);
        } else {
            mCustomAlert.setConfirmText(getString(R.string.KAlert_confirm_button_text));
        }

        switch (type) {
            case KAlertDialog.SUCCESS_TYPE:
                mCustomAlert.setCustomImage(R.drawable.ic_success);
                mCustomAlert.setConfirmText("Tiếp tục");
                break;
            case KAlertDialog.WARNING_TYPE:
                mCustomAlert.setCustomImage(R.drawable.ic_success);
                break;
        }

        mCustomAlert.changeAlertType(KAlertDialog.CUSTOM_IMAGE_TYPE);

        if (actionCancel != null) {
            mCustomAlert.setCancelClickListener(actionCancel);

            if (!TextUtils.isEmpty(titleButtonCancel)) {
                mCustomAlert.setCancelText(titleButtonCancel);
            } else {
                mCustomAlert.setCancelText(getString(R.string.KAlert_cancel_button_text));
            }
        } else {
            mCustomAlert.showCancelButton(false);
        }
        if (actionConfirm != null) {
            mCustomAlert.setConfirmClickListener(actionConfirm);
        } else {
            mCustomAlert.setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                @Override
                public void onClick(KAlertDialog kAlertDialog) {
                    mCustomAlert.dismiss();
                }
            });
        }
        mCustomAlert.show();
    }

    public void checkBack() {
        FullScreencall();

        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        }

    }

    public void setDataOrder(OrderModel[] list) {
        BaseFragment baseFragment = getCurrentFragment();
        if (baseFragment instanceof FragmentOrder) {
            ((FragmentOrder) baseFragment).filterData(list);
        }
    }

    public void setDataCustomer(CustomerModel model) {
        BaseFragment baseFragment = getCurrentFragment();
        if (baseFragment instanceof FragmentHome) {
            ((FragmentHome) baseFragment).setDataCustomer(model);
        }
    }

    public void setDataHome(ProductModel model) {
        BaseFragment baseFragment = getCurrentFragment();
        if (baseFragment instanceof FragmentHome) {
            ((FragmentHome) baseFragment).setDataProduct(model);
        }
    }
}