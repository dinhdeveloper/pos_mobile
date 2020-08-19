package qtc.project.pos_tablet.fragment.productdetail;

import android.content.Intent;
import android.util.Log;

import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos_tablet.activity.HomeActivity;
import qtc.project.pos_tablet.model.ProductModel;
import qtc.project.pos_tablet.ui.views.fragment.productdetail.FragmentProductDetailView;
import qtc.project.pos_tablet.ui.views.fragment.productdetail.FragmentProductDetailViewCallback;
import qtc.project.pos_tablet.ui.views.fragment.productdetail.FragmentProductDetailViewInterface;

public class FragmentProductDetail extends BaseFragment<FragmentProductDetailViewInterface, BaseParameters> implements FragmentProductDetailViewCallback {



    @Override
    protected void initialize() {
        HomeActivity activity = (HomeActivity) getActivity();
        view.init(activity, this);

        getIntent(activity);
    }

    private void getIntent(HomeActivity activity) {
        Intent intent = activity.getIntent();
        ProductModel productModel = (ProductModel) intent.getSerializableExtra("DETAIL");
        Log.e("HAHAHAH", productModel.getName());


    }

    @Override
    protected FragmentProductDetailViewInterface getViewInstance() {
        return new FragmentProductDetailView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

}
