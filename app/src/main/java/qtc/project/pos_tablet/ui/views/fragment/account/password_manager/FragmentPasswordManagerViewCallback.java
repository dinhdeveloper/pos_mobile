package qtc.project.pos_tablet.ui.views.fragment.account.password_manager;

public interface FragmentPasswordManagerViewCallback {
    void onClickBackHeader();

    void onSubmitChangePassword(String oldPassword, String newPass);

    void onClickForgotPassword();
}
