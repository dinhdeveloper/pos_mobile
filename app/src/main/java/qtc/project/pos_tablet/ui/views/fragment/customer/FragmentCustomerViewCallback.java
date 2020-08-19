package qtc.project.pos_tablet.ui.views.fragment.customer;

import qtc.project.pos_tablet.model.CustomerModel;

public interface FragmentCustomerViewCallback {
    void updateCustomer(CustomerModel customerModel);
    void createCustomer(CustomerModel customerModel);

    void callDataSearchCus(String toString);

    void callAllDataCustomer();

    void goHome();
}
