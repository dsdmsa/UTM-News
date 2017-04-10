package dsdmsa.utmnews.mvp;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import dsdmsa.utmnews.models.Post;

/**
 * Created by dsdmsa on 4/8/17.
 */

public interface BookmarksFragmentVP {
    interface View extends MvpView, LoadingView {
        void addNewses(List<Post> newses);
    }

    interface Presenter {
        void loadNews();
    }
}
