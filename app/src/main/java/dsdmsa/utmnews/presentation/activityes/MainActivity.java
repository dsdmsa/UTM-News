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

import butterknife.BindView;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.presentation.fragments.BaseFragment;
import dsdmsa.utmnews.presentation.fragments.HomeFragment;
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

    private long mBackPressed;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragment(new HomeFragment());
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        addFragment(new HomeFragment());
                        break;
                    case R.id.menu_bookmarks:
                        Toast.makeText(MainActivity.this, "12", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_tags:
                        Toast.makeText(MainActivity.this, "13", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_search:
                        Toast.makeText(MainActivity.this, "14x", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }

    public void addFragment(BaseFragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL < System.currentTimeMillis()) {
            Toasty.info(this, "Press again to exit", Toast.LENGTH_SHORT).show();
            mBackPressed = System.currentTimeMillis();
            return;
        }
        super.onBackPressed();
    }
}


