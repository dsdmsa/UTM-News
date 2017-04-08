package dsdmsa.utmnews.activityes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.fragments.AboutFragment;
import dsdmsa.utmnews.fragments.BookmarksFragment;
import dsdmsa.utmnews.fragments.SearchFragment;
import dsdmsa.utmnews.fragments.SettingsFragment;
import dsdmsa.utmnews.mvp.MainActivityVP;
import dsdmsa.utmnews.presenters.MainActivityPresenter;
import dsdmsa.utmnews.views.adapters.MainViewPagerAdapter;

public class MainActivity extends BaseActivity
        implements MainActivityVP.View,
        NavigationView.OnNavigationItemSelectedListener {

    @InjectPresenter
    MainActivityPresenter presenter;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.pager)
    ViewPager viewPager;

    @BindView(R.id.activity_main)
    DrawerLayout drawerLayout;

    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    private ActionBarDrawerToggle mDrawerToggle;
    private MainViewPagerAdapter mMainViewPagerAdapter;


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent().inject(this);
        navigationView.setNavigationItemSelectedListener(this);
        setupToolbar();
        mMainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(mMainViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.hello_world, R.string.hello_world2);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        toolbarTitle.setText(R.string.start_title);
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

    @Subscribe
    public void addFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .addToBackStack(fragment.getTag())
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                getSupportFragmentManager().popBackStack();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_search:
                drawerLayout.closeDrawer(GravityCompat.START);
                addFragment(new SearchFragment());
                break;
            case R.id.menu_bookmarks:
                drawerLayout.closeDrawer(GravityCompat.START);
                addFragment(new BookmarksFragment());
                break;
            case R.id.menu_info:
                drawerLayout.closeDrawer(GravityCompat.START);
                addFragment(new AboutFragment());
                break;
            case R.id.menu_settings:
                drawerLayout.closeDrawer(GravityCompat.START);
                addFragment(new SettingsFragment());
                break;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}


