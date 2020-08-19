package qtc.project.pos_tablet.api.home.category;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.BaseApiParams;
import qtc.project.pos_tablet.helper.Consts;
import qtc.project.pos_tablet.model.BaseResponseModel;
import qtc.project.pos_tablet.model.CategoryModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@ApiRequest.ApiName("list_category")
public class CategoryRequest extends ApiRequest<CategoryRequest.Service, BaseResponseModel<CategoryModel>, CategoryRequest.ApiParams> {
    public CategoryRequest() {
        super(CategoryRequest.Service.class, RequestOrigin.NONE, Consts.HOST_API, Consts.MODE, Consts.TRUST_CERTIFICATE);
    }

    @Override
    protected void postAfterRequest(BaseResponseModel<CategoryModel> result) throws Exception {

    }

    @Override
    protected Call<BaseResponseModel<CategoryModel>> call(ApiParams params) {
        params.detect = "list_category";
        return getService().call(params);
    }


    interface Service {
        @Headers(Consts.HEADES)
        @POST(Consts.REST_ENDPOINT)
        Call<BaseResponseModel<CategoryModel>> call(@Body CategoryRequest.ApiParams params);
    }

    public static class ApiParams extends BaseApiParams {
        public String detect;
    }
}
