package qtc.project.pos_mobile.api.customer;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.BaseApiParams;
import qtc.project.pos_mobile.helper.Consts;
import qtc.project.pos_mobile.model.BaseResponseModel;
import qtc.project.pos_mobile.model.CustomerModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@ApiRequest.ApiName("list_customer")
public class CustomerRequest extends ApiRequest<CustomerRequest.Service, BaseResponseModel<CustomerModel>, CustomerRequest.ApiParams> {
    public CustomerRequest() {
        super(CustomerRequest.Service.class, RequestOrigin.NONE, Consts.HOST_API, Consts.MODE, Consts.TRUST_CERTIFICATE);
    }

    @Override
    protected void postAfterRequest(BaseResponseModel<CustomerModel> result) throws Exception {

    }

    @Override
    protected Call<BaseResponseModel<CustomerModel>> call(ApiParams params) {
        params.detect = "list_customer";
        return getService().call(params);
    }


    interface Service {
        @Headers(Consts.HEADES)
        @POST(Consts.REST_ENDPOINT)
        Call<BaseResponseModel<CustomerModel>> call(@Body CustomerRequest.ApiParams params);
    }

    public static class ApiParams extends BaseApiParams {
        public String detect;
        public String id_business;
        public String level_id;
        public String customer_filter;
        public String type_manager;
        public String page;

    }
}
