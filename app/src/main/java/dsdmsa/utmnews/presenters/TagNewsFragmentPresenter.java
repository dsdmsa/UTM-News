//package dsdmsa.utmnews.presenters;
//
//import com.arellomobile.mvp.InjectViewState;
//import com.arellomobile.mvp.MvpPresenter;
//
//import java.util.List;
//
//import javax.inject.Inject;
//
//import dsdmsa.utmnews.App;
//import dsdmsa.utmnews.models.Post;
//import dsdmsa.utmnews.mvp.CategoryNewsFragmentVP;
//import dsdmsa.utmnews.network.OnDataLoaded;
//import dsdmsa.utmnews.network.services.UtmServices;
//
///**
// * Created by dsdmsa on 4/8/17.
// */
//
//@InjectViewState
//public class TagNewsFragmentPresenter extends MvpPresenter<CategoryNewsFragmentVP.View> implements
//        CategoryNewsFragmentVP.Presenter,
//        OnDataLoaded<List<Post>> {
//
//    @Inject
//    UtmServices services;
//
//    @Override
//    public void getCategoryNewses(int tagId, int perPage, int page) {
//        App.getAppComponent().inject(this);
//        services.getNewsByTag(tagId, page, perPage, this);
//        getViewState().showProgressDialog();
//    }
//
//    @Override
//    public void onSuccess(List<Post> response) {
//        getViewState().hideProgressDialog();
//        getViewState().showNewses(response);
//    }
//
//    @Override
//    public void onError(String errorMsg) {
//        getViewState().hideProgressDialog();
//        getViewState().showInfoMessage(errorMsg);
//    }
//}
