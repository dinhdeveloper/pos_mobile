package qtc.project.pos_tablet.ui.views.fragment.levelcustomer;


import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import b.laixuantam.myaarlibrary.dependency.AppProvider;
import qtc.project.pos_tablet.R;
import qtc.project.pos_tablet.activity.HomeActivity;
import qtc.project.pos_tablet.adapter.customer.LevelCustomerAdapter;
import qtc.project.pos_tablet.adapter.customer.ListCustomerLevelApdater;
import qtc.project.pos_tablet.helper.Consts;
import qtc.project.pos_tablet.model.CustomerModel;
import qtc.project.pos_tablet.model.LevelCustomerModel;

public class FragmentLevelCustomerView extends BaseView<FragmentLevelCustomerView.UIContainer> implements FragmentLevelCustomerViewInterface {

    HomeActivity activity;
    FragmentLevelCustomerViewCallback callback;

    ListCustomerLevelApdater infoAdapter;

    private String id_Level = null;
    public int tongkhach = 0;

    @Override
    public void init(HomeActivity activity, FragmentLevelCustomerViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        eventClick();

        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.toggleNav();
            }
        });

        ui.imvHome.setOnClickListener(v -> {
            if (callback!=null)
                callback.eventBackHome();
        });
    }

    private void eventClick() {
        ui.btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.eventBackHome();
                }
            }
        });
    }

    @Override
    public void initRecyclerView(ArrayList<LevelCustomerModel> body) {
        LevelCustomerAdapter adapter = new LevelCustomerAdapter(activity, body);
        ui.recycler_view_product.setLayoutManager(new GridLayoutManager(getContext(), 2));
        ui.recycler_view_product.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setListener(new LevelCustomerAdapter.CustomerLevelAdapterListener() {
            @Override
            public void onItemClick(LevelCustomerModel model) {
                ui.levelnodata.setVisibility(View.GONE);
                ui.leveldata.setVisibility(View.VISIBLE);
                AppProvider.getImageHelper().displayImage(Consts.HOST_API + model.getImage(), ui.imageLevel, null, R.drawable.imageloading);
                ui.description.setText(model.getDescription());
                ui.nameLevel.setText("Số người nhận được cấp độ " + model.getName() + " : ");

                tongkhach = Integer.parseInt(model.getTotal_customer());

                ui.discount.setText("Có " + model.getTotal_customer() + " người hiển thị");

                //xem chi tiet
                ui.btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (callback != null) {
                            if (model.getTotal_customer().equals("0")) {
                                Toast.makeText(activity, "Không có khách hàng nào.", Toast.LENGTH_SHORT).show();
                            } else {
                                id_Level = model.getId();
                                callback.callPopup(model);
                                initDialog();
                            }
                        }
                    }
                });

            }
        });
    }

    ArrayList<CustomerModel> list = new ArrayList<>();
    AlertDialog.Builder alert;
    AlertDialog dialog;
    TextView tongkhachhang;

    @Override
    public void setDataCusDiaLog(ArrayList<CustomerModel> liist) {
        if (dialog != null) {
            list.clear();
            list.addAll(liist);
            infoAdapter.notifyDataSetChanged();
            tongkhach = list.size();
            tongkhachhang.setText("Có tất cả " + String.valueOf(tongkhach) + " khách hàng");
            dialog.show();
        }
    }

    public void initDialog() {

        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View popupView = layoutInflater.inflate(R.layout.custom_popup_level_detail, null);
        RecyclerView recycler_view_list_customer = popupView.findViewById(R.id.recycler_view_list_customer);
        tongkhachhang = popupView.findViewById(R.id.tongkhachhang);

        tongkhachhang.setText("Có tất cả " + String.valueOf(tongkhach) + " khách hàng");
        recycler_view_list_customer.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        infoAdapter = new ListCustomerLevelApdater(activity, list);
        recycler_view_list_customer.setAdapter(infoAdapter);
        infoAdapter.notifyDataSetChanged();

        LinearLayout btnExit = popupView.findViewById(R.id.btnExit);
        EditText edit_filter = popupView.findViewById(R.id.edit_filter);
        ImageView image_close = popupView.findViewById(R.id.image_close);

        alert = new AlertDialog.Builder(activity);
        alert.setView(popupView);
        dialog = alert.create();
        dialog.setCanceledOnTouchOutside(false);
        //dialog.show();

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        image_close.setOnClickListener(v -> {
            edit_filter.setText(null);
            if (callback!=null)
                callback.getAllDataById(id_Level);
        });

        //tim kiem trong popup.
        edit_filter.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchCustomer(edit_filter.getText().toString(), id_Level);
                    return true;
                }
//                Toast.makeText(activity, "Không có kết quả tìm kiếm!", Toast.LENGTH_SHORT).show();
//                return false;
                return false;
            }
        });

        edit_filter.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    searchCustomer(edit_filter.getText().toString(), id_Level);
                    return true;
                }
                    return false;
            }
        });

//        ui.image_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                searchCustomer(edit_filter.getText().toString(), id_Level);
//            }
//        });

    }

    @Override
    public void viewCountItemToDiaLog(String tongkhachs) {
        if (tongkhachs != null) {
            //tongkhach = Integer.parseInt(tongkhachs);
        }
    }

    private void getTotalCustomer(String id_level) {
        if (callback != null)
            callback.getgetTotalCustomer(id_level);
    }

    private void searchCustomer(String toString, String id_Level) {
        if (callback != null) {
            callback.eventSearchCustomer(toString, id_Level);
        }
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentLevelCustomerView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_level_customer;
    }

    public static class UIContainer extends BaseUiContainer {
        @UiElement(R.id.recycler_view_product)
        public RecyclerView recycler_view_product;

        @UiElement(R.id.imageLevel)
        public ImageView imageLevel;

        @UiElement(R.id.image_search)
        public ImageView image_search;

        @UiElement(R.id.description)
        public TextView description;

        @UiElement(R.id.nameLevel)
        public TextView nameLevel;

        @UiElement(R.id.discount)
        public TextView discount;

        @UiElement(R.id.leveldata)
        public NestedScrollView leveldata;

        @UiElement(R.id.levelnodata)
        public LinearLayout levelnodata;

        @UiElement(R.id.btnSubmit)
        public LinearLayout btnSubmit;

        @UiElement(R.id.btnExit)
        public LinearLayout btnExit;

        @UiElement(R.id.fragmentLevel)
        public LinearLayout fragmentLevel;

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.imvHome)
        public ImageView imvHome;
    }
}
