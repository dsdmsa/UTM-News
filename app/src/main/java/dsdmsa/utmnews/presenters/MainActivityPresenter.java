package dsdmsa.utmnews.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import dsdmsa.utmnews.FragmentNavigation;
import dsdmsa.utmnews.fragments.BaseFragment;
import dsdmsa.utmnews.mvp.MainActivityVP;

/**
 * Created by dsdmsa on 4/8/17.
 */
@InjectViewState
public class MainActivityPresenter extends MvpPresenter<MainActivityVP.View>
        implements MainActivityVP.Presenter,
        FragmentNavigation.Presenter{


    @Override
    public void addFragment(BaseFragment fragment) {
        getViewState().addFragment(fragment);
    }
}
