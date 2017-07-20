package dsdmsa.utmnews.presentation.mvp;

import dsdmsa.utmnews.fragments.BaseFragment;

/**
 * Created by dsdmsa on 4/8/17.
 */

public interface FragmentNavigation {
    interface View {
        void atachPresenter(Presenter presenter);

        String getTitle();

        void retry();

    }

    interface Presenter {
        void addFragment(BaseFragment fragment);

        void setTitle(String title);

        void retry();
    }
}
