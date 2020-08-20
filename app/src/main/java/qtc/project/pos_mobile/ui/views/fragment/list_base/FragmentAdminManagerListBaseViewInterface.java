package qtc.project.pos_mobile.ui.views.fragment.list_base;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos_mobile.activity.HomeActivity;
import qtc.project.pos_mobile.model.BaseResponseModel;

public interface FragmentAdminManagerListBaseViewInterface extends BaseViewInterface {

    void init(HomeActivity activity, FragmentAdminManagerListBaseViewCallback callback);

    void showEmptyList();

    void hideEmptyList();

    void setDataList(BaseResponseModel dataList);

    void setNoMoreLoading();

    void resetListData();

    void hideRootView();

    void showRootView();

    void clearListData();
}
