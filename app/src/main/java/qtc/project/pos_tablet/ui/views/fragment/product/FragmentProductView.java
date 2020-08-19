package qtc.project.pos_tablet.ui.views.fragment.product;

import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import b.laixuantam.myaarlibrary.dependency.AppProvider;
import b.laixuantam.myaarlibrary.helper.KeyboardUtils;
import qtc.project.pos_tablet.R;
import qtc.project.pos_tablet.activity.HomeActivity;
import qtc.project.pos_tablet.adapter.home.CategoryAdapter;
import qtc.project.pos_tablet.adapter.product.ProductAdapter;
import qtc.project.pos_tablet.adapter.product.ProductInfoAdapter;
import qtc.project.pos_tablet.adapter.product.ProductProductAdapter;
import qtc.project.pos_tablet.helper.Consts;
import qtc.project.pos_tablet.model.CategoryModel;
import qtc.project.pos_tablet.model.PackageInfoModel;
import qtc.project.pos_tablet.model.ProductModel;

public class FragmentProductView extends BaseView<FragmentProductView.UIContainer> implements FragmentProductViewInterface {


    HomeActivity activity;
    FragmentProductViewCallback callback;
    ProductProductAdapter productAdapter;
    ArrayList<ProductModel> arrayList = new ArrayList<>();

    boolean enableLoadMore = true;

    String gia;
    boolean click = false;

    private Timer timer = new Timer();
    private final long DELAY = 1000;
    String BARCODE,QRCODE ;
    String PRODUCT_NAME ;

    @Override
    public void init(HomeActivity activity, FragmentProductViewCallback callback) {
        this.activity = activity;
        this.callback = callback;
        KeyboardUtils.setupUI(getView(), activity);
        initRecycler();
        onClick();

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

    }

