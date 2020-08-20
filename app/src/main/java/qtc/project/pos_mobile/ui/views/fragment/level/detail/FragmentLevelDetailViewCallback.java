package qtc.project.pos_mobile.ui.views.fragment.level.detail;

import qtc.project.pos_mobile.model.LevelCustomerModel;

public interface FragmentLevelDetailViewCallback {
    void onBackP();

    void callPopup(LevelCustomerModel model);

    void goHome();
}
