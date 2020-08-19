package qtc.project.pos_tablet.ui.views.fragment.notification;

public interface FragmentNotificationViewCallback {
    void onClickBackHeader();

    void onRequestRefreshListNotification();

    void onRequestLoadMoreListNotification();

    void onRequestCheckViewNotify(String id);

    void onClickShowShoppingCart();

    void onClickFilterProduct();
}
