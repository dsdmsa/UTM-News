package dsdmsa.utmnews.presentation.mvp;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import dsdmsa.utmnews.domain.models.Post;


public interface CategoryNewsFragmentVP {
    interface View extends MvpView, LoadingView {
        void showNewses(List<Post> response);
    }

    interface Presenter {
        void getCategoryNewses(int categoryId,int perPage,int page);
    }
}
