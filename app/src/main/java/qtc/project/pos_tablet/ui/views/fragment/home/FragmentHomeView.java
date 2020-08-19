package qtc.project.pos_tablet.ui.views.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import b.laixuantam.myaarlibrary.helper.KeyboardUtils;
import b.laixuantam.myaarlibrary.widgets.currencyedittext.CurrencyEditText;
import qtc.project.pos_tablet.R;
import qtc.project.pos_tablet.activity.HomeActivity;
import qtc.project.pos_tablet.adapter.customer.ListCustomerFHomeAdapter;
import qtc.project.pos_tablet.adapter.home.CategoryAdapter;
import qtc.project.pos_tablet.adapter.home.ListItemClickAdapter;
import qtc.project.pos_tablet.adapter.product.ProductAdapter;
import qtc.project.pos_tablet.adapter.product.ProductInfoAdapter;
import qtc.project.pos_tablet.bill.BillActivity;
import qtc.project.pos_tablet.database.DatabaseProvider;
import qtc.project.pos_tablet.dependency.AppProvider;
import qtc.project.pos_tablet.model.CategoryModel;
import qtc.project.pos_tablet.model.CustomerModel;
import qtc.project.pos_tablet.model.ListOrderModel;
import qtc.project.pos_tablet.model.PackageInfoModel;
import qtc.project.pos_tablet.model.ProductModel;

import static qtc.project.pos_tablet.database.DatabaseProvider.DATABASE_NAME;

public class FragmentHomeView extends BaseView<FragmentHomeView.UIContainer> implements FragmentHomeViewInterface {

    HomeActivity activity;
    FragmentHomeViewCallback callback;

    String name = "";
    String level = null;
    String discount = null;
    String gia = "";
    String id_customer = null;
    String tongtien = null;
    int tonkho = 0;

    private Timer timer = new Timer();
    private final long DELAY = 1000; // in ms

    int size = 0;
    long tienthoilai = 0;
    long tienkhachdua;
    HashMap<String, ListOrderModel> hashMap = new HashMap<>();
    ArrayList<ListOrderModel> arList = new ArrayList<>();

    ArrayList<ProductModel> arrayList = new ArrayList<>();
    boolean enableLoadMore = true;
    ProductAdapter productAdapter;

    ListItemClickAdapter clickAdapter;

    DatabaseProvider provider;

    @Override
    public void init(HomeActivity activity, FragmentHomeViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        provider = new DatabaseProvider(activity);
        KeyboardUtils.setupUI(getView(), activity);
        initProduct();
        ui.edit_filter.requestFocus();
        getDataSQLite();
        click();
    }

