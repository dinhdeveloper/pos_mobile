package qtc.project.pos_tablet.ui.views.fragment.order;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos_tablet.R;
import qtc.project.pos_tablet.activity.HomeActivity;
import qtc.project.pos_tablet.adapter.order.OrderAdapter;
import qtc.project.pos_tablet.adapter.order.OrderDetailAdapter;
import qtc.project.pos_tablet.model.OrderDetailModel;
import qtc.project.pos_tablet.model.OrderModel;


public class FragmentOrderView extends BaseView<FragmentOrderView.UIContainer> implements FragmentOrderViewInterface {

    HomeActivity activity;
    FragmentOrderViewCallback callback;

    DatePickerDialog picker;
    TimePickerDialog timePicker;
    String date = "";
    String time = "";
    String tongtien = null;

    @Override
    public void init(HomeActivity activity, FragmentOrderViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        eventClick();
    }

    private void eventClick() {

        ui.imvHome.setOnClickListener(v -> {
            if (callback!=null)
                callback.goHome();
        });

        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.toggleNav();
            }
        });
        ui.filter_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = activity.getLayoutInflater();
                View popupView = layoutInflater.inflate(R.layout.custom_popup_filter_order, null);

                LinearLayout btnExit = popupView.findViewById(R.id.btnExit);
                LinearLayout btnSearch = popupView.findViewById(R.id.btnSearch);
                TextView calendarFilter = popupView.findViewById(R.id.calendarFilter);
                ImageView imageCalendar = popupView.findViewById(R.id.imageCalendar);
                TextView hourFilter = popupView.findViewById(R.id.hourFilter);
                EditText idOrderFilter = popupView.findViewById(R.id.idOrderFilter);

                AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                alert.setView(popupView);
                AlertDialog dialog = alert.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);


                imageCalendar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        picker = new DatePickerDialog(getContext(),
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        calendarFilter.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                        date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + " ";


                                    }
                                }, year, month, day);
                        picker.show();
                    }
                });
                hourFilter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final Calendar cldr = Calendar.getInstance();
                        int hour = cldr.get(Calendar.HOUR_OF_DAY);
                        int minutes = cldr.get(Calendar.MINUTE);
                        // time picker dialog
                        timePicker = new TimePickerDialog(getContext(),
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                        hourFilter.setText(sHour + ":" + sMinute);
                                        time = sHour + ":" + sMinute;
                                    }
                                }, hour, minutes, true);
                        timePicker.show();
                    }
                });

                btnSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (callback != null) {
                            callback.filterData(date + time, idOrderFilter.getText().toString());
                            dialog.dismiss();
                            ui.layoutOrderGone.setVisibility(View.VISIBLE);
                            ui.lauyoutOrderDetail.setVisibility(View.GONE);

                        }
                    }
                });


                btnExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });


        //search customer
        ui.edit_filter.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        //ui.edit_filter.setInputType();
        ui.edit_filter.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (ui.edit_filter.getText().toString() != null) {
                        searchOrder(ui.edit_filter.getText().toString());
                        return true;
                    }
                }
                Toast.makeText(activity, "Không có kết quả tìm kiếm!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        ui.edit_filter.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    if (ui.edit_filter.getText().toString() != null) {
                        searchOrder(ui.edit_filter.getText().toString());
                        return true;
                    }
                }
                return false;
            }
        });

        ui.image_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ui.edit_filter.getText().toString() != null) {
                    searchOrder(ui.edit_filter.getText().toString());
                } else {
                    Toast.makeText(activity, "Không có kết quả tìm kiếm!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //xos search
        ui.image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ui.edit_filter.setText(null);
                callback.reQuestData();
            }
        });
    }

    private void searchOrder(String search) {
        if (callback != null)
            callback.searchOrder(search);
    }

    @Override
    public void initRecyclerViewOrder(ArrayList<OrderModel> body) {

        OrderAdapter adapter = new OrderAdapter(activity, body);
        ui.recycler_view_order.setLayoutManager(new GridLayoutManager(getContext(), 2));
        ui.recycler_view_order.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setListener(new OrderAdapter.OrderAdapterListener() {
            @Override
            public void onItemClick(OrderModel model) {
               // try {
                    ui.layoutOrderGone.setVisibility(View.GONE);
                    ui.lauyoutOrderDetail.setVisibility(View.VISIBLE);
                    ui.idEmployee.setText(model.getEmployee_id());
                    ui.idCustomer.setText(model.getCustomer_id_code());
                    ui.idOrder.setText(model.getOrder_id_code());

                    ArrayList<OrderDetailModel> detailModels = new ArrayList<>();
                    if (model.getListDataOrderDetail().size() > 0) {
                        ui.layoutShow.setVisibility(View.VISIBLE);
                        for (int i = 0; i < model.getListDataOrderDetail().size(); i++) {
                            detailModels.add(model.getListDataOrderDetail().get(i));
                        }
                        OrderDetailAdapter detailAdapter = new OrderDetailAdapter(activity, detailModels);
                        ui.recycler_view_order_detail.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                        ui.recycler_view_order_detail.setAdapter(detailAdapter);
                        detailAdapter.notifyDataSetChanged();

                        int size = detailModels.size();
                        long allPrice = 0;

                        for (int i = 0; i < size; i++) {
                            long total = Long.valueOf(detailModels.get(i).getPrice()) * Long.valueOf(detailModels.get(i).getQuantity());
                            allPrice += total;
                        }
                        String pattern = "###,###.###";
                        DecimalFormat decimalFormat = new DecimalFormat(pattern);

                        ui.idPriceDemo.setText(decimalFormat.format(allPrice));
//                        if (!model.getCustomer_level_discount().isEmpty()){
//                            ui.priceSale.setText(model.getCustomer_level_discount() + "%");
//                        }
                        float tiengiam = allPrice - Long.valueOf(model.getOrder_total());
                        ui.allPrice.setText(decimalFormat.format(Long.valueOf(model.getOrder_total())) + " đ");
                        // ui.allPrice.setText(decimalFormat.format(allPrice - Integer.parseInt(model.getCustomer_level_discount())));
                        ui.priceSale.setText(decimalFormat.format(tiengiam)+" đ");
                        if (model.getOrder_status().equalsIgnoreCase("Y")) {
                            ui.btnExit.setVisibility(View.VISIBLE);
                            ui.btnExit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    callback.cancelOrder(model.getId_order());
                                }
                            });
                        } else if (model.getOrder_status().equalsIgnoreCase("N")) {
                            ui.btnExit.setVisibility(View.INVISIBLE);
                        }


                    } else {
                        ui.layoutShow.setVisibility(View.GONE);
                    }
//                } catch (Exception e) {
//                    Log.e("Ex", e.getMessage());
//                }
            }
        });
    }

    @Override
    public void initDataFilter(ArrayList<OrderModel> body, String dates, String id_order) {


//        OrderAdapter adapter = new OrderAdapter(activity, body);
//        ui.recycler_view_order.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        ui.recycler_view_order.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//        ui.layout_thongtin_filter.setVisibility(View.VISIBLE);
//
//        if (dates != null) {
//            ui.textDay.setText("Thời gian: " + dates);
//
//        }
//        if (id_order != null) {
//            ui.textId.setText("Mã: " + id_order);
//        }
//        ui.imageClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ui.layout_thongtin_filter.setVisibility(View.GONE);
//                ui.textDay.setText("Thời gian: ");
//                ui.textId.setText("Mã: ");
//                activity.replaceFragment(new FragmentOrder(), false, null);
//            }
//        });
//        adapter.setListener(new OrderAdapter.OrderAdapterListener() {
//            @Override
//            public void onItemClick(OrderModel model) {
//                ui.layoutOrderGone.setVisibility(View.GONE);
//                ui.lauyoutOrderDetail.setVisibility(View.VISIBLE);
//                ui.idEmployee.setText(model.getEmployee_id());
//                ui.idCustomer.setText(model.getCustomer_id());
//                ui.idOrder.setText(model.getId_order());
//
////                if (model.getListDataOrderDetail().size() > 0) {
////                    String pattern = "###,###.###";
////                    DecimalFormat decimalFormat = new DecimalFormat(pattern);
////                    ui.allPrice.setText(decimalFormat.format(Integer.parseInt(model.getOrder_total())) + " đ");
////                    int allPrices = 0;
////                    int tempPrice = 0;
////                    for (int i = 0; i < model.getListDataOrderDetail().size(); i++) {
////                        int a = 0;
////                        int b = 0;
////                        a = Integer.parseInt(model.getListDataOrderDetail().get(i).getPrice());
////                        b = Integer.parseInt(model.getListDataOrderDetail().get(i).getQuantity());
////                        allPrices = a * b;
////                        tempPrice += allPrices;
////                    }
////                    ui.idPriceDemo.setText(decimalFormat.format(tempPrice) + " đ");
////                    int priceSale = 0;
////                    priceSale = tempPrice - Integer.parseInt(model.getOrder_total());
////                    ui.priceSale.setText(decimalFormat.format(priceSale) + " đ");
////
////                    OrderDetailAdapter detailAdapter = new OrderDetailAdapter(activity, body);
////                    ui.recycler_view_order_detail.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
////                    ui.recycler_view_order_detail.setAdapter(detailAdapter);
////                    detailAdapter.notifyDataSetChanged();
////
////                    if (model.getOrder_status().equals("Y")) {
////                        ui.btnExit.setVisibility(View.VISIBLE);
////                        ui.btnExit.setOnClickListener(new View.OnClickListener() {
////                            @Override
////                            public void onClick(View view) {
////                                callback.cancelOrder(model.getId_order());
////                            }
////                        });
////                    } else if (model.getOrder_status().equals("N")) {
////                        ui.btnExit.setVisibility(View.INVISIBLE);
////                    }
////                } else {
////                    ui.layoutpriceTemp.setVisibility(View.GONE);
////                    ui.layoutPriceSale.setVisibility(View.GONE);
////                    ui.layoutPriceTatal.setVisibility(View.GONE);
////                    ui.btnExit.setVisibility(View.GONE);
////                    ui.viewer.setVisibility(View.GONE);
////                }
//            }
//        });
    }

    @Override
    public void showSuccess() {

        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View popupView = layoutInflater.inflate(R.layout.alert_dialog_success, null);
        TextView title_text = popupView.findViewById(R.id.title_text);
        TextView content_text = popupView.findViewById(R.id.content_text);
        Button custom_confirm_button = popupView.findViewById(R.id.custom_confirm_button);

        title_text.setText("Xác nhận");
        content_text.setText("Bạn đã huỷ thành công!");

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setView(popupView);
        AlertDialog dialog = alert.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        custom_confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (callback != null)
                    callback.reQuestData();
                dialog.dismiss();
            }
        });

    }


    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentOrderView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_order;

    }

    public static class UIContainer extends BaseUiContainer {
        @UiElement(R.id.recycler_view_order)
        public RecyclerView recycler_view_order;

        @UiElement(R.id.recycler_view_order_detail)
        public RecyclerView recycler_view_order_detail;

        @UiElement(R.id.lauyoutOrderDetail)
        public LinearLayout lauyoutOrderDetail;

        @UiElement(R.id.layoutpriceTemp)
        public LinearLayout layoutpriceTemp;

        @UiElement(R.id.layoutPriceSale)
        public LinearLayout layoutPriceSale;

        @UiElement(R.id.layoutPriceTatal)
        public LinearLayout layoutPriceTatal;

        @UiElement(R.id.viewer)
        public View viewer;

        @UiElement(R.id.layoutOrderGone)
        public LinearLayout layoutOrderGone;

        @UiElement(R.id.btnExit)
        public LinearLayout btnExit;

        @UiElement(R.id.filter_order)
        public ImageView filter_order;


        @UiElement(R.id.idCustomer)
        public TextView idCustomer;

        @UiElement(R.id.idEmployee)
        public TextView idEmployee;

        @UiElement(R.id.idOrder)
        public TextView idOrder;

        @UiElement(R.id.idPriceDemo)
        public TextView idPriceDemo;

        @UiElement(R.id.priceSale)
        public TextView priceSale;

        @UiElement(R.id.allPrice)
        public TextView allPrice;


        @UiElement(R.id.layout_thongtin_filter)
        public LinearLayout layout_thongtin_filter;

        @UiElement(R.id.textDay)
        public TextView textDay;

        @UiElement(R.id.textId)
        public TextView textId;

        @UiElement(R.id.imageClose)
        public ImageView imageClose;

        @UiElement(R.id.layoutShow)
        public LinearLayout layoutShow;

        @UiElement(R.id.image_search)
        public ImageView image_search;

        @UiElement(R.id.edit_filter)
        public EditText edit_filter;

        @UiElement(R.id.image_close)
        public ImageView image_close;

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.imvHome)
        public ImageView imvHome;
    }
}
