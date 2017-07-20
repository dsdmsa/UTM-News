package dsdmsa.utmnews.presentation.mvp;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import dsdmsa.utmnews.models.Tag;

/**
 * Created by dsdmsa on 4/8/17.
 */

public interface TagsFragmentVP {
    interface View extends MvpView, LoadingView {

        void showTags(List<Tag> response);
    }

    interface Presenter {

    }
}
