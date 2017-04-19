package dsdmsa.utmnews.presenters;

import android.content.Intent;
import android.view.MenuItem;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.fragments.AboutFragment;
import dsdmsa.utmnews.fragments.BaseFragment;
import dsdmsa.utmnews.fragments.BookmarksFragment;
import dsdmsa.utmnews.fragments.CategoryListFragment;
import dsdmsa.utmnews.fragments.LatestNewsFragment;
import dsdmsa.utmnews.fragments.TagListFragment;
import dsdmsa.utmnews.mvp.FragmentNavigation;
import dsdmsa.utmnews.mvp.MainActivityVP;

/**
 * Created by dsdmsa on 4/8/17.
 */
@InjectViewState
public class MainActivityPresenter extends MvpPresenter<MainActivityVP.View>
        implements MainActivityVP.Presenter,
        FragmentNavigation.Presenter {


    public MainActivityPresenter() {
        getViewState().addFragment(LatestNewsFragment.newInstance());

//        ConnectionChangeReceiver connectionChangeReceiver = new ConnectionChangeReceiver();
//        connectionChangeReceiver.onReceive();

    }

    @Override
    public void addFragment(BaseFragment fragment) {
        getViewState().addFragment(fragment);
    }

    @Override
    public void setTitle(String title) {
        getViewState().setTootlbarTitile(title);
    }

    @Override
    public void onItemSelected(MenuItem menuItem) {
        BaseFragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.menu_categories:
                fragment = new CategoryListFragment();
                break;
            case R.id.menu_tags:
                fragment = new TagListFragment();
                break;
            case R.id.menu_bookmarks:
                fragment = new BookmarksFragment();
                break;
            case R.id.menu_info:
                fragment = new AboutFragment();
                break;
            default:
                fragment = new LatestNewsFragment();
        }
        getViewState().addFragment(fragment);
    }

    @Override
    public void openNetSettings(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting");
        App.getAppComponent().getContext().startActivity(intent);
    }

}
