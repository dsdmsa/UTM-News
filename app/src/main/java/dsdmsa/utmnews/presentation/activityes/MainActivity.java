package dsdmsa.utmnews.presentation.activityes;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.domain.models.enums.NetState;
import dsdmsa.utmnews.presentation.fragments.BaseFragment;
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
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;

    private long mBackPressed;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:
                        break;
                    case R.id.menu_bookmarks:
                        break;
                    case R.id.menu_tags:
                        break;
                    case R.id.menu_search:
                        break;
                }
                return false;
            }
        });
    }

    public void addFragment(BaseFragment fragment) {
        fragment.atachPresenter(presenter);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL < System.currentTimeMillis()) {
            Toasty.info(this, getString(R.string.tab_again_exit_info), Toast.LENGTH_SHORT).show();
            mBackPressed = System.currentTimeMillis();
            return;
        }
        super.onBackPressed();
    }

    @Subscribe
    public void showSnake(final NetState state) {
        final Snackbar snackbar = Snackbar.make(coordinator, state.getState(), Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(state.getActionText(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state.equals(NetState.OFFLINE)) {
//                    presenter.openNetSettings();
                } else {
                    presenter.retry();
                    snackbar.dismiss();
                }
            }
        });

        View sbView = snackbar.getView();
        sbView.setBackgroundColor(state.getBkgColor());
        snackbar.setActionTextColor(Color.BLACK);
        snackbar.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}


