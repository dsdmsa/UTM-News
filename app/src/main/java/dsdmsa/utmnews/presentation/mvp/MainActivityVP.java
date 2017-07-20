package dsdmsa.utmnews.presentation.mvp;

import android.view.MenuItem;

import com.arellomobile.mvp.MvpView;

import dsdmsa.utmnews.fragments.BaseFragment;

/**
 * Created by dsdmsa on 4/8/17.
 */

public interface MainActivityVP {
    interface View extends MvpView{
        void addFragment(BaseFragment fragment);

        void setTootlbarTitile(String title);
    }

    interface Presenter{

        void openNetSettings();

        void onItemSelected(MenuItem menuItem);
    }
}