    private void onClick() {
        //BARCODE
        ui.imvInBarcode.setOnClickListener(v -> {
            if (callback!=null)
            {
                //callback.inBarCode(PRODUCT_NAME,BARCODE,"0");
                callback.connetUSB();
            }
        });

        ui.imvInQrCode.setOnClickListener(v -> {
            if (callback!=null)
            {
                callback.inBarCode(PRODUCT_NAME,QRCODE,"1");
            }
        });

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
        //search customer
        ui.edit_filter.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        //ui.edit_filter.setInputType();
        ui.edit_filter.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (ui.edit_filter.getText().toString() != null) {
                        searchProduct(ui.edit_filter.getText().toString());
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
                    searchProduct(ui.edit_filter.getText().toString());
                } else {
                    Toast.makeText(activity, "Không có kết quả tìm kiếm!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //xos search
        ui.image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
                productAdapter.notifyDataSetChanged();
                ui.edit_filter.setText(null);
                callback.callAllData();
                callback.resetPage();
                enableLoadMore = true;
            }
        });

//        //sua
//        ui.imageEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(activity, "Chọn lô muốn chỉnh sửa", Toast.LENGTH_SHORT).show();
//                click = true;
//            }
//        });
    }

    private void searchProduct(String search) {
        if (callback != null) {
            if (search != null) {
                callback.searchProduct(search);
            } else {
                callback.callAllData();
            }
        }
    }
    public void genarateScanBarcode(String resultCode){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix;
            bitMatrix = multiFormatWriter.encode(resultCode, BarcodeFormat.CODE_128, 300, 150);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            ui.barCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public void genarateScanQrcode(String resultCode){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix;
            bitMatrix = multiFormatWriter.encode(resultCode, BarcodeFormat.QR_CODE, 300, 300);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            ui.qrCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
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
                    ui.layoutProductGone.setVisibility(View.VISIBLE);
                    ui.layoutProduct.setVisibility(View.GONE);
                    enableLoadMore = true;
                }
            }

            @Override
            public void onHeaderItemClick() {
                if (callback != null)
                    {
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
    public void initRecyclerViewProduct(ProductModel[] list) {
        if (list == null || list.length == 0) {
            if (arrayList.size() == 0)
                showEmptyList();
            return;
        }
        for (ProductModel model : list) {
            if (Integer.valueOf(model.getTotal_stock()) >= 0) {
                arrayList.add(model);
            }
        }
        if (arrayList.size() == 0) {
            callback.loadMore();
        }
        productAdapter.notifyDataSetChanged();
    }

    private void showEmptyList() {
    }


    public void initRecycler() {
        ui.recycler_view_product.getRecycledViewPool().clear();
        productAdapter = new ProductProductAdapter(activity, arrayList);
        ui.recycler_view_product.setLayoutManager(new GridLayoutManager(getContext(), 4));
        ui.recycler_view_product.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
        productAdapter.setListener(new ProductProductAdapter.ProductProductAdapterListener() {
            @Override
            public void onItemClick(ProductModel model) {
//                for (int i = 0; i < model.getListDataProduct().size(); i++) {
//                    ui.tonKhoAnToan.setText(model.getListDataProduct().get(i).getQuantity_storage());
//                }
                ui.layoutProduct.setVisibility(View.VISIBLE);
                ui.layoutProductGone.setVisibility(View.GONE);
                AppProvider.getImageHelper().displayImage(Consts.HOST_API + model.getImage(), ui.imageProduct, null, R.drawable.imageloading);
                ui.nameProduct.setText(model.getName());
                PRODUCT_NAME = model.getName();
                ui.idProduct.setText(model.getId_code());
                ui.description.setText(model.getDescription());
                BARCODE = model.getBarcode();
                QRCODE = model.getQr_code();
                ui.tvBarCode.setText(model.getBarcode());
                ui.tvQrcode.setText(model.getQr_code());
                if (model.getListDataProduct().size() > 0) {
                    String pattern = "###,###.###";
                    DecimalFormat decimalFormat = new DecimalFormat(pattern);
                    ui.priceProduct.setVisibility(View.VISIBLE);
                    ui.tonKho.setVisibility(View.VISIBLE);
                    ui.priceProduct.setText(decimalFormat.format(Long.valueOf(model.getSale_price())) + " đ");
                    ui.tonKho.setText(model.getTotal_stock());
                    ui.tonKhoAnToan.setText(model.getQuantity_safetystock());
                }
                else {
                    String pattern = "###,###.###";
                    DecimalFormat decimalFormat = new DecimalFormat(pattern);
                    ui.priceProduct.setVisibility(View.GONE);
                    ui.tonKho.setVisibility(View.GONE);
                    ui.tonKhoAnToan.setText(model.getQuantity_safetystock());
                }
                try {
                    genarateScanBarcode(model.getBarcode());
                    genarateScanQrcode(model.getQr_code());
                }catch (Exception e){
                    Log.e("Ex",e.getMessage());
                }

                ui.choosePrice.setVisibility(View.GONE);
//                ui.choosePrice.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (callback != null) {
//                            callback.callPopup(activity, model);
//                        }
//                    }
//                });

                ui.imageEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        click = false;
                        ui.btnUpdate.setVisibility(View.VISIBLE);
                        ui.imageEdit.setBackground(ContextCompat.getDrawable(activity, R.drawable.custom_background_close_order));

                        ui.nameProduct.setEnabled(true);
                        ui.nameProduct.isFocused();
                        ui.nameProduct.requestFocus();

                        ui.tonKhoAnToan.setEnabled(true);
                        ui.tonKhoAnToan.isFocused();

                        ui.description.setEnabled(true);
                        ui.description.isFocused();

                    }
                });

                ui.btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ProductModel productModel = new ProductModel();
                        productModel.setId(model.getId());
                        productModel.setName(ui.nameProduct.getText().toString());
                        productModel.setDescription(ui.description.getText().toString());
                        productModel.setQuantity_safetystock(ui.tonKhoAnToan.getText().toString());

                        if (callback != null) {
                            callback.updateProductDetail(productModel);
                        }
                    }
                });
            }

            @Override
            public void onRequestLoadMoreProduct() {
                if (callback != null && enableLoadMore)
                    callback.loadMore();
            }
        });

    }

    @Override
    public void initDataDialog(ProductModel model) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View popupView = layoutInflater.inflate(R.layout.custom_popup_price_detail, null);
        RecyclerView recycler_view_price = popupView.findViewById(R.id.recycler_view_price);
        LinearLayout btnChon = popupView.findViewById(R.id.btnChon);

        recycler_view_price.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        ProductInfoAdapter infoAdapters = new ProductInfoAdapter(activity, model.getListDataProduct());
        recycler_view_price.setAdapter(infoAdapters);


        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setView(popupView);
        AlertDialog dialog = alert.create();
        //dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        infoAdapters.setOnItemClickListener(new ProductInfoAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClickListener(PackageInfoModel model) {
                gia = model.getSale_price();
            }
        });

        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pattern = "###,###.###";
                DecimalFormat decimalFormat = new DecimalFormat(pattern);
                if (gia != null) {
                    ui.priceProduct.setText(decimalFormat.format(Long.valueOf(gia)) + " đ");
                } else {
                    ui.priceProduct.setText(model.getSale_price());
                }
                ui.tonKhoAnToan.setText(model.getQuantity_safetystock());
                dialog.dismiss();
            }
        });
    }

    @Override
    public void resetLayout() {
        ui.layoutProduct.setVisibility(View.GONE);
        ui.layoutProductGone.setVisibility(View.VISIBLE);
        ui.imageEdit.setBackground(ContextCompat.getDrawable(activity, R.color.hinhnen));

    }

    @Override
    public void showUpdateSuccess() {
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
                ui.imageEdit.setBackground(ContextCompat.getDrawable(activity, R.drawable.border_shape_white_layout_search_contact));
                if (callback != null)
                    callback.callAllData();
                dialog.dismiss();
            }
        });
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

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentProductView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_product;
    }

    public static class UIContainer extends BaseUiContainer {
        @UiElement(R.id.recycler_view_category)
        public RecyclerView recycler_view_category;

        @UiElement(R.id.recycler_view_product)
        public RecyclerView recycler_view_product;

        @UiElement(R.id.imageProduct)
        public ImageView imageProduct;

        @UiElement(R.id.imageEdit)
        public ImageView imageEdit;

        @UiElement(R.id.nameProduct)
        public TextView nameProduct;

        @UiElement(R.id.idProduct)
        public TextView idProduct;

        @UiElement(R.id.priceProduct)
        public TextView priceProduct;

        @UiElement(R.id.tonKho)
        public TextView tonKho;

        @UiElement(R.id.tonKhoAnToan)
        public TextView tonKhoAnToan;

        @UiElement(R.id.description)
        public TextView description;

        @UiElement(R.id.layoutProduct)
        public LinearLayout layoutProduct;

        @UiElement(R.id.layoutProductGone)
        public LinearLayout layoutProductGone;

        @UiElement(R.id.choosePrice)
        public LinearLayout choosePrice;

        @UiElement(R.id.btnUpdate)
        public LinearLayout btnUpdate;

        @UiElement(R.id.fragmentHome)
        public LinearLayout fragmentHome;

        @UiElement(R.id.edit_filter)
        public EditText edit_filter;

        @UiElement(R.id.image_close)
        public ImageView image_close;

        @UiElement(R.id.image_search)
        public ImageView image_search;

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.qrCode)
        public ImageView qrCode;

        @UiElement(R.id.barCode)
        public ImageView barCode;

        @UiElement(R.id.tvBarCode)
        public TextView tvBarCode;

        @UiElement(R.id.tvQrcode)
        public TextView tvQrcode;

        @UiElement(R.id.imvHome)
        public ImageView imvHome;

        @UiElement(R.id.imvInBarcode)
        public ImageView imvInBarcode;

        @UiElement(R.id.imvInQrCode)
        public ImageView imvInQrCode;


    }
}
