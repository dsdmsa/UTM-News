package dsdmsa.utmnews.presentation.mvp;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import dsdmsa.utmnews.presentation.fragments.BaseFragment;


public interface HomeContract {
    interface View extends MvpView, LoadingView {
        void displayPages(List<BaseFragment> baseFragments);
    }

    interface Presenter {
        void getCategories();
    }

}
