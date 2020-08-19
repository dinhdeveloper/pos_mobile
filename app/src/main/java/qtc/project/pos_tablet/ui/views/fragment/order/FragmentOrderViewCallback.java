package qtc.project.pos_tablet.ui.views.fragment.order;

public interface FragmentOrderViewCallback {
    void cancelOrder(String id_order);

    void filterData(String date,String id_order);

    void reQuestData();

    void searchOrder(String search);

    void goHome();
}
