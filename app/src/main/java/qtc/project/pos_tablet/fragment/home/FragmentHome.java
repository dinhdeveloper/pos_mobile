package qtc.project.pos_tablet.fragment.home;

import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import b.laixuantam.myaarlibrary.widgets.dialog.alert.KAlertDialog;
import info.androidhive.barcode.BarcodeReader;
import qtc.project.pos_tablet.activity.HomeActivity;
import qtc.project.pos_tablet.api.customer.CustomerRequest;
import qtc.project.pos_tablet.api.home.category.CategoryRequest;
import qtc.project.pos_tablet.api.home.product.ProductRequest;
import qtc.project.pos_tablet.api.order.CreateOrderRequest;
import qtc.project.pos_tablet.database.DatabaseProvider;
import qtc.project.pos_tablet.dependency.AppProvider;
import qtc.project.pos_tablet.model.BaseResponseModel;
import qtc.project.pos_tablet.model.CategoryModel;
import qtc.project.pos_tablet.model.CustomerModel;
import qtc.project.pos_tablet.model.ListOrderModel;
import qtc.project.pos_tablet.model.ProductModel;
import qtc.project.pos_tablet.ui.views.fragment.home.FragmentHomeView;
import qtc.project.pos_tablet.ui.views.fragment.home.FragmentHomeViewCallback;
import qtc.project.pos_tablet.ui.views.fragment.home.FragmentHomeViewInterface;

public class FragmentHome extends BaseFragment<FragmentHomeViewInterface, BaseParameters> implements FragmentHomeViewCallback, BarcodeReader.BarcodeReaderListener {

    private String cateId = null;
    ArrayList<CustomerModel> list;
    HomeActivity activity;
    int page =1;
    private int totalPage = 0;

    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);
        showProgress();
        requestDataCategory();
        requestDataProduct();

