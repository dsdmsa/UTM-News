package dsdmsa.utmnews.mvp;

import com.arellomobile.mvp.MvpView;

import dsdmsa.utmnews.fragments.BaseFragment;

/**
 * Created by dsdmsa on 4/8/17.
 */

public interface MainActivityVP {
    interface View extends MvpView{
        void addFragment(BaseFragment fragment);
    }
    interface Presenter{

    }
}
