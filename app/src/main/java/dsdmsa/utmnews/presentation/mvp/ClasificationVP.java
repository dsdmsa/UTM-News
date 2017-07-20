package dsdmsa.utmnews.presentation.mvp;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import dsdmsa.utmnews.domain.models.Category;
import dsdmsa.utmnews.domain.models.Tag;


public interface ClasificationVP {
    interface View extends MvpView, LoadingView {

        void showCategories(List<Category> response);

        void showTags(List<Tag> response);
    }

    interface Presenter {

        void getTagList();

        void getCategoryList();

    }
}
