package qtc.project.pos_mobile.ui.views.fragment.order.detail;

public interface FragmentOrderDetailViewCallback {
    void onBackP();

    void cancelOrder(String id_order);

    void reQuestData();
}
