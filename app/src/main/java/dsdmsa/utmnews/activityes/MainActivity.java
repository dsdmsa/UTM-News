package dsdmsa.utmnews.activityes;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
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

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.commit451.teleprinter.Teleprinter;

import org.chromium.customtabsclient.CustomTabsActivityHelper;

import butterknife.BindView;
import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.fragments.AboutFragment;
import dsdmsa.utmnews.fragments.BaseFragment;
import dsdmsa.utmnews.fragments.BookmarksFragment;
import dsdmsa.utmnews.fragments.CategoryListFragment;
import dsdmsa.utmnews.fragments.LatestNewsFragment;
import dsdmsa.utmnews.fragments.SearchFragment;
import dsdmsa.utmnews.fragments.TagListFragment;
import dsdmsa.utmnews.models.Post;
import dsdmsa.utmnews.mvp.MainActivityVP;
import dsdmsa.utmnews.presenters.MainActivityPresenter;
import me.zhanghai.android.customtabshelper.CustomTabsHelperFragment;

public class MainActivity extends BaseActivity implements
        MainActivityVP.View,
        NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener,
        TextView.OnEditorActionListener,
        DrawerLayout.DrawerListener,
        CustomTabsActivityHelper.CustomTabsFallback {

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


    private ActionBarDrawerToggle mDrawerToggle;
    private Teleprinter teleprinter;
    private CustomTabsHelperFragment customTabsHelperFragment;
    private CustomTabsIntent customTabsIntent;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent().inject(this);
        teleprinter = new Teleprinter(this);
        navigationView.setNavigationItemSelectedListener(this);
        searchEditText.setOnEditorActionListener(this);
        drawerLayout.addDrawerListener(this);
        fab.setOnClickListener(this);
        setupToolbar();

        customTabsHelperFragment = CustomTabsHelperFragment.attachTo(this);

        customTabsIntent = new CustomTabsIntent.Builder()
                .enableUrlBarHiding()
                .setToolbarColor(ContextCompat.getColor(this, R.color.primary_dark))
                .setShowTitle(true)
                .build();

        addFragment(LatestNewsFragment.newInstance());

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

    @Override
    public void addFragment(BaseFragment fragment) {
        fragment.atachPresenter(presenter);
        String fragmentTitle = fragment.getTitle();
        toolbarTitle.setText(fragmentTitle);
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(fragmentTitle, 0);
        if (!fragmentPopped && manager.findFragmentByTag(fragmentTitle) == null) {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(R.id.fragment_container, fragment, fragmentTitle);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(fragmentTitle);
            ft.commit();
        }
    }

    @Override
    public void openDetails(Post post) {
        CustomTabsHelperFragment.open(
                this,
                customTabsIntent,
                Uri.parse(post.getLink()),
                this
        );
    }

    @Override
    public void onBackPressed() {
        searchEditText.setVisibility(View.GONE);
        teleprinter.hideKeyboard();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                addFragment(new LatestNewsFragment());
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.menu_categories:
                drawerLayout.closeDrawer(GravityCompat.START);
                addFragment(new CategoryListFragment());
                break;
            case R.id.menu_tags:
                drawerLayout.closeDrawer(GravityCompat.START);
                addFragment(new TagListFragment());
                break;
            case R.id.menu_bookmarks:
                drawerLayout.closeDrawer(GravityCompat.START);
                addFragment(new BookmarksFragment());
                break;
            case R.id.menu_info:
                drawerLayout.closeDrawer(GravityCompat.START);
                addFragment(new AboutFragment());
                break;
//            case R.id.menu_settings:
//                drawerLayout.closeDrawer(GravityCompat.START);
//                addFragment(new SettingsFragment());
//                break;
        }
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
                    teleprinter.hideKeyboard();
                    searchEditText.setVisibility(View.GONE);
                    addFragment(SearchFragment.newInstance(searchEditText.getText().toString()));
                }
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            teleprinter.hideKeyboard();
            searchEditText.setVisibility(View.GONE);
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
        teleprinter.hideKeyboard();
        searchEditText.setVisibility(View.GONE);
    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    public void openUri(Activity activity, Uri uri) {
//        getViewState().showInfoMessage("error");
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
//            getViewState().showInfoMessage("activity not found");
        }
    }
}


