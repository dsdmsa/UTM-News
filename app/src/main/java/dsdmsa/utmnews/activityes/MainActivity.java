package dsdmsa.utmnews.activityes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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
import com.commit451.teleprinter.Teleprinter;

import butterknife.BindView;
import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.fragments.AboutFragment;
import dsdmsa.utmnews.fragments.BaseFragment;
import dsdmsa.utmnews.fragments.BookmarksFragment;
import dsdmsa.utmnews.fragments.CategoryListFragment;
import dsdmsa.utmnews.fragments.LatestNewsFragment;
import dsdmsa.utmnews.fragments.SearchFragment;
import dsdmsa.utmnews.fragments.SettingsFragment;
import dsdmsa.utmnews.fragments.TagListFragment;
import dsdmsa.utmnews.mvp.MainActivityVP;
import dsdmsa.utmnews.presenters.MainActivityPresenter;

public class MainActivity extends BaseActivity implements
        MainActivityVP.View,
        NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener,
        TextView.OnEditorActionListener {

    @InjectPresenter
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
        fab.setOnClickListener(this);
        setupToolbar();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, LatestNewsFragment.newInstance())
                .commit();
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
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .addToBackStack(fragment.getTag())
                .commit();
    }

    @Override
    public void onBackPressed() {
        searchEditText.setVisibility(View.GONE);
        if (searchEditText.getVisibility() == View.VISIBLE) {
            teleprinter.hideKeyboard();
            searchEditText.clearFocus();
            searchEditText.setVisibility(View.GONE);
            return;
        }
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
                getSupportFragmentManager().popBackStack();
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
            case R.id.menu_settings:
                drawerLayout.closeDrawer(GravityCompat.START);
                addFragment(new SettingsFragment());
                break;
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
}


