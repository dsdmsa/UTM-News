package dsdmsa.utmnews.mvp;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import dsdmsa.utmnews.models.Post;

/**
 * Created by dsdmsa on 4/8/17.
 */

public interface LatestNewsFragmentVP {
    interface View extends MvpView, LoadingView {
        void addNewses(List<Post> newses);

        void refreshDatas(List<Post> response);

    }

    interface Presenter {
        void loadNewsOnPage(int page);

        void shareString(String str);

        void bookmarkPost(Post post);
    }
}
