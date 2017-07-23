package dsdmsa.utmnews.presentation.mvp;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import dsdmsa.utmnews.domain.models.SimplePost;


public interface BookmarsContract {
    interface View extends MvpView, LoadingView {

        void addNewses(List<SimplePost> newses);

        void clearList();
    }

    interface Presenter {

        void refreshNewses();

        void bookmark(SimplePost post);
    }
}
