package dsdmsa.utmnews.presentation.mvp;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import dsdmsa.utmnews.domain.models.Category;


public interface HomeContract {
    interface View extends MvpView, LoadingView {
        void updateCategories(List<Category> categories);

        void hideRefreshLaout();

        void showRefreshLayout();
    }

    interface Presenter {
        void getCategories();
    }

}
