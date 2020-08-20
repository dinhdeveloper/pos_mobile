package qtc.project.pos_mobile.ui.views.fragment.level;

import qtc.project.pos_mobile.model.LevelCustomerModel;

public interface FragmentLevelCustomerViewCallback {
    void goToLevelDetail(LevelCustomerModel model);

    void goHome();

    void callNav();
}
