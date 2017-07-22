package dsdmsa.utmnews.presentation.activityes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;

import javax.inject.Inject;

import butterknife.BindView;
import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.domain.utils.FragmentNavigation;
import dsdmsa.utmnews.presentation.fragments.BookmarksFragment;
import dsdmsa.utmnews.presentation.fragments.HomeFragment;
import dsdmsa.utmnews.presentation.fragments.TagListFragment;
import dsdmsa.utmnews.presentation.mvp.MainActivityVP;
import dsdmsa.utmnews.presentation.presenters.MainActivityPresenter;
import es.dmoral.toasty.Toasty;

import static dsdmsa.utmnews.domain.utils.Constants.TIME_INTERVAL;

public class MainActivity extends BaseActivity implements
        MainActivityVP.View {

    @InjectPresenter(type = PresenterType.GLOBAL)
    MainActivityPresenter presenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @Inject
    protected FragmentNavigation fragmentNavigation;

    private long mBackPressed;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent().inject(this);
        fragmentNavigation.init(getSupportFragmentManager(), R.id.fragment_container);
        fragmentNavigation.showFragment(R.id.menu_home, new HomeFragment());

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        fragmentNavigation.showFragment(R.id.menu_home, new HomeFragment());
                        break;
                    case R.id.menu_tags:
                        fragmentNavigation.showFragment(R.id.menu_tags, new TagListFragment());
                        break;
                    case R.id.menu_bookmarks:
                        fragmentNavigation.showFragment(R.id.menu_bookmarks, new BookmarksFragment());
                        break;
                    case R.id.menu_search:
//                        fragmentNavigation.showFragment(R.id.menu_search, new HomeFragment());
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (fragmentNavigation.getSize() == 1) {
            if (mBackPressed + TIME_INTERVAL < System.currentTimeMillis()) {
                Toasty.info(this, "Press again to exit", Toast.LENGTH_SHORT).show();
                mBackPressed = System.currentTimeMillis();
                return;
            }
            super.onBackPressed();
        } else {
            navigation.setSelectedItemId(fragmentNavigation.bakPressed());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragmentNavigation.onDestory();
    }
}


