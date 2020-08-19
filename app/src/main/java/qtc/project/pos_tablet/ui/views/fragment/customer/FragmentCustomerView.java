package qtc.project.pos_tablet.ui.views.fragment.customer;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import b.laixuantam.myaarlibrary.helper.KeyboardUtils;
import qtc.project.pos_tablet.R;
import qtc.project.pos_tablet.activity.HomeActivity;
import qtc.project.pos_tablet.adapter.customer.CustomerAdapter;
import qtc.project.pos_tablet.model.CustomerModel;

public class FragmentCustomerView extends BaseView<FragmentCustomerView.UIContainer> implements FragmentCustomerViewInterface {

    HomeActivity activity;
    FragmentCustomerViewCallback callback;

    @Override
    public void init(HomeActivity activity, FragmentCustomerViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        KeyboardUtils.setupUI(getView(),activity);

        ui.imageNavLeft.setOnClickListener(v -> {
            activity.toggleNav();
        });
        ui.imvHome.setOnClickListener(v -> {
            if (callback!=null){
                callback.goHome();
            }
        });

        addData();
        onClickSearch();
    }

    private void onClickSearch() {
        ui.text_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchCustomer(ui.text_search.getText().toString());
                    return true;
                }
                Toast.makeText(activity, "Không có kết quả tìm kiếm!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        ui.text_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    searchCustomer(ui.text_search.getText().toString());
                    return true;
                }
                return false;
            }
        });

        ui.image_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ui.text_search.getText().toString()!=null){
                    searchCustomer(ui.text_search.getText().toString());
                }
                else {
                    Toast.makeText(activity, "Không có kết quả tìm kiếm!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ui.image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ui.text_search.setText(null);
                if (callback!=null)
                    callback.callAllDataCustomer();
            }
        });
    }

    private void searchCustomer(String toString) {
        if (callback != null){
            if (toString!=null){
                callback.callDataSearchCus(toString);
            }
            else {
                callback.callAllDataCustomer();
            }
        }
    }

    private void addData() {
        ui.addLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ui.layoutCreateCus.setVisibility(View.VISIBLE);
                ui.layoutCustomerNone.setVisibility(View.GONE);
                ui.layoutCustomer.setVisibility(View.GONE);

                ui.btnCreate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomerModel model = new CustomerModel();
                        model.setFull_name(ui.name_cus.getText().toString());
                        model.setAddress(ui.address_cus.getText().toString());
                        model.setEmail(ui.email_cus.getText().toString());
                        model.setPhone_number(ui.phone_cus.getText().toString());
                        if (callback != null) {
                            callback.createCustomer(model);
                        }
                    }
                });
                // nut xoa trong tao customer
                ui.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ui.layoutCustomer.setVisibility(View.GONE);
                        ui.layoutCreateCus.setVisibility(View.GONE);
                        ui.layoutCustomerNone.setVisibility(View.VISIBLE);
                        if (callback != null)
                            callback.callAllDataCustomer();
                    }
                });
            }
        });



