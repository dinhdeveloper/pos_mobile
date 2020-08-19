package qtc.project.pos_tablet.api.order;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.BaseApiParams;
import qtc.project.pos_tablet.helper.Consts;
import qtc.project.pos_tablet.model.BaseResponseModel;
import qtc.project.pos_tablet.model.OrderModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@ApiRequest.ApiName("list_order")
public class OrderRequest extends ApiRequest<OrderRequest.Service, BaseResponseModel<OrderModel>, OrderRequest.ApiParams> {

    public OrderRequest() {
        super(OrderRequest.Service.class, RequestOrigin.NONE, Consts.HOST_API, Consts.MODE, Consts.TRUST_CERTIFICATE);
    }

    @Override
    protected void postAfterRequest(BaseResponseModel<OrderModel> result) throws Exception {

    }

    @Override
    protected Call<BaseResponseModel<OrderModel>> call(OrderRequest.ApiParams params) {
        params.detect = "list_order";
        return getService().call(params);
    }


    interface Service {
        @Headers(Consts.HEADES)
        @POST(Consts.REST_ENDPOINT)
        Call<BaseResponseModel<OrderModel>> call(@Body OrderRequest.ApiParams params);
    }

    public static class ApiParams extends BaseApiParams {
        public String detect;
        public String time_filter;
        public String order_id;
        public String order_code;
        public String filter;
    }
}
