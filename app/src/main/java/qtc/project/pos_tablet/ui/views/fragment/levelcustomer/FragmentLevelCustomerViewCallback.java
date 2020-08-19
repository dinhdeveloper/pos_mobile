package qtc.project.pos_tablet.ui.views.fragment.levelcustomer;

import qtc.project.pos_tablet.model.LevelCustomerModel;

public interface FragmentLevelCustomerViewCallback {
    void callPopup(LevelCustomerModel model);

    void eventBackHome();

    void eventSearchCustomer(String toString,String id);

    void getgetTotalCustomer(String id_level);

    void getAllDataById(String id_level);
}
