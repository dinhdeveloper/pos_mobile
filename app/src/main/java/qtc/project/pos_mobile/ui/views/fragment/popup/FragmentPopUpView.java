package qtc.project.pos_mobile.ui.views.fragment.popup;

import android.widget.ImageView;
import android.widget.TextView;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos_mobile.R;
import qtc.project.pos_mobile.activity.HomeActivity;

public class FragmentPopUpView extends BaseView<FragmentPopUpView.UIContainer> implements FragmentPopUpViewInterface {

    HomeActivity activity;
    FragmentPopUpViewCallback callback;

    @Override
    public void init(HomeActivity activity, FragmentPopUpViewCallback callback) {
        this.callback = callback;
        this.activity = activity;
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentPopUpView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.custom_popup_price_detail;
    }



    public static class UIContainer extends BaseUiContainer {
        @UiElement(R.id.createProductDate)
        public TextView createProductDate;
        @UiElement(R.id.priceItem)
        public TextView priceItem;
        @UiElement(R.id.quantity)
        public TextView quantity;
        @UiElement(R.id.ticked)
        public ImageView ticked;
    }
}
