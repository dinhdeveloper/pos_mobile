package qtc.project.pos_tablet.ui.views.fragment.list_base;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos_tablet.activity.HomeActivityBackup;
import qtc.project.pos_tablet.model.BaseResponseModel;

public interface FragmentAdminManagerListBaseViewInterface extends BaseViewInterface {

    void init(HomeActivityBackup activity, FragmentAdminManagerListBaseViewCallback callback);

    void showEmptyList();

    void hideEmptyList();

    void setDataList(BaseResponseModel dataList);

    void setNoMoreLoading();

    void resetListData();

    void hideRootView();

    void showRootView();

    void clearListData();
}
