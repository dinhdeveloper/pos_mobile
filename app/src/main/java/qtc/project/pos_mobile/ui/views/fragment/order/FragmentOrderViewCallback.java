package qtc.project.pos_mobile.ui.views.fragment.order;

import qtc.project.pos_mobile.model.OrderModel;

public interface FragmentOrderViewCallback {
    void goToDetail(OrderModel model);

    void goHome();

    void filter();

    void reQuestData();

    void searchOrder(String search);

    void loadMore();

    void callNav();
}
