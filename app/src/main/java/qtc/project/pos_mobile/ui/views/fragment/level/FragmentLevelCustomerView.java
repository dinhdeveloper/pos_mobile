package qtc.project.pos_mobile.ui.views.fragment.level;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos_mobile.R;
import qtc.project.pos_mobile.activity.HomeActivity;
import qtc.project.pos_mobile.adapter.level.LevelCustomerAdapter;
import qtc.project.pos_mobile.model.LevelCustomerModel;

public class FragmentLevelCustomerView extends BaseView<FragmentLevelCustomerView.UIContainer> implements FragmentLevelCustomerViewInterface{
    HomeActivity activity;
    FragmentLevelCustomerViewCallback callback;
    @Override
    public void init(HomeActivity activity, FragmentLevelCustomerViewCallback callback) {
        this.activity = activity;
        this.callback = callback;
        ui.title_header.setText("Cấp độ khách hàng");

        ui.imvHome.setOnClickListener(v -> {
            if (callback!=null)
                callback.goHome();
        });
        ui.imageNavLeft.setOnClickListener(v -> {
            if (callback!=null){
                callback.callNav();
            }
        });
    }

    @Override
    public void initRecyclerView(ArrayList<LevelCustomerModel> list) {
        if (list!=null){
            ui.recycler_view_list.setVisibility(View.VISIBLE);
            ui.layoutNone.setVisibility(View.GONE);
            LevelCustomerAdapter adapter = new LevelCustomerAdapter(activity, list);
            ui.recycler_view_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
            ui.recycler_view_list.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            adapter.setListener(model -> {
                if (callback!=null)
                    callback.goToLevelDetail(model);
            });
        }
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentLevelCustomerView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_level_customer;
    }



    public class UIContainer extends BaseUiContainer {
        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.title_header)
        public TextView title_header;

        @UiElement(R.id.imvHome)
        public ImageView imvHome;

        @UiElement(R.id.layoutNone)
        public LinearLayout layoutNone;

        @UiElement(R.id.recycler_view_list)
        public RecyclerView recycler_view_list;

        //
    }
}
