package qtc.project.pos_tablet.ui.views.fragment.account.forgot_password;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos_tablet.activity.HomeActivityBackup;

public interface FragmentForgotPasswordViewInterface extends BaseViewInterface {

    void init(HomeActivityBackup activity, FragmentForgotPasswordViewCallback callback);

    void checkPhoneRegisterSuccessExists();

    void setCodeSMS(String code);

    void verifySMSSuccess();

    void verifySMSFailed();
}
