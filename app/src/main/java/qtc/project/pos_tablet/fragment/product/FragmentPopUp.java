package qtc.project.pos_tablet.fragment.product;

import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos_tablet.activity.HomeActivity;
import qtc.project.pos_tablet.ui.views.fragment.popup.FragmentPopUpView;
import qtc.project.pos_tablet.ui.views.fragment.popup.FragmentPopUpViewCallback;
import qtc.project.pos_tablet.ui.views.fragment.popup.FragmentPopUpViewInterface;

public class FragmentPopUp extends BaseFragment<FragmentPopUpViewInterface, BaseParameters> implements FragmentPopUpViewCallback {
    @Override
    protected void initialize() {
        HomeActivity activity = (HomeActivity) getActivity();
        view.init(activity, this);
        
        getIntentData(activity);
    }

    private void getIntentData(HomeActivity activity) {
//        Intent intent = activity.getIntent();
//        Log.e("hhaha",intent.getStringExtra("model")+"");

    }

    @Override
    protected FragmentPopUpViewInterface getViewInstance() {
        return new FragmentPopUpView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }
}
