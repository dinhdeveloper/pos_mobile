package qtc.project.pos_tablet.ui.views.fragment.productdetail;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos_tablet.R;
import qtc.project.pos_tablet.activity.HomeActivity;

public class FragmentProductDetailView  extends BaseView<FragmentProductDetailView.UIContainer> implements FragmentProductDetailViewInterface {
    HomeActivity activity;
    FragmentProductDetailViewCallback callback;

    @Override
    public void init(HomeActivity activity, FragmentProductDetailViewCallback callback) {
        this.activity = activity;
        this.callback = callback;
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentProductDetailView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.custom_layout_product_detail;
    }

    public static class UIContainer extends BaseUiContainer {
    }
}
