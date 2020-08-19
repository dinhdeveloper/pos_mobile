package qtc.project.pos_tablet.ui.views.fragment.home;

import java.util.ArrayList;

import qtc.project.pos_tablet.model.CategoryModel;
import qtc.project.pos_tablet.model.ListOrderModel;

public interface FragmentHomeViewCallback {
    void  onChangToCategoryDetail(CategoryModel model);
    void callPopup();

    void callAllData();

    void tinhTien(ArrayList<ListOrderModel> arList,String level,String tongtien, String discount);

    void callDataSearchProduct(String search, int status);

    void callDataSearchCus(String toString);

    void callAllDataCustomer();

    void loadMore();

    void resetPage();
}
