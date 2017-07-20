package dsdmsa.utmnews.presentation.mvp;

import com.arellomobile.mvp.MvpView;

/**
 * Created by dsdmsa on 4/8/17.
 */

public interface BookmarksFragmentVP {
    interface View extends MvpView{
        void showInfoMessage(String s);
    }

    interface Presenter {
    }
}
