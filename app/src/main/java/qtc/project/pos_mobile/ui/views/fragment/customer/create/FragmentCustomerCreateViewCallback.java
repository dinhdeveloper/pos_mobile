package qtc.project.pos_mobile.ui.views.fragment.customer.create;

import qtc.project.pos_mobile.model.CustomerModel;

public interface FragmentCustomerCreateViewCallback {
    void onBackP();

    void createCustomer(CustomerModel customerModel);
}
