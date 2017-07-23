package dsdmsa.utmnews.presentation.mvp;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.domain.models.Tag;


public interface TagListContract {
    interface View extends MvpView, LoadingView {
        void addNewses(List<SimplePost> newses);

        void refreshDatas(List<SimplePost> response);

        void setupRecyclerView();
    }

    interface Presenter {
        void getCategoryNewses(int page);

        void refresh();

        void setTag(Tag tag);
    }
}
