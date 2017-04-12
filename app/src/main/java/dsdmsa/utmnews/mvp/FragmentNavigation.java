package dsdmsa.utmnews.mvp;

import dsdmsa.utmnews.fragments.BaseFragment;

/**
 * Created by dsdmsa on 4/8/17.
 */

public interface FragmentNavigation {
    interface View {
        void atachPresenter(Presenter presenter);

        String getTitle();
    }

    interface Presenter {
        void addFragment(BaseFragment fragment);

        void showPostDetails(String url);
    }
}
