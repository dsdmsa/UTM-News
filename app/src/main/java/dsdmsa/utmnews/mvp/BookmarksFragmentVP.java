package dsdmsa.utmnews.mvp;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import dsdmsa.utmnews.models.SimplePost;

/**
 * Created by dsdmsa on 4/8/17.
 */

public interface BookmarksFragmentVP {
    interface View extends MvpView, LoadingView {
        void addNewses(List<SimplePost> newses);
    }

    interface Presenter {
        void loadNews();

        void removePost(SimplePost post);
    }
}
