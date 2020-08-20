package qtc.project.pos_mobile.api.customer;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.BaseApiParams;
import qtc.project.pos_mobile.helper.Consts;
import qtc.project.pos_mobile.model.BaseResponseModel;
import qtc.project.pos_mobile.model.CountCustomerModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
@ApiRequest.ApiName("count_customer_level")
public class CountCustomerRequest extends ApiRequest<CountCustomerRequest.Service, BaseResponseModel<CountCustomerModel>, CountCustomerRequest.ApiParams> {

    public CountCustomerRequest() {
        super(CountCustomerRequest.Service.class, RequestOrigin.NONE, Consts.HOST_API, Consts.MODE, Consts.TRUST_CERTIFICATE);
    }

    @Override
    protected void postAfterRequest(BaseResponseModel<CountCustomerModel> result) throws Exception {

    }

    @Override
    protected Call<BaseResponseModel<CountCustomerModel>> call(CountCustomerRequest.ApiParams params) {
        params.detect = "count_customer_level";
        return getService().call(params);
    }


    interface Service {
        @Headers(Consts.HEADES)
        @POST(Consts.REST_ENDPOINT)
        Call<BaseResponseModel<CountCustomerModel>> call(@Body CountCustomerRequest.ApiParams params);
    }

    public static class ApiParams extends BaseApiParams {
        public String detect;
        public String level_id;
    }
}