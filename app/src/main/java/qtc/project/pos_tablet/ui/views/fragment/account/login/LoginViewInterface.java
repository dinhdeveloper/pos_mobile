package qtc.project.pos_tablet.ui.views.fragment.account.login;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos_tablet.activity.LoginActivity;

public interface LoginViewInterface extends BaseViewInterface {
    void initialize(LoginActivity activity,LoginViewCallback callback);

    void showRootViewLogin();

    void hideRootViewLogin();
}
