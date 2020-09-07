package qtc.project.pos_mobile.ui.views.admin.activity.home;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos_mobile.activity.admin.HomeAdminActivity;

public interface HomeAdminActivityViewInterface extends BaseViewInterface {

    void init(HomeAdminActivity activity,HomeAdminActivityViewCallback callback);

    void openDrawer();

    void closeDrawer();

    boolean isDrawerOpen();

    void updatePopup();

    void toggleNav();
}
