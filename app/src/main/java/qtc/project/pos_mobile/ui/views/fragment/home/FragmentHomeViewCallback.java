package qtc.project.pos_mobile.ui.views.fragment.home;

import java.util.ArrayList;

import qtc.project.pos_mobile.model.ListOrderModel;

public interface FragmentHomeViewCallback {
    void goToChooseCustomer();

    void callDataSearchProduct(String id_code);

    void tinhTien(ArrayList<ListOrderModel> arList, String id_customer, String tongtien, String discount);

    void goToProductList(String toString);

    void callNav();
}
