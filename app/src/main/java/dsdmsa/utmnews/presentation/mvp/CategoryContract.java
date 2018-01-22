package dsdmsa.utmnews.presentation.mvp;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import dsdmsa.utmnews.domain.models.SimplePost;


public interface CategoryContract {
    interface View extends MvpView, LoadingView {
        void addNewses(List<SimplePost> newses);

        void refreshDates(List<SimplePost> response);

        void setupRecyclerView();

        void clearDates();

        void hideInfoMessage();

        void showBottomLoadingView();

        void hideBottomLoadingView();
    }

    interface Presenter {
        void getCategoryNewses(int page, int categoryId);

        void refresh(int categoryId);

        void bookmark(SimplePost post);
    }
}
