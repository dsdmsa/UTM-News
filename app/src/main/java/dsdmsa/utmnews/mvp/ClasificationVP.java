package dsdmsa.utmnews.mvp;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import dsdmsa.utmnews.models.Category;
import dsdmsa.utmnews.models.Tag;

/**
 * Created by dsdmsa on 4/8/17.
 */

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
