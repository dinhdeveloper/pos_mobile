package qtc.project.pos_mobile.ui.views.admin.activity.home;

import android.support.v4.app.Fragment;

public interface HomeAdminActivityViewCallback {
    void onClickBottomBarMenuHome();

    void onClickBottomBarMenuCategory();

    void onClickBottomBarMenuAccount();

    void onClickBottomBarMenuShoppingCart();

    void onClickBottomBarMenuTransaction();

    void onClickBottomBarMenuOrder();

    void onClickItemNav(Fragment fragment);

    void logOut();

    void onClickLogin(String oldPass, String newPass,String employee_id);

    void goToFragmentInfoUser();
}