    private void getDataSQLite() {
        ArrayList<ListOrderModel> listOrderModels = provider.getNotes();
        if (listOrderModels.isEmpty()) {
            ui.layoutShow.setVisibility(View.GONE);
            ui.layoutOrderHome.setVisibility(View.GONE);
            ui.layoutProductGone.setVisibility(View.VISIBLE);
            initProduct();
        } else {
            ui.layoutShow.setVisibility(View.VISIBLE);
            ui.layoutOrderHome.setVisibility(View.VISIBLE);
            ui.layoutProductGone.setVisibility(View.GONE);
            //chon khach hang
            ui.layoutChooseCus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        callback.callPopup();
                        diaLog();
                    }
                }
            });
            String tongTonKho = null;
            for (ListOrderModel orderModel : listOrderModels) {
                hashMap.put(orderModel.getId(), orderModel);
                tongTonKho = orderModel.getTotalStore();
            }
            arList.clear();
            for (Map.Entry<String, ListOrderModel> map : hashMap.entrySet()) {
                arList.add(map.getValue());
            }
            ui.recycler_view_list_order.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            clickAdapter = new ListItemClickAdapter(activity, arList);
            ui.recycler_view_list_order.setAdapter(clickAdapter);

            String finalTongTonKho = tongTonKho;
            clickAdapter.setListener(new ListItemClickAdapter.ListItemClickAdapterListener() {
                @Override
                public void onItemClick(ListOrderModel model) {

                }

                @Override
                public void onClickThemSoLuong(ListOrderModel model) {
                    if (hashMap.get(model.getId()) != null) {
                        //dem += 1;
                        String old_quantity = hashMap.get(model.getId()).getQuantityProduct();
                        int new_quantity = Integer.valueOf(old_quantity) + 1;
                        hashMap.get(model.getId()).setQuantityProduct(String.valueOf(new_quantity));
                        provider.updateRecord(hashMap.get(model.getId()));
                        tinhTong();
                    }
                }

                @Override
                public void onClickGiamSoLuong(ListOrderModel model) {
                    if (hashMap.get(model.getId()) != null) {
                        //dem -= 1;
                        String old_quantity = hashMap.get(model.getId()).getQuantityProduct();
                        int new_quantity = Integer.valueOf(old_quantity) - 1;
                        hashMap.get(model.getId()).setQuantityProduct(String.valueOf(new_quantity));
                        if (new_quantity <= 1) {
                            hashMap.get(model.getId()).setQuantityProduct(String.valueOf(1));
                        }
                        provider.updateRecord(hashMap.get(model.getId()));
                        tinhTong();
                    }
                }

                @Override
                public void onClickXoaItem(ListOrderModel items) {
                    if (hashMap.get(items.getId()) != null) {
                        provider.deleteRow(hashMap.get(items.getId()).getId());
                        hashMap.remove(items.getId());
                        clickAdapter.remove(items);

                        tinhTong();
                    }
                }

                @Override
                public void onChangeSoLuong(ListOrderModel item) {
                    if (hashMap.get(item.getId()) != null) {
                        hashMap.get(item.getId()).setQuantityProduct(item.getQuantityProduct());
                        if (finalTongTonKho != null) {
                            if (Integer.valueOf(hashMap.get(item.getId()).getInventory()) <= Integer.valueOf(finalTongTonKho)) {
                                tinhTong();
                                provider.updateRecord(hashMap.get(item.getId()));
                            } else {
                                Toast.makeText(activity, "Tồn kho không đủ", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            tinhTong();
                        }
                    }
                }
            });
            clickAdapter.notifyDataSetChanged();
            tinhTong();
            //tinh tien
            ui.btnChoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (hashMap.size() > 0) {
                        KeyboardUtils.setupUI(getView(), activity);
                        LayoutInflater layoutInflater = activity.getLayoutInflater();
                        View popupView = layoutInflater.inflate(R.layout.custom_popup_tien_khach_dua, null);

                        LinearLayout btnExit = popupView.findViewById(R.id.btnExit);
                        LinearLayout btnOk = popupView.findViewById(R.id.btnOk);
                        CurrencyEditText priceItem = popupView.findViewById(R.id.priceItem);
                        TextView tien_thoi_lai_khach = popupView.findViewById(R.id.tien_thoi_lai_khach);
                        TextView tong_thanh_tien = popupView.findViewById(R.id.tong_thanh_tien);

                        String pattern = "###,###.###";
                        DecimalFormat decimalFormat = new DecimalFormat(pattern);
                        tong_thanh_tien.setText(decimalFormat.format(Long.valueOf(tongtien)));

                        priceItem.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if (timer != null)
                                    timer.cancel();
                                timer = new Timer();
                                timer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (!TextUtils.isEmpty(s)) {
                                                    tienkhachdua = Long.valueOf(priceItem.getStringValue());
                                                    tienthoilai = tienkhachdua - Long.valueOf(tongtien);
                                                    tien_thoi_lai_khach.setText(decimalFormat.format(tienthoilai));
                                                }
                                            }
                                        });
                                    }

                                }, DELAY);
                            }
                        });

