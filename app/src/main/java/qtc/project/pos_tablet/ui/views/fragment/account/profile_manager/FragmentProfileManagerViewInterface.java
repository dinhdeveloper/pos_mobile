package qtc.project.pos_tablet.ui.views.fragment.account.profile_manager;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos_tablet.activity.HomeActivityBackup;

public interface FragmentProfileManagerViewInterface extends BaseViewInterface {
    void init(HomeActivityBackup activity, FragmentProfileManagerViewCallback callback);

    void setDataUserImage(String outfile);

    void configTypeShowUpdateInfo(String type);
}
