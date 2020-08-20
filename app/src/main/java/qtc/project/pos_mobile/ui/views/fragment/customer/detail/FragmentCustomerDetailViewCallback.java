package qtc.project.pos_mobile.ui.views.fragment.customer.detail;

import qtc.project.pos_mobile.model.CustomerModel;

public interface FragmentCustomerDetailViewCallback {
    void onBackP();

    void updateCustomer(CustomerModel customerModel,String id);
}
