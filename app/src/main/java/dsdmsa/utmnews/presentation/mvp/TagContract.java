package dsdmsa.utmnews.presentation.mvp;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import dsdmsa.utmnews.domain.models.Tag;


public interface TagContract {
    interface View extends MvpView, LoadingView {
        void showTags(List<Tag> response);

        void clear();
    }

    interface Presenter {
        void getTags();

        void refresh();
    }
}
