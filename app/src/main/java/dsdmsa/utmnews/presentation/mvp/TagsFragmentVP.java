package dsdmsa.utmnews.presentation.mvp;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import dsdmsa.utmnews.domain.models.Tag;


public interface TagsFragmentVP {
    interface View extends MvpView, LoadingView {

        void showTags(List<Tag> response);
    }

    interface Presenter {

    }
}
