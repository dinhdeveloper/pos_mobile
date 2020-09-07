package qtc.project.pos_mobile.api.customer;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.BaseApiParams;
import qtc.project.pos_mobile.helper.Consts;
import qtc.project.pos_mobile.model.BaseResponseModel;
import qtc.project.pos_mobile.model.LevelCustomerModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
@ApiRequest.ApiName("list_level")
public class LevelCustomerRequest  extends ApiRequest<LevelCustomerRequest.Service, BaseResponseModel<LevelCustomerModel>, LevelCustomerRequest.ApiParams> {

    public LevelCustomerRequest() {
        super(LevelCustomerRequest.Service.class, RequestOrigin.NONE, Consts.HOST_API, Consts.MODE, Consts.TRUST_CERTIFICATE);
    }

    @Override
    protected void postAfterRequest(BaseResponseModel<LevelCustomerModel> result) throws Exception {

    }

    @Override
    protected Call<BaseResponseModel<LevelCustomerModel>> call(ApiParams params) {
        params.detect = "list_level";
        return getService().call(params);
    }


    interface Service {
        @Headers(Consts.HEADES)
        @POST(Consts.REST_ENDPOINT)
        Call<BaseResponseModel<LevelCustomerModel>> call(@Body LevelCustomerRequest.ApiParams params);
    }

    public static class ApiParams extends BaseApiParams {
        public String detect;
        public String id_business;
    }
}
