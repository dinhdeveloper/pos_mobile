package qtc.project.pos_mobile.ui.views.fragment.customer;

import qtc.project.pos_mobile.model.CustomerModel;

public interface FragmentCustomerViewCallback {
    void goHome();

    void goDetail(CustomerModel model);

    void addCustomer();

    void callAllDataCustomer();

    void callDataSearchCus(String toString);

    void callNav();
}