//                        priceItem.setOnKeyListener(new View.OnKeyListener() {
//                            @Override
//                            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
//                                    if (tienthoilai < 0) {
//                                        priceItem.setText(null);
//                                        tien_thoi_lai_khach.setText(null);
//                                        Toast.makeText(activity, "Hãy nhập đúng tiền", Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        if (!priceItem.getStringValue().trim().isEmpty()){
//                                            callback.tinhTien(arList, id_customer, tongtien, discount);
//                                            dialog.dismiss();
//                                        }
//                                    }
//                                    return true;
//                                }
//                                return false;
//                            }
//                        });

                        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                        alert.setView(popupView);
                        AlertDialog dialog = alert.create();
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();

                        btnExit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (tienthoilai < 0) {
                                    priceItem.setText(null);
                                    tien_thoi_lai_khach.setText(null);
                                    Toast.makeText(activity, "Hãy nhập đúng tiền", Toast.LENGTH_SHORT).show();
                                } else {
                                    callback.tinhTien(arList, id_customer, tongtien, discount);
                                    dialog.dismiss();
                                }
                            }
                        });

                        priceItem.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                            @Override
                            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                if (actionId == EditorInfo.IME_ACTION_DONE) {
                                    if (tienthoilai < 0) {
                                        priceItem.setText(null);
                                        tien_thoi_lai_khach.setText(null);
                                        Toast.makeText(activity, "Hãy nhập đúng tiền", Toast.LENGTH_SHORT).show();
                                    } else {
                                        callback.tinhTien(arList, id_customer, tongtien, discount);
                                        dialog.dismiss();
                                    }
                                }
                                return true;
                            }
                        });

                    } else {
                        Toast.makeText(activity, "Bạn chưa chọn sản phẩm", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void addItemCart(ProductModel model, PackageInfoModel infoModel) {

        if (hashMap.get(infoModel.getPack_id()) != null) {
            tonkho = Integer.valueOf(model.getTotal_stock());
            String old_quantity = hashMap.get(infoModel.getPack_id()).getQuantityProduct();
            int new_quantity = Integer.valueOf(old_quantity) + 1;
            if (new_quantity <= tonkho) {
                hashMap.get(infoModel.getPack_id()).setQuantityProduct(String.valueOf(new_quantity));
                provider.updateRecord(hashMap.get(infoModel.getPack_id()));
            } else {
                Toast.makeText(activity, "Tồn kho không đủ.", Toast.LENGTH_SHORT).show();
            }
        } else {
            ListOrderModel orderModel = new ListOrderModel();
            orderModel.setId(infoModel.getPack_id());
            orderModel.setNameProduct(model.getName());
            orderModel.setQuantityProduct(String.valueOf(1));
            orderModel.setPriceProduct(model.getSale_price());
            orderModel.setInventory(model.getTotal_stock());
            hashMap.put(infoModel.getPack_id(), orderModel);
            //add moi record sqlite
            provider.addNotes(orderModel);
        }
        arList.clear();
        for (Map.Entry<String, ListOrderModel> map : hashMap.entrySet()) {
            arList.add(map.getValue());
        }
        //save vao sharedPreferences
//        arList.clear();
//        Set<String> keySet = hashMap.keySet();
//        for (String key : keySet) {
//            arList.add(hashMap.get(key));
//            AppProvider.getPreferences().saveMap(key, hashMap.get(key));
//            AppProvider.getPreferences().saveKeyMap(keySet);
//        }

        ui.recycler_view_list_order.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        clickAdapter = new ListItemClickAdapter(activity, arList);
        ui.recycler_view_list_order.setAdapter(clickAdapter);

        clickAdapter.setListener(new ListItemClickAdapter.ListItemClickAdapterListener() {
            @Override
            public void onItemClick(ListOrderModel model) {

            }

            @Override
            public void onClickThemSoLuong(ListOrderModel model) {
                if (hashMap.get(model.getId()) != null) {
                    //dem += 1;
                    String old_quantity = hashMap.get(model.getId()).getQuantityProduct();
                    int new_quantity = Integer.valueOf(old_quantity) + 1;
                    hashMap.get(model.getId()).setQuantityProduct(String.valueOf(new_quantity));
                    provider.updateRecord(hashMap.get(model.getId()));
                    tinhTong();
                }
            }

            @Override
            public void onClickGiamSoLuong(ListOrderModel model) {
                if (hashMap.get(model.getId()) != null) {
                    //dem -= 1;
                    String old_quantity = hashMap.get(model.getId()).getQuantityProduct();
                    int new_quantity = Integer.valueOf(old_quantity) - 1;
                    hashMap.get(model.getId()).setQuantityProduct(String.valueOf(new_quantity));
                    if (new_quantity <= 1) {
                        hashMap.get(model.getId()).setQuantityProduct(String.valueOf(1));
                    }
                    provider.updateRecord(hashMap.get(model.getId()));
                    tinhTong();
                }
            }

            @Override
            public void onClickXoaItem(ListOrderModel items) {
                if (hashMap.get(items.getId()) != null) {
                    provider.deleteRecord(hashMap.get(items.getId()));
                    hashMap.remove(items.getId());
                    clickAdapter.remove(items);
                    tinhTong();
                }
            }

            @Override
            public void onChangeSoLuong(ListOrderModel item) {
                if (hashMap.get(item.getId()) != null) {
                    hashMap.get(item.getId()).setQuantityProduct(item.getQuantityProduct());
                    if (Integer.valueOf(hashMap.get(item.getId()).getInventory()) <= Integer.valueOf(model.getTotal_stock())) {
                        tinhTong();
                    } else {
                        Toast.makeText(activity, "Tồn kho không đủ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        clickAdapter.notifyDataSetChanged();
        tinhTong();
        //tinh tien
        ui.btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hashMap.size() > 0) {
                    KeyboardUtils.setupUI(getView(), activity);
                    LayoutInflater layoutInflater = activity.getLayoutInflater();
                    View popupView = layoutInflater.inflate(R.layout.custom_popup_tien_khach_dua, null);

                    LinearLayout btnExit = popupView.findViewById(R.id.btnExit);
                    LinearLayout btnOk = popupView.findViewById(R.id.btnOk);
                    CurrencyEditText priceItem = popupView.findViewById(R.id.priceItem);
                    TextView tien_thoi_lai_khach = popupView.findViewById(R.id.tien_thoi_lai_khach);
                    TextView tong_thanh_tien = popupView.findViewById(R.id.tong_thanh_tien);

                    String pattern = "###,###.###";
                    DecimalFormat decimalFormat = new DecimalFormat(pattern);
                    tong_thanh_tien.setText(decimalFormat.format(Long.valueOf(tongtien)));

                    priceItem.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (timer != null)
                                timer.cancel();
                            timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (!TextUtils.isEmpty(s)) {
                                                tienkhachdua = Long.valueOf(priceItem.getStringValue());
                                                tienthoilai = tienkhachdua - Long.valueOf(tongtien);
                                                tien_thoi_lai_khach.setText(decimalFormat.format(tienthoilai));
                                            }
                                        }
                                    });
                                }

                            }, DELAY);
                        }
                    });

