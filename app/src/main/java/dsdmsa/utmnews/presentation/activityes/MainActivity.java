package dsdmsa.utmnews.presentation.activityes;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.commit451.teleprinter.Teleprinter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.domain.models.enums.NetState;
import dsdmsa.utmnews.presentation.fragments.BaseFragment;
import dsdmsa.utmnews.presentation.fragments.SearchFragment;
import dsdmsa.utmnews.presentation.mvp.MainActivityVP;
import dsdmsa.utmnews.presentation.presenters.MainActivityPresenter;
import es.dmoral.toasty.Toasty;

import static dsdmsa.utmnews.domain.utils.Constants.TIME_INTERVAL;

public class MainActivity extends BaseActivity implements
        MainActivityVP.View,
        NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener,
        TextView.OnEditorActionListener,
        DrawerLayout.DrawerListener {

    @InjectPresenter(type = PresenterType.GLOBAL)
    MainActivityPresenter presenter;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.et_search)
    EditText searchEditText;
    @BindView(R.id.activity_main)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Teleprinter teleprinter;
    private long mBackPressed;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        teleprinter = new Teleprinter(this);
        navigationView.setNavigationItemSelectedListener(this);
        searchEditText.setOnEditorActionListener(this);
        drawerLayout.addDrawerListener(this);
        fab.setOnClickListener(this);
        setupToolbar();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.hello_world, R.string.hello_world2);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    @Override
    public void addFragment(BaseFragment fragment) {
        fragment.atachPresenter(presenter);
        setTootlbarTitile(fragment.getTitle());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void setTootlbarTitile(String title) {
        toolbarTitle.setText(title);
    }

    @Override
    public void onBackPressed() {
        searchEditText.setVisibility(View.GONE);
        teleprinter.hideKeyboard();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        if (mBackPressed + TIME_INTERVAL < System.currentTimeMillis()) {
            Toasty.info(this, getString(R.string.tab_again_exit_info), Toast.LENGTH_SHORT).show();
            mBackPressed = System.currentTimeMillis();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        presenter.onItemSelected(item);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                if (searchEditText.getVisibility() == View.GONE) {
                    searchEditText.setVisibility(View.VISIBLE);
                    searchEditText.requestFocus();
                    teleprinter.showKeyboard(searchEditText);
                } else {
                    hideKeyboard();
                    addFragment(SearchFragment.newInstance(searchEditText.getText().toString()));
                }
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            hideKeyboard();
            addFragment(SearchFragment.newInstance(searchEditText.getText().toString()));
            return true;
        }
        return false;
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        navigationView.setAlpha(slideOffset);

    }

    @Override
    public void onDrawerOpened(View drawerView) {
        hideKeyboard();
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        hideKeyboard();
    }

    private void hideKeyboard() {
        teleprinter.hideKeyboard();
        searchEditText.setVisibility(View.GONE);
    }

    @Override
    public void onDrawerStateChanged(int newState) {
        if (newState == DrawerLayout.STATE_DRAGGING){
            hideKeyboard();
        }
    }

    @Subscribe
    public void showSnake(final NetState state) {
        final Snackbar snackbar = Snackbar.make(coordinatorLayout, state.getState(), Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(state.getActionText(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state.equals(NetState.OFFLINE)) {
                    presenter.openNetSettings();
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


