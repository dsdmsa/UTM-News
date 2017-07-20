package dsdmsa.utmnews.presentation.mvp;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import dsdmsa.utmnews.models.SimplePost;


/**
 * Created by dsdmsa on 4/8/17.
 */

public interface NewsFragmentVP {
    interface View extends MvpView, LoadingView {
        void addNewses(List<SimplePost> newses);

        void refreshDatas(List<SimplePost> response);

        void setupRecyclerView();
    }

    interface Presenter {
        void loadNewsOnPage(int page);

        void shareString(String str);

        void bookmarkPost(SimplePost post);

        void getSearchedNewses(String searchKey, int perPage, int page);

        void getCategoryNewses(int categoryId,int perPage,int page);

        void getNewsByTag(int tagId, int perPage, int page);

        void removeBookmarkPost(SimplePost post);
    }
}