//                    priceItem.setOnKeyListener(new View.OnKeyListener() {
//                        @Override
//                        public boolean onKey(View v, int keyCode, KeyEvent event) {
//                            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
//                                if (tienthoilai < 0) {
//                                    priceItem.setText(null);
//                                    tien_thoi_lai_khach.setText(null);
//                                    Toast.makeText(activity, "Hãy nhập đúng tiền", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    if (!priceItem.getStringValue().trim().isEmpty()){
//                                        callback.tinhTien(arList, id_customer, tongtien, discount);
//                                        dialog.dismiss();
//                                    }
//                                }
//                                return true;
//                            }
//                            return false;
//                        }
//                    });

                    AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                    alert.setView(popupView);
                    AlertDialog dialog = alert.create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();

                    btnExit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (callback != null) {
                                if (tienthoilai < 0) {
                                    priceItem.setText(null);
                                    tien_thoi_lai_khach.setText(null);
                                    Toast.makeText(activity, "Hãy nhập đúng tiền", Toast.LENGTH_SHORT).show();
                                } else {
                                    callback.tinhTien(arList, id_customer, tongtien, discount);
                                    dialog.dismiss();
                                }
//                                callback.tinhTien(arList, id_customer, tongtien, discount);
//                                dialog.dismiss();
                            }
                        }
                    });

                    priceItem.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if (actionId == EditorInfo.IME_ACTION_DONE) {
                                if (tienthoilai < 0) {
                                    priceItem.setText(null);
                                    tien_thoi_lai_khach.setText(null);
                                    Toast.makeText(activity, "Hãy nhập đúng tiền", Toast.LENGTH_SHORT).show();
                                } else {
                                    callback.tinhTien(arList, id_customer, tongtien, discount);
                                    dialog.dismiss();
                                }
                            }
                            return true;
                        }
                    });

                } else {
                    Toast.makeText(activity, "Bạn chưa chọn sản phẩm", Toast.LENGTH_SHORT).show();
                }

