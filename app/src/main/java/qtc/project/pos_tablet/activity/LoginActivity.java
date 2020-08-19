package qtc.project.pos_tablet.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragmentActivity;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import b.laixuantam.myaarlibrary.widgets.dialog.alert.KAlertDialog;
import qtc.project.pos_tablet.R;
import qtc.project.pos_tablet.api.account.login.LoginRequest;
import qtc.project.pos_tablet.dependency.AppProvider;
import qtc.project.pos_tablet.model.BaseResponseModel;
import qtc.project.pos_tablet.model.EmployeeModel;
import qtc.project.pos_tablet.ui.views.action_bar.base_main_actionbar.BaseMainActionbarViewInterface;
import qtc.project.pos_tablet.ui.views.fragment.account.login.LoginView;
import qtc.project.pos_tablet.ui.views.fragment.account.login.LoginViewCallback;
import qtc.project.pos_tablet.ui.views.fragment.account.login.LoginViewInterface;

//public class LoginActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        LinearLayout btnLogin = findViewById(R.id.btnLoginButton);
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//            }
//        });
//    }
//}


public class LoginActivity extends BaseFragmentActivity<LoginViewInterface, BaseMainActionbarViewInterface, BaseParameters> implements LoginViewCallback {

    private LoginActivity activity;
    private CallbackManager callbackManager;

    @Override
    protected void initialize(Bundle savedInstanceState) {
        activity = LoginActivity.this;
        view.initialize(activity, this);
        if (AppProvider.getPreferences().getUserModel() != null) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
    }


    @Override
    protected LoginViewInterface getViewInstance() {
        return new LoginView();
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
        return R.id.activityLogin;
    }

    @Override
    public void onClickLogin(String username, String password) {
        requestLogin(username, password);
    }

    private void requestLogin(String username, String password) {
        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
            showAlert(getString(R.string.error_connect_internet), KAlertDialog.ERROR_TYPE);
            return;
        }
        showProgress(getString(R.string.loading));
        LoginRequest.ApiParams params = new LoginRequest.ApiParams();
        params.id_code = username;
        params.detect = "employee_login";
        params.password = password;
        AppProvider.getApiManagement().call(LoginRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<EmployeeModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<EmployeeModel> result) {
                dismissProgress();

                if (!TextUtils.isEmpty(result.getSuccess()) && Objects.requireNonNull(result.getSuccess()).equalsIgnoreCase("true")) {

                    AppProvider.getPreferences().saveStatusLogin(true);
                    EmployeeModel userModel = result.getData()[0];
                    FirebaseMessaging.getInstance().subscribeToTopic("pos_notifycation_employee_" + userModel.getId());
                    FirebaseMessaging.getInstance().subscribeToTopic("pos_notifycation_app_tablet");
                    if (result.getData() != null && result.getData().length > 0) {
                        AppProvider.getPreferences().saveUserModel(userModel);
                    }
                    if (activity != null) {
                        activity.goToHome();
                    }

                } else {
                    if (!TextUtils.isEmpty(result.getMessage())) {
                        LayoutInflater layoutInflater = activity.getLayoutInflater();
                        View popupView = layoutInflater.inflate(R.layout.alert_dialog_login, null);

                        TextView title_text = popupView.findViewById(R.id.title_text);
                        TextView content_text = popupView.findViewById(R.id.content_text);
                        Button custom_confirm_button = popupView.findViewById(R.id.custom_confirm_button);
                        title_text.setText("Xác nhận");
                        content_text.setText(result.getMessage());
                        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                        alert.setView(popupView);
                        AlertDialog dialog = alert.create();
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();

                        custom_confirm_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                    }
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

    private void goToHome() {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }


    @Override
    public void onClickBackHeader() {

    }


}