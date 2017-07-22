package dsdmsa.utmnews.presentation.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import dsdmsa.utmnews.presentation.mvp.MainActivityVP;

/**
 * Created by dsdmsa on 4/8/17.
 */
@InjectViewState
public class MainActivityPresenter extends MvpPresenter<MainActivityVP.View>
        implements MainActivityVP.Presenter{

    }

//    private BaseFragment curentFragment;
//
//    public MainActivityPresenter() {
//        addFragment(LatestNewsFragment.newInstance());
//    }
//
//    @Override
//    public void addFragment(BaseFragment fragment) {
//        curentFragment = fragment;
//        getViewState().addFragment(fragment);
//    }
//
//    @Override
//    public void setTitle(String title) {
//        getViewState().setTootlbarTitile(title);
//    }
//
//    @Override
//    public void retry() {
//        curentFragment.retry();
//    }
//
//    @Override
//    public void onItemSelected(MenuItem menuItem) {
//        BaseFragment fragment = null;
//        switch (menuItem.getItemId()) {
//            case R.id.menu_categories:
//                fragment = new CategoryListFragment();
//                break;
//            case R.id.menu_tags:
//                fragment = new TagListFragment();
//                break;
//            case R.id.menu_bookmarks:
//                fragment = new BookmarksFragment();
//                break;
//            case R.id.menu_info:
//                fragment = new AboutFragment();
//                break;
//            default:
//                fragment = new LatestNewsFragment();
//        }
//        getViewState().addFragment(fragment);
//    }
//
//    @Override
//    public void openNetSettings(){
//        Intent intent = new Intent(ACTION_PICK_WIFI_NETWORK);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        App.getAppComponent().getApp().startActivity(intent);
//    }

//}
