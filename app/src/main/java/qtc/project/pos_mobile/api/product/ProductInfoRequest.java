package qtc.project.pos_mobile.api.product;

//@ApiRequest.ApiName("list_product_info")
//public class ProductInfoRequest extends ApiRequest<ProductInfoRequest.Service, BaseResponseModel<ProductModel.PackageInfoBean>, ProductInfoRequest.ApiParams>{
//
//    public ProductInfoRequest() {
//        super(ProductInfoRequest.Service.class, RequestOrigin.NONE, Consts.HOST_API, Consts.MODE, Consts.TRUST_CERTIFICATE);
//    }
//
//    @Override
//    protected void postAfterRequest(BaseResponseModel<ProductModel.PackageInfoBean> result) throws Exception {
//
//    }
//
//    @Override
//    protected Call<BaseResponseModel<ProductModel.PackageInfoBean>> call(ProductInfoRequest.ApiParams params) {
//        params.detect = "list_product";
//        return getService().call(params);
//    }
//
//
//    interface Service {
//        @Headers(Consts.HEADES)
//        @POST(Consts.REST_ENDPOINT)
//        Call<BaseResponseModel<ProductModel.PackageInfoBean>> call(@Body ProductInfoRequest.ApiParams params);
//    }
//
//    public static class ApiParams extends BaseApiParams {
//        public String detect;
//        public String id_category;
//    }
//}
