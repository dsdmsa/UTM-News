package dsdmsa.utmnews.presentation.mvp;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import dsdmsa.utmnews.domain.models.SimplePost;


public interface SearchNewsContract {
    interface View extends MvpView, LoadingView {

        void addNewses(List<SimplePost> newses);

        void clearList();

        void hideInfoMessage();

        void showBottomLoadingView();

        void hideBottomLoadingView();
    }

    interface Presenter {

        void getNews(int page, String searchTerm);

        void refreshNewses(String searchTerm);

        void bookmark(SimplePost post);
    }
}
