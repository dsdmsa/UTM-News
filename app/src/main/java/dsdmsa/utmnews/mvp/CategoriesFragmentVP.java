package dsdmsa.utmnews.mvp;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import dsdmsa.utmnews.models.Category;

/**
 * Created by dsdmsa on 4/8/17.
 */

public interface CategoriesFragmentVP {
    interface View extends MvpView,LoadingView{

        void showCategories(List<Category> response);
    }
    interface Presenter{

    }
}
