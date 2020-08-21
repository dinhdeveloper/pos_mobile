package qtc.project.pos_mobile.ui.views.fragment.level.customerById;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos_mobile.R;
import qtc.project.pos_mobile.activity.HomeActivity;
import qtc.project.pos_mobile.adapter.customer.CustomerAdapter;
import qtc.project.pos_mobile.adapter.level.CustomerLevelAdapter;
import qtc.project.pos_mobile.adapter.order.OrderAdapter;
import qtc.project.pos_mobile.model.CustomerModel;

public class FragmentCustomerListView extends BaseView<FragmentCustomerListView.UIContainer> implements FragmentCustomerListViewInterface{
    HomeActivity activity;
    FragmentCustomerListViewCallback callback;
    boolean enableLoadMore = true;
    CustomerLevelAdapter adapter;
    ArrayList<CustomerModel> arrayList = new ArrayList<>();
    String level_id;
    @Override
    public void init(HomeActivity activity, FragmentCustomerListViewCallback callback) {
        this.activity = activity;
        this.callback = callback;
        initRecycle();

        ui.imageNavLeft.setOnClickListener(v -> {
            if (callback!=null)
                callback.onBackP();
        });

        onClick();
    }

    private void onClick() {
        ui.image_close.setOnClickListener(v -> {
            enableLoadMore = true;
            ui.edit_filter.setText(null);
            if (callback!=null){
                callback.getAllData();
            }
        });

        ui.edit_filter.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    searchData(ui.edit_filter.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    private void searchData(String  search) {
        if (callback!=null)
            callback.seachCustomer(search,level_id);
    }

    private void initRecycle() {
        ui.recycler_view_list_customer.getRecycledViewPool().clear();
        adapter = new CustomerLevelAdapter(activity,arrayList);
        ui.recycler_view_list_customer.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        ui.recycler_view_list_customer.setAdapter(adapter);
    }

    @Override
    public void initView(String name,CustomerModel[] list) {
        if (list == null || list.length == 0) {
            if (arrayList.size() == 0)
                showEmptyList();
            return;
        }
        if (name!=null){
            ui.title_header.setText(name);
        }
        arrayList.addAll(Arrays.asList(list));
        adapter.notifyDataSetChanged();

        if (arrayList!=null){
            for (CustomerModel model:arrayList){
                level_id = model.getLevel_id();
            }
        }
    }

    @Override
    public void resetData() {
        arrayList.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void clearnData() {
        arrayList.clear();
        adapter.notifyDataSetChanged();
    }

    private void showEmptyList() {
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentCustomerListView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.custom_popup_level_detail;
    }



    public class UIContainer extends BaseUiContainer {
        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.title_header)
        public TextView title_header;

        @UiElement(R.id.imvHome)
        public ImageView imvHome;

        @UiElement(R.id.edit_filter)
        public EditText edit_filter;

        @UiElement(R.id.image_close)
        public ImageView image_close;

        @UiElement(R.id.recycler_view_list_customer)
        public RecyclerView recycler_view_list_customer;


    }
}
