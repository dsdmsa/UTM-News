package dsdmsa.utmnews.presentation.mvp;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import dsdmsa.utmnews.domain.models.SimplePost;


public interface SearchNewsContract {
    interface View extends MvpView, LoadingView {

        void addNewses(List<SimplePost> newses);

        void clearList();
    }

    interface Presenter {

        void setSearchKey(String key);

        void getNews(int page);

        void refreshNewses();

        void savePost(SimplePost post);
    }
}
