package qtc.project.pos_tablet.ui.views.fragment.account.password_manager;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos_tablet.activity.HomeActivityBackup;

public interface FragmentPasswordManagerViewInterface extends BaseViewInterface {
    void init(HomeActivityBackup activity, FragmentPasswordManagerViewCallback callback);

    void validateUpdatePasswordSuccess();
}