//        ui.addLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ui.nameCustomer.setText(null);
//
//                ui.addressCustomer.setText(null);
//
//                ui.emailCustomer.setText(null);
//
//                ui.phoneCustomer.setText(null);
//
//
//                ui.layoutCustomer.setVisibility(View.VISIBLE);
//                ui.layoutCustomerNone.setVisibility(View.GONE);
//
//                ui.nameCustomer.setEnabled(true);
//                ui.nameCustomer.isFocused();
//
//                ui.layoutIdCustomer.setVisibility(View.GONE);
//                ui.layoutLevelCus.setVisibility(View.GONE);
//
//                ui.addressCustomer.setEnabled(true);
//                ui.addressCustomer.isFocused();
//                ui.emailCustomer.setEnabled(true);
//                ui.emailCustomer.isFocused();
//                ui.phoneCustomer.setEnabled(true);
//                ui.phoneCustomer.isFocused();
//                ui.txtUpdate.setText("Tạo mới");
//                ui.btnDelete.setVisibility(View.VISIBLE);
//                ui.imageEdit.setVisibility(View.GONE);
//                ui.btnUpdate.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (callback != null) {
//                            CustomerModel model = new CustomerModel();
//                            model.setFull_name(ui.nameCustomer.getText().toString());
//                            model.setAddress(ui.addressCustomer.getText().toString());
//                            model.setEmail(ui.emailCustomer.getText().toString());
//                            model.setPhone_number(ui.phoneCustomer.getText().toString());
//                            callback.createCustomer(model);
//                        }
//                    }
//                });
//
//                // nut xoa trong tao customer
//                ui.btnDelete.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ui.layoutCustomer.setVisibility(View.GONE);
//                        ui.layoutCustomerNone.setVisibility(View.VISIBLE);
//                        if (callback!=null)
//                            callback.callAllDataCustomer();
//                    }
//                });
//            }
//        });

    }

    private void eventClick() {
        ui.imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ui.imageEdit.setBackground(ContextCompat.getDrawable(activity, R.drawable.custom_background_close_order));

                ui.nameCustomer.setEnabled(true);
                ui.nameCustomer.isFocused();

                ui.addressCustomer.setEnabled(true);
                ui.addressCustomer.isFocused();

                ui.emailCustomer.setEnabled(true);
                ui.emailCustomer.isFocused();

//                ui.nameLevelCuss.setEnabled(true);
//                ui.nameLevelCuss.isFocused();

                ui.phoneCustomer.setEnabled(true);
                ui.phoneCustomer.isFocused();

                ui.btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (callback != null) {
                            CustomerModel model = new CustomerModel();
                            model.setId(ui.idCustomer.getText().toString());
                            model.setFull_name(ui.nameCustomer.getText().toString());
                            model.setAddress(ui.addressCustomer.getText().toString());
                            model.setEmail(ui.emailCustomer.getText().toString());
                            model.setPhone_number(ui.phoneCustomer.getText().toString());

                            callback.updateCustomer(model);

//                            SweetAlertDialog pDialog = new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE);
//                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//                            pDialog.setTitleText("Xác nhận")
//                                    .setContentText("Cập nhật thành công")
//                                    .setConfirmText("Tiếp tục")
//                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                        @Override
//                                        public void onClick(SweetAlertDialog sDialog) {
//                                            sDialog.dismissWithAnimation();
//                                        }
//                                    }).show();
                        }
                    }
                });
            }
        });

    }

    @Override
    public void initRecyclerViewCustomer(ArrayList<CustomerModel> body) {
        CustomerAdapter adapter = new CustomerAdapter(activity, body);
        ui.recycler_view_customer.setLayoutManager(new GridLayoutManager(getContext(), 2));
        ui.recycler_view_customer.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setListener(new CustomerAdapter.CustomerAdapterListener() {
            @Override
            public void onItemClick(CustomerModel model) {
                ui.layoutCustomer.setVisibility(View.VISIBLE);
                ui.layoutCustomerNone.setVisibility(View.GONE);
                ui.layoutCreateCus.setVisibility(View.GONE);

                ui.txtUpdate.setText("Cập nhật");
                ui.btnDelete.setVisibility(View.GONE);

                ui.idCustomer.setText(model.getId());
                ui.nameCustomer.setText(model.getFull_name());
                ui.addressCustomer.setText(model.getAddress());
                ui.emailCustomer.setText(model.getEmail());
                ui.nameLevelCuss.setText(model.getLevel_name());
                ui.phoneCustomer.setText(model.getPhone_number());

                eventClick();
            }
        });

    }

    @Override
    public void resetFragment() {

        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View popupView = layoutInflater.inflate(R.layout.alert_dialog_success, null);
        TextView title_text = popupView.findViewById(R.id.title_text);
        TextView content_text = popupView.findViewById(R.id.content_text);
        Button custom_confirm_button = popupView.findViewById(R.id.custom_confirm_button);

        title_text.setText("Xác nhận");
        content_text.setText("Bạn đã cập nhật thành công!");

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setView(popupView);
        AlertDialog dialog = alert.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        custom_confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ui.layoutCustomer.setVisibility(View.GONE);
                ui.layoutCreateCus.setVisibility(View.GONE);
                ui.layoutCustomerNone.setVisibility(View.VISIBLE);
                ui.imageEdit.setBackground(ContextCompat.getDrawable(activity, R.color.hinhnen));

                if (callback!=null)
                    callback.callAllDataCustomer();

                dialog.dismiss();
            }
        });
    }

    @Override
    public void showDiaLogSucess() {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View popupView = layoutInflater.inflate(R.layout.alert_dialog_success, null);
        TextView title_text = popupView.findViewById(R.id.title_text);
        TextView content_text = popupView.findViewById(R.id.content_text);
        Button custom_confirm_button = popupView.findViewById(R.id.custom_confirm_button);

        title_text.setText("Xác nhận");
        content_text.setText("Bạn đã tạo mới thành công!");

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setView(popupView);
        AlertDialog dialog = alert.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        custom_confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ui.name_cus.setText(null);
                ui.address_cus.setText(null);
                ui.email_cus.setText(null);
                ui.phone_cus.setText(null);

                ui.layoutCustomer.setVisibility(View.GONE);
                ui.layoutCreateCus.setVisibility(View.GONE);
                ui.layoutCustomerNone.setVisibility(View.VISIBLE);

                if (callback!=null)
                    callback.callAllDataCustomer();

                dialog.dismiss();
            }
        });
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentCustomerView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_customer;
    }

    public static class UIContainer extends BaseUiContainer {
        @UiElement(R.id.recycler_view_customer)
        public RecyclerView recycler_view_customer;

        @UiElement(R.id.layoutCustomerNone)
        public LinearLayout layoutCustomerNone;

        @UiElement(R.id.layoutCustomer)
        public LinearLayout layoutCustomer;

        @UiElement(R.id.btnUpdate)
        public LinearLayout btnUpdate;

        @UiElement(R.id.layoutName)
        public LinearLayout layoutName;

        @UiElement(R.id.layoutIdCustomer)
        public LinearLayout layoutIdCustomer;

        @UiElement(R.id.layoutLevelCus)
        public LinearLayout layoutLevelCus;

        @UiElement(R.id.btnDelete)
        public LinearLayout btnDelete;

        @UiElement(R.id.btn_detail_Customer)
        public LinearLayout btn_detail_Customer;

        @UiElement(R.id.addLayout)
        public LinearLayout addLayout;

        @UiElement(R.id.imageEdit)
        public ImageView imageEdit;

        @UiElement(R.id.image_close)
        public ImageView image_close;



        @UiElement(R.id.nameCustomer)
        public EditText nameCustomer;

        @UiElement(R.id.idCustomer)
        public EditText idCustomer;

        @UiElement(R.id.phoneCustomer)
        public EditText phoneCustomer;

        @UiElement(R.id.emailCustomer)
        public EditText emailCustomer;

        @UiElement(R.id.addressCustomer)
        public EditText addressCustomer;

        @UiElement(R.id.nameLevelCuss)
        public EditText nameLevelCuss;

        @UiElement(R.id.txtUpdate)
        public TextView txtUpdate;

        @UiElement(R.id.text_search)
        public EditText text_search;

        @UiElement(R.id.image_search)
        public ImageView image_search;

        //create
        @UiElement(R.id.layoutCreateCus)
        public LinearLayout layoutCreateCus;

        @UiElement(R.id.btnCreate)
        public LinearLayout btnCreate;

        @UiElement(R.id.name_cus)
        public EditText name_cus;

        @UiElement(R.id.phone_cus)
        public EditText phone_cus;

        @UiElement(R.id.address_cus)
        public EditText address_cus;

        @UiElement(R.id.email_cus)
        public EditText email_cus;

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.imvHome)
        public ImageView imvHome;

    }
}
