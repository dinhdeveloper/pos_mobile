package qtc.project.pos_tablet.ui.views.fragment.notification;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos_tablet.activity.HomeActivityBackup;
import qtc.project.pos_tablet.model.NotificationModel;

public interface FragmentNotificationViewInterface extends BaseViewInterface {

    void init(HomeActivityBackup activity, FragmentNotificationViewCallback callback);

    void setDataNotification(NotificationModel[] data);

    void setNoMoreLoading();

    void validateCheckSeenNotifySuccess();

    void hideRootView();

    void showRootView();
}
