package dsdmsa.utmnews.mvp;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import dsdmsa.utmnews.models.SimplePost;


/**
 * Created by dsdmsa on 4/8/17.
 */

public interface LatestNewsFragmentVP {
    interface View extends MvpView, LoadingView {
        void addNewses(List<SimplePost> newses);

        void refreshDatas(List<SimplePost> response);

    }

    interface Presenter {
        void loadNewsOnPage(int page);

        void shareString(String str);

        void bookmarkPost(SimplePost post);
    }
}