//                    priceItem.setOnTouchListener(new View.OnTouchListener() {
//                        @Override
//                        public boolean onTouch(View v, MotionEvent event) {
//                            AppUtils.showKeyboard(getContext());
//                            return false;
//                        }
//                    });


                //}
            }
        });
    }

    private void click() {

        ui.edit_filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (timer != null)
                    timer.cancel();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (!TextUtils.isEmpty(s)) {
                                    searchProduct(ui.edit_filter.getText().toString());
                                }
                            }
                        });
                    }

                }, DELAY);
            }
        });

        ui.imageNavLeft.setOnClickListener(v -> {
            activity.toggleNav();
        });

        ui.imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                discount = null;
                name = null;
                level = null;
                ui.nameCustomer.setText(null);
                ui.nameLevelCus.setText(null);
                tinhTong();
            }
        });

        ui.edit_filter.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchProduct(ui.edit_filter.getText().toString());
                    return true;
                }
                //Toast.makeText(activity, "Không có kết quả tìm kiếm!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        ui.search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ui.edit_filter.getText().toString() != null) {
                    searchProduct(ui.edit_filter.getText().toString());
                } else {
                    Toast.makeText(activity, "Không có kết quả tìm kiếm!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ui.image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ui.edit_filter.setText(null);
                arrayList.clear();
                enableLoadMore = true;
                productAdapter.notifyDataSetChanged();
                if (callback != null) {
                    callback.callAllData();
                    enableLoadMore = true;
                }
            }
        });

    }


    private void searchProduct(String toString) {
        if (callback != null) {
            if (toString != null) {
                callback.callDataSearchProduct(toString, 1);
                ui.edit_filter.setText(null);
                ui.edit_filter.requestFocus();
            } else {
                callback.callAllData();
            }
        }
    }

    private void searchCustomer(String toString) {
        if (callback != null) {
            if (toString != null) {
                callback.callDataSearchCus(toString);
            } else {
                callback.callAllDataCustomer();
            }
        }
    }


    @Override
    public void initRecyclerViewCaregory(ArrayList<CategoryModel> body) {
        CategoryAdapter adapter = new CategoryAdapter(body, activity);
        ui.recycler_view_category.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        ui.recycler_view_category.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setListener(new CategoryAdapter.CategoryAdapterListener() {
            @Override
            public void onItemClick(CategoryModel model) {
                if (callback != null) {
                    callback.onChangToCategoryDetail(model);
                    enableLoadMore = true;
                }
            }

            @Override
            public void onHeaderItemClick() {
                if (callback != null) {
                    arrayList.clear();
                    productAdapter.notifyDataSetChanged();
                    callback.resetPage();
                    callback.callAllData();
                    enableLoadMore = true;
                }
            }
        });
    }

    @Override
    public void initRecyclerViewProduct(ProductModel[] list, int status) {
        if (list == null || list.length == 0) {
            if (arrayList.size() == 0)
                showEmptyList();
            return;
        }
        if (status == 0) {
            for (ProductModel model : list) {
                if (Integer.valueOf(model.getTotal_stock()) >= 0) {
                    arrayList.add(model);
                }
            }
        } else if (status == 1) { //search
            if (list.length == 1) {
                for (ProductModel model : list) {
                    if (Integer.valueOf(model.getTotal_stock()) >= 0) {
                        arrayList.add(model);
                    }
                    chooseCustomer();
                    gia = model.getSale_price();
                    ui.layoutShow.setVisibility(View.VISIBLE);
                    try {
                        addItemCart(model, model.getListDataProduct().get(0));
                    } catch (Exception e) {
                        Log.e("ERROR", e.getMessage());
                    }
                }
            } else {
                for (ProductModel model : list) {
                    if (Integer.valueOf(model.getTotal_stock()) >= 0) {
                        arrayList.add(model);
                    }
                }
            }
        }
        //arrayList.addAll(Arrays.asList(list));
        productAdapter.notifyDataSetChanged();
    }

    private void showEmptyList() {
    }


    public void initProduct() {
        ui.recycler_view_product.getRecycledViewPool().clear();
        productAdapter = new ProductAdapter(activity, arrayList);
        ui.recycler_view_product.setLayoutManager(new GridLayoutManager(getContext(), 4));
        ui.recycler_view_product.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
        productAdapter.setListener(new ProductAdapter.ProductAdapterListener() {
            @Override
            public void onItemClick(ProductModel model) {
                chooseCustomer();
                gia = model.getSale_price();
                ui.layoutShow.setVisibility(View.VISIBLE);
                addItemCart(model, model.getListDataProduct().get(0));
            }

            @Override
            public void onRequestLoadMoreProduct() {
                if (callback != null && enableLoadMore)
                    callback.loadMore();
            }
        });
    }

    boolean check = false;

    private void getListPrice(ProductModel model) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View popupView = layoutInflater.inflate(R.layout.custom_popup_product_detail, null);
        RecyclerView recycler_view_price = popupView.findViewById(R.id.recycler_view_price);

        List<PackageInfoModel> modelList = new ArrayList<>();

        for (PackageInfoModel item : model.getListDataProduct()) {
            if (Integer.parseInt(item.getQuantity_storage()) > 0) {
                modelList.add(item);
            }
        }

        LinearLayout btnChon = popupView.findViewById(R.id.btnChon);
        TextView san_pham = popupView.findViewById(R.id.san_pham);
        recycler_view_price.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        ProductInfoAdapter infoAdapter = new ProductInfoAdapter(activity, modelList);
        recycler_view_price.setAdapter(infoAdapter);
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setView(popupView);
        AlertDialog dialog = alert.create();

        san_pham.setText("Sản phẩm " + model.getName());

        if (model.getListDataProduct().size() > 0) {
//            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } else {
            Toast.makeText(activity, "Không có giá được chọn.", Toast.LENGTH_SHORT).show();
        }

        btnChon.setOnClickListener(v -> {
            if (check == false) {
                Toast.makeText(activity, "Bạn chưa chọn sản phẩm nào", Toast.LENGTH_SHORT).show();
            }
        });

        infoAdapter.setOnItemClickListener(new ProductInfoAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClickListener(PackageInfoModel pakmodel) {
                gia = pakmodel.getSale_price();
                check = true;
                //chon gia san pham va cho vao gio hang.
                btnChon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (check == true) {
                            ui.layoutShow.setVisibility(View.VISIBLE);
                            addItemCart(model, pakmodel);
                            dialog.dismiss();
                            check = false;
                        }
                    }
                });
            }
        });
    }

    public void tinhTong() {
        try {
            //tong tien tren 1 item
            long priceTemp = 0;
            //tong tien
            long total = 0;
            String pattern = "###,###.###";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);
            Set<String> keySet = hashMap.keySet();
            for (String key : keySet) {
                //tong tien 1 item.
                priceTemp = Long.valueOf(hashMap.get(key).getQuantityProduct()) * Long.valueOf(hashMap.get(key).getPriceProduct());
                total += priceTemp;
            }
            ui.idPriceDemo.setText(decimalFormat.format(total) + " đ");
            if (discount != null) {
                ui.priceSale.setText(decimalFormat.format(Integer.parseInt(discount)) + " %");

                long tiengiam = (total * Integer.parseInt(discount)) / 100;
                tongtien = String.valueOf(total - tiengiam);
                ui.allPrice.setText(decimalFormat.format(Long.valueOf(tongtien)) + " đ");
            }
            //khach vang lai
            else if (discount == null) {
                discount = "0";
                ui.priceSale.setText(decimalFormat.format(Integer.parseInt(discount)) + " %");
                long tiengiam = (total * Integer.parseInt(discount)) / 100;
                tongtien = String.valueOf(total - tiengiam);
                ui.allPrice.setText(decimalFormat.format(Long.valueOf(tongtien)) + " đ");
            }
        } catch (Exception e) {
            Log.e("Ex", e.getMessage());
        }
    }

    ListCustomerFHomeAdapter infoAdapter;
    AlertDialog dialogs;
    AlertDialog dialog;
    ArrayList<CustomerModel> list = new ArrayList<>();
    TextView tongkhachhang;

    public void diaLog() {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View popupView = layoutInflater.inflate(R.layout.custom_popup_list_customer_home, null);
        RecyclerView recycler_view_list_customer = popupView.findViewById(R.id.recycler_view_list_customer);

        LinearLayout btnExit = popupView.findViewById(R.id.btnExit);
        LinearLayout btnOk = popupView.findViewById(R.id.btnOk);
        EditText text_search = popupView.findViewById(R.id.text_search);
        tongkhachhang = popupView.findViewById(R.id.tongkhachhang);
        ImageView image_search = popupView.findViewById(R.id.image_search);
        ImageView image_close = popupView.findViewById(R.id.image_close);
        recycler_view_list_customer.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        infoAdapter = new ListCustomerFHomeAdapter(activity, list);
        recycler_view_list_customer.setAdapter(infoAdapter);
        infoAdapter.notifyDataSetChanged();

        AlertDialog.Builder alerts = new AlertDialog.Builder(activity);
        alerts.setView(popupView);
        dialogs = alerts.create();
        dialogs.setCanceledOnTouchOutside(false);
        // dialog.show();

        if (size > 0) {
            tongkhachhang.setText("Có tất cả " + size + " khách hàng.");
        }

        infoAdapter.setListener(new ListCustomerFHomeAdapter.ListCustomerAdapterListener() {
            @Override
            public void onItemClick(CustomerModel model) {
                name = model.getFull_name();
                level = model.getLevel_name();
                id_customer = model.getId();
                if (model.getLevel_discount() == null) {
                    discount = String.valueOf("0");
                } else {
                    discount = String.valueOf(model.getLevel_discount());
                }
            }
        });

        image_close.setOnClickListener(v -> {
            text_search.setText(null);
            if (callback != null)
                callback.callAllDataCustomer();
        });


        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogs.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ui.nameCustomer.setText(name);
                ui.nameLevelCus.setText(level);
                ui.priceSale.setText(String.valueOf(discount));
                tinhTong();
                dialogs.dismiss();
            }
        });
        text_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchCustomer(text_search.getText().toString());
                    return true;
                }
                Toast.makeText(activity, "Không có kết quả tìm kiếm!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        text_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    searchCustomer(text_search.getText().toString());
                    return true;
                }
                return false;
            }
        });

        image_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (text_search.getText().toString() != null) {
                    searchCustomer(text_search.getText().toString());
                } else {
                    Toast.makeText(activity, "Không có kết quả tìm kiếm!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void initCustomer(ArrayList<CustomerModel> liist) {

        if (dialogs != null) {
            size = liist.size();
            list.clear();
            list.addAll(liist);
            tongkhachhang.setText("Có tất cả " + size + " khách hàng.");
            infoAdapter.notifyDataSetChanged();
            dialogs.show();
        }
    }

    //    @Override
//    public void putBarcode(String rawValue) {
//    }
//
    @Override
    public void truyenIntent(ArrayList<ListOrderModel> arList) {
        Intent intent = new Intent(activity, BillActivity.class);
        Bundle bundle = new Bundle();
        if (tienkhachdua == 0) {
            tienkhachdua = Long.valueOf(tongtien);
        }
        bundle.putLong("tong_tien", Long.valueOf(tongtien));
        bundle.putLong("tien_khach_dua", tienkhachdua);
        bundle.putLong("tien_thoi_lai", tienthoilai);
        bundle.putSerializable("model", arList);
        intent.putExtras(bundle);
        activity.startActivity(intent);

    }

    @Override
    public void loadMore() {
    }

    @Override
    public void setNoMoreLoading() {
        enableLoadMore = false;
    }

    @Override
    public void clearListDataProduct() {
        arrayList.clear();
        productAdapter.notifyDataSetChanged();
    }


    private void chooseCustomer() {
        ui.layoutOrderHome.setVisibility(View.VISIBLE);
        ui.layoutProductGone.setVisibility(View.GONE);
        //chon khach hang
        ui.layoutChooseCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.callPopup();
                    diaLog();
                }
            }
        });
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentHomeView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_home;
    }

    public static class UIContainer extends BaseUiContainer {
        @UiElement(R.id.recycler_view_category)
        public RecyclerView recycler_view_category;

        @UiElement(R.id.recycler_view_product)
        public RecyclerView recycler_view_product;

        @UiElement(R.id.recycler_view_list_order)
        public RecyclerView recycler_view_list_order;

        @UiElement(R.id.layoutOrderHome)
        public LinearLayout layoutOrderHome;

        @UiElement(R.id.layoutProductGone)
        public LinearLayout layoutProductGone;

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.imageClose)
        public ImageView imageClose;

        @UiElement(R.id.search_button)
        public ImageView search_button;

        @UiElement(R.id.edit_filter)
        public EditText edit_filter;

        @UiElement(R.id.image_close)
        public ImageView image_close;

        @UiElement(R.id.layoutChooseCus)
        public LinearLayout layoutChooseCus;

        @UiElement(R.id.nameCustomers)
        public TextView nameCustomer;

        @UiElement(R.id.nameLevelCuss)
        public TextView nameLevelCus;

        @UiElement(R.id.idPriceDemo)
        public TextView idPriceDemo;

        @UiElement(R.id.priceSale)
        public TextView priceSale;

        @UiElement(R.id.allPrice)
        public TextView allPrice;

        @UiElement(R.id.btnChoose)
        public LinearLayout btnChoose;

        @UiElement(R.id.layoutShow)
        public LinearLayout layoutShow;

    }
}
