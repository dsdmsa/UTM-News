package dsdmsa.utmnews.activityes;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.mvp.MainActivityVP;
import dsdmsa.utmnews.presenters.MainActivityPresenter;
import dsdmsa.utmnews.views.adapters.MainViewPagerAdapter;

import static dsdmsa.utmnews.App.appComponent;
import static dsdmsa.utmnews.utils.Constants.TIME_TO_WHAIT_TO_EXIT;

public class MainActivity extends BaseActivity implements MainActivityVP.View {

    @InjectPresenter
    MainActivityPresenter presenter;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.search_ic)
    ImageView searchImage;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.pager)
    ViewPager viewPager;

    @BindView(R.id.activity_main)
    DrawerLayout drawerLayout;

    private long backPressed = 0;
    private ActionBarDrawerToggle mDrawerToggle;
    private MainViewPagerAdapter mMainViewPagerAdapter;


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appComponent.inject(this);
        setSupportActionBar(toolbar);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.hello_world, R
                .string.hello_world2);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mMainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(mMainViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (backPressed + TIME_TO_WHAIT_TO_EXIT > System.currentTimeMillis()) {
                super.onBackPressed();
            } else {
                Toast.makeText(getBaseContext(), R.string.press_to_exit, Toast.LENGTH_SHORT).show();
                backPressed = System.currentTimeMillis();
            }
        }
    }
}


