package dsdmsa.utmnews.presentation.mvp;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import dsdmsa.utmnews.domain.models.Category;
import dsdmsa.utmnews.domain.models.SimplePost;


public interface CategoryContract {
    interface View extends MvpView, LoadingView {
        void addNewses(List<SimplePost> newses);

        void refreshDatas(List<SimplePost> response);

        void setupRecyclerView();

        void clearDates();

        void hideInfoMessage();
    }

    interface Presenter {
        void getCategoryNewses(int page);

        void refresh();

        void setCategory(Category category);

        void bookmark(SimplePost post);
    }
}
