package dsdmsa.utmnews.presentation.mvp;

import com.arellomobile.mvp.MvpView;


public interface BookmarksFragmentVP {
    interface View extends MvpView{
        void showInfoMessage(String s);
    }

    interface Presenter {
    }
}
