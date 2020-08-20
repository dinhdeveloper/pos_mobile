package qtc.project.pos_mobile.ui.views.fragment.customer.filter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos_mobile.R;
import qtc.project.pos_mobile.activity.HomeActivity;
import qtc.project.pos_mobile.adapter.home.ListCustomerFHomeAdapter;
import qtc.project.pos_mobile.model.CustomerModel;

public class FragmentCustomerFilterView extends BaseView<FragmentCustomerFilterView.UIContainer> implements FragmentCustomerFilterViewInterface {
    HomeActivity activity;
    FragmentCustomerFilterViewCallback callback;

    @Override
    public void init(HomeActivity activity, FragmentCustomerFilterViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        ui.btnExit.setOnClickListener(v -> {
            if (callback!=null)
                callback.onBackP();
        });

        ui.imageNavLeft.setOnClickListener(v -> {
            if (callback!=null)
                callback.onBackP();
        });
        ui.title_header.setText("Danh sách khách hàng");
    }

    @Override
    public void initCustomer(ArrayList<CustomerModel> list) {
        if (list != null) {
            ui.tvTotal.setText("Có tất cả "+list.size()+" khách hàng");
            ui.recycler_view_list_customer.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            ListCustomerFHomeAdapter infoAdapter = new ListCustomerFHomeAdapter(activity, list);
            ui.recycler_view_list_customer.setAdapter(infoAdapter);
            infoAdapter.notifyDataSetChanged();

            infoAdapter.setListener(model -> {
                ui.btnOk.setOnClickListener(v -> {
                    if (callback!=null)
                        callback.setCustomerToHome(model);
                });

                ui.btnExit.setOnClickListener(v -> {
                    if (callback!=null)
                        callback.onBackP();
                });
            });
        }
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentCustomerFilterView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_customer_filter;
    }


    public class UIContainer extends BaseUiContainer {
        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.title_header)
        public TextView title_header;

        @UiElement(R.id.tvTotal)
        public TextView tvTotal;

        @UiElement(R.id.imvHome)
        public ImageView imvHome;

        @UiElement(R.id.text_search)
        public EditText text_search;

        @UiElement(R.id.image_close)
        public ImageView image_close;

        @UiElement(R.id.recycler_view_list_customer)
        public RecyclerView recycler_view_list_customer;

        @UiElement(R.id.btnExit)
        public LinearLayout btnExit;

        @UiElement(R.id.btnOk)
        public LinearLayout btnOk;

    }
}