//        BarcodeDetector barcodeDetector =
//                new BarcodeDetector.Builder(activity)
//                        .setBarcodeFormats(Barcode.QR_CODE)//QR_CODE)
//                        .build();
//
//        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
//            @Override
//            public void release() {
//
//            }
//
//            @Override
//            public void receiveDetections(Detector.Detections<Barcode> detections) {
//                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
//                if (barcodes.size() != 0) {
//                    Log.e("AAAAA",barcodes.get(0).displayValue+"");
//                }
//            }
//        });


    }

    private void requestDataProduct() {

        ProductRequest.ApiParams params = new ProductRequest.ApiParams();
        if (cateId != null) {
            params.id_category = cateId;
        }
        params.page = String.valueOf(page);
        showProgress();
        AppProvider.getApiManagement().call(ProductRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<ProductModel> result) {
                dismissProgress();
                if (!TextUtils.isEmpty(result.getSuccess()) && Objects.requireNonNull(result.getSuccess()).equalsIgnoreCase("true")) {
                    if (!TextUtils.isEmpty(result.getTotal_page())) {
                        totalPage = Integer.valueOf(result.getTotal_page());
                        if (page == totalPage) {
                            view.setNoMoreLoading();
                        }
                    } else {
                        view.setNoMoreLoading();
                    }
                    view.initRecyclerViewProduct(result.getData(),0);
                } else {
                    if (!TextUtils.isEmpty(result.getMessage()))
                        showAlert(result.getMessage(), KAlertDialog.ERROR_TYPE);
                    else
                        showAlert("Không thể tải dữ liệu.", KAlertDialog.ERROR_TYPE);
                }
            }

            @Override
            public void onError(ErrorApiResponse error) {
                dismissProgress();
                Log.e("onError", error.message);
            }

            @Override
            public void onFail(ApiRequest.RequestError error) {
                dismissProgress();
                Log.e("onFail", error.name());
            }
        });
    }

    private void requestDataCategory() {
        CategoryRequest.ApiParams params = new CategoryRequest.ApiParams();
        showProgress();
        AppProvider.getApiManagement().call(CategoryRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<CategoryModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<CategoryModel> body) {
                dismissProgress();
                if (body != null) {
                    ArrayList<CategoryModel> dataBeans = new ArrayList<>();
                    if (body.getData() != null && body.getData().length > 0)
                        dataBeans.addAll(Arrays.asList(body.getData()));
                    view.initRecyclerViewCaregory(dataBeans);
                }
            }

            @Override
            public void onError(ErrorApiResponse error) {
                dismissProgress();
                Log.e("onError", error.message);
            }

            @Override
            public void onFail(ApiRequest.RequestError error) {
                dismissProgress();
                Log.e("onFail", error.name());
            }
        });
    }

    @Override
    protected FragmentHomeViewInterface getViewInstance() {
        return new FragmentHomeView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void onChangToCategoryDetail(CategoryModel model) {
        cateId = model.getId();
        ProductRequest.ApiParams params = new ProductRequest.ApiParams();
        if (cateId != null) {
            params.id_category = cateId;
        }
        showProgress();
        AppProvider.getApiManagement().call(ProductRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<ProductModel> result) {
                dismissProgress();
                if (!TextUtils.isEmpty(result.getSuccess()) && Objects.requireNonNull(result.getSuccess()).equalsIgnoreCase("true")) {
                    if (!TextUtils.isEmpty(result.getTotal_page())) {
                        totalPage = Integer.valueOf(result.getTotal_page());
                        if (page == totalPage) {
                            view.setNoMoreLoading();
                        }
                    } else {
                        view.setNoMoreLoading();
                    }
                    view.clearListDataProduct();
                    view.initRecyclerViewProduct(result.getData(),0);
                } else {
                    if (!TextUtils.isEmpty(result.getMessage()))
                        showAlert(result.getMessage(), KAlertDialog.ERROR_TYPE);
                    else
                        showAlert("Không thể tải dữ liệu.", KAlertDialog.ERROR_TYPE);
                }

            }

            @Override
            public void onError(ErrorApiResponse error) {
                dismissProgress();
                Log.e("onError", error.message);
            }

            @Override
            public void onFail(ApiRequest.RequestError error) {
                dismissProgress();
                Log.e("onFail", error.name());
            }
        });
    }

    @Override
    public void callPopup() {
        callDataCustomer();
    }

    @Override
    public void callAllData() {
        ProductRequest.ApiParams params = new ProductRequest.ApiParams();
        resetPage();
        params.page = String.valueOf(page);
        showProgress();
        AppProvider.getApiManagement().call(ProductRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<ProductModel> result) {
                dismissProgress();
                if (!TextUtils.isEmpty(result.getSuccess()) && Objects.requireNonNull(result.getSuccess()).equalsIgnoreCase("true")) {
                    if (!TextUtils.isEmpty(result.getTotal_page())) {
                        totalPage = Integer.valueOf(result.getTotal_page());
                        if (page == totalPage) {
                            view.setNoMoreLoading();
                        }
                    } else {
                        view.setNoMoreLoading();
                    }
                    view.initRecyclerViewProduct(result.getData(),0);
                } else {
                    if (!TextUtils.isEmpty(result.getMessage()))
                        showAlert(result.getMessage(), KAlertDialog.ERROR_TYPE);
                    else
                        showAlert("Không thể tải dữ liệu.", KAlertDialog.ERROR_TYPE);
                }
            }

            @Override
            public void onError(ErrorApiResponse error) {
                dismissProgress();
                Log.e("onError", error.message);
            }

            @Override
            public void onFail(ApiRequest.RequestError error) {
                dismissProgress();
                Log.e("onFail", error.name());
            }
        });
    }


    @Override
    public void tinhTien(ArrayList<ListOrderModel> arList, String id_customer, String tongtien, String discount) {
        showProgress();
        CreateOrderRequest.ApiParams params = new CreateOrderRequest.ApiParams();
        StringBuilder id_product_pack = new StringBuilder();
        StringBuilder quantity_product_pack = new StringBuilder();
        StringBuilder price_product_pack = new StringBuilder();

        for (ListOrderModel orderModel : arList) {
            params.employee_id = AppProvider.getPreferences().getUserModel().getId();
            if (!TextUtils.isEmpty(id_customer)){
                params.id_customer = id_customer;
            }
            id_product_pack.append(orderModel.getId() + ",");
            price_product_pack.append(orderModel.getPriceProduct() + ",");
            quantity_product_pack.append(orderModel.getQuantityProduct() + ",");
            // int tongtien = Integer.parseInt(orderModel.getPriceProduct()) * Integer.parseInt(orderModel.getQuantityProduct());
            params.total = tongtien;
        }

        params.id_product_pack = String.valueOf(id_product_pack);
        params.price_product_pack = String.valueOf(price_product_pack);
        params.quantity_product_pack = String.valueOf(quantity_product_pack);
//        //random order_id_code;
//        Calendar c = Calendar.getInstance();
//        int year = c.get(Calendar.YEAR);
//        // Trả về giá trị từ 0 - 11
//        int month = c.get(Calendar.MONTH);
//        int day = c.get(Calendar.DAY_OF_MONTH);
//        int hour = c.get(Calendar.HOUR_OF_DAY);
//        int minute = c.get(Calendar.MINUTE);
//        int second = c.get(Calendar.SECOND);
//        int millis = c.get(Calendar.MILLISECOND);
//
//        String id_code = String.valueOf("DH" + year + "" + (month + 1) + "" + day + "" + hour + "" + minute + "" + second);
//        params.id_code = id_code;

        AppProvider.getApiManagement().call(CreateOrderRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel>() {
            @Override
            public void onSuccess(BaseResponseModel body) {
                dismissProgress();
                if (body.getSuccess().equals("true")) {
                    DatabaseProvider provider = new DatabaseProvider(activity);
                    provider.deleteDatabase();
                    //view.showBill(list);
                    view.truyenIntent(arList);
                } else if (body.getSuccess().equals("false")) {
                    Toast.makeText(getContext(), "" + body.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(ErrorApiResponse error) {
                dismissProgress();
                Log.e("onError", error.message);
            }

            @Override
            public void onFail(ApiRequest.RequestError error) {
                dismissProgress();
                Log.e("onFail", error.name());
            }
        });
    }


    @Override
    public void callDataSearchProduct(String search, int statuss) {
        ProductRequest.ApiParams params = new ProductRequest.ApiParams();
        if (search != null) {
            params.product = search;
        }
        showProgress();
        AppProvider.getApiManagement().call(ProductRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<ProductModel> body) {
                dismissProgress();

                if (body != null) {
                    view.clearListDataProduct();
                    view.setNoMoreLoading();
                    view.initRecyclerViewProduct(body.getData(),statuss);
                }
            }

            @Override
            public void onError(ErrorApiResponse error) {
                dismissProgress();
                Log.e("onError", error.message);
            }

            @Override
            public void onFail(ApiRequest.RequestError error) {
                dismissProgress();
                Log.e("onFail", error.name());
            }
        });
    }

    @Override
    public void callDataSearchCus(String toString) {
        showProgress();
        CustomerRequest.ApiParams params = new CustomerRequest.ApiParams();
        if (toString != null) {
            params.customer_filter = toString;
        }
        AppProvider.getApiManagement().call(CustomerRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<CustomerModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<CustomerModel> body) {
                if (body != null) {
                    dismissProgress();
                    ArrayList<CustomerModel> list = new ArrayList<>();
                    list.addAll(Arrays.asList(body.getData()));
                    view.initCustomer(list);
                }
            }

            @Override
            public void onError(ErrorApiResponse error) {
                dismissProgress();
                Log.e("onError", error.message);
            }

            @Override
            public void onFail(ApiRequest.RequestError error) {
                dismissProgress();
                Log.e("onFail", error.name());
            }
        });
    }

    @Override
    public void callAllDataCustomer() {
        showProgress();
        CustomerRequest.ApiParams params = new CustomerRequest.ApiParams();
        AppProvider.getApiManagement().call(CustomerRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<CustomerModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<CustomerModel> body) {
                if (body != null) {
                    dismissProgress();
                    ArrayList<CustomerModel> list = new ArrayList<>();
                    list.addAll(Arrays.asList(body.getData()));
                    view.initCustomer(list);
                }
            }

            @Override
            public void onError(ErrorApiResponse error) {
                dismissProgress();
                Log.e("onError", error.message);
            }

            @Override
            public void onFail(ApiRequest.RequestError error) {
                dismissProgress();
                Log.e("onFail", error.name());
            }
        });
    }

    @Override
    public void loadMore() {
        ++page;

        if (totalPage > 0 && page <= totalPage) {

            requestDataProduct();
        } else {
            view.setNoMoreLoading();
        }
    }

    @Override
    public void resetPage() {
        page =1;
        totalPage = 0;
        cateId =null;
    }

    private void callDataCustomer() {
        showProgress();
        CustomerRequest.ApiParams params = new CustomerRequest.ApiParams();
        AppProvider.getApiManagement().call(CustomerRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<CustomerModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<CustomerModel> body) {
                if (body != null) {
                    dismissProgress();
                    list = new ArrayList<>();
                    list.addAll(Arrays.asList(body.getData()));
                    showPopup(list);
                }
            }

            @Override
            public void onError(ErrorApiResponse error) {
                dismissProgress();
                Log.e("onError", error.message);
            }

            @Override
            public void onFail(ApiRequest.RequestError error) {
                dismissProgress();
                Log.e("onFail", error.name());
            }
        });
    }

    public void showPopup(ArrayList<CustomerModel> list) {
        if (list.size() != 0) {
            view.initCustomer(list);
        }
    }

    @Override
    public void onScanned(Barcode barcode) {
        Log.e("AAAAA", barcode.displayValue);
    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {
        Log.e("AAAAA", barcodes.get(0).displayValue);
    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {

    }
}
