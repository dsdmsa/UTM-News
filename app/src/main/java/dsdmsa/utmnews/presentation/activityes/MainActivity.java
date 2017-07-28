package dsdmsa.utmnews.presentation.activityes;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.commit451.teleprinter.Teleprinter;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.domain.utils.Constants;
import dsdmsa.utmnews.domain.utils.FragmentNavigation;
import dsdmsa.utmnews.presentation.fragments.AboutFragment;
import dsdmsa.utmnews.presentation.fragments.BaseFragment;
import dsdmsa.utmnews.presentation.fragments.BookmarksFragment;
import dsdmsa.utmnews.presentation.fragments.HomeFragment;
import dsdmsa.utmnews.presentation.fragments.SearchNewsListFragment;
import dsdmsa.utmnews.presentation.fragments.TagListFragment;
import dsdmsa.utmnews.presentation.mvp.MainActivityVP;
import dsdmsa.utmnews.presentation.presenters.MainActivityPresenter;
import dsdmsa.utmnews.presentation.views.BottomNavigationViewHelper;
import es.dmoral.toasty.Toasty;

import static dsdmsa.utmnews.domain.utils.Constants.TIME_INTERVAL;

public class MainActivity extends BaseActivity implements
        MainActivityVP.View {

    @InjectPresenter
    MainActivityPresenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @BindView(R.id.et_search)
    EditText etSearch;

    @Inject
    protected FragmentNavigation fragmentNavigation;

    @BindView(R.id.btn_search)
    ImageView btnSearch;

    @BindView(R.id.tab_title)
    TextView tabTitle;

    private long mBackPressed;
    private Teleprinter teleprinter;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        App.getAppComponent().inject(this);
        fragmentNavigation.init(getSupportFragmentManager(), R.id.fragment_container);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragmentNavigation.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        App.getAppComponent().inject(this);
        fragmentNavigation.init(getSupportFragmentManager(), R.id.fragment_container);
        teleprinter = new Teleprinter(this);
        fragmentNavigation.showFragment(R.id.menu_home, new HomeFragment());

        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_home:
                    hideSearch();
                    fragmentNavigation.showFragment(R.id.menu_home, new HomeFragment());
                    break;
                case R.id.menu_tags:
                    hideSearch();
                    fragmentNavigation.showFragment(R.id.menu_tags, new TagListFragment());
                    break;
                case R.id.menu_bookmarks:
                    hideSearch();
                    fragmentNavigation.showFragment(R.id.menu_bookmarks, new BookmarksFragment());
                    break;
                case R.id.menu_search:
                    search();
                    break;
            }
            setToolbarTitle("");
            return true;
        });

        RxTextView.textChanges(etSearch)
                .debounce(Constants.DEBOUNCH_TIMEOUT, TimeUnit.MILLISECONDS)
                .filter(charSequence -> !charSequence.toString().isEmpty())
                .map(CharSequence::toString)
                .subscribe(value -> EventBus.getDefault().post(value));

        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                EventBus.getDefault().post(v.getText().toString());
                teleprinter.hideKeyboard();
                return true;
            }
            return false;
        });
    }

    @Override
    public void onBackPressed() {
        setToolbarTitle("");
        if (fragmentNavigation.getSize() == 1) {
            if (mBackPressed + TIME_INTERVAL < System.currentTimeMillis()) {
                Toasty.custom(this, getString(R.string.tab_again_exit_info), null,
                        ContextCompat.getColor(this, R.color.primary_light),
                        ContextCompat.getColor(this, R.color.primary_dark),
                        Toast.LENGTH_SHORT, false, true).show();
                mBackPressed = System.currentTimeMillis();
                return;
            }
            super.onBackPressed();
        } else {
            navigation.setSelectedItemId(fragmentNavigation.bakPressed());
        }
    }

    @OnClick(R.id.btn_about)
    public void onViewClicked() {
        fragmentNavigation.showFragment(Constants.ABOUT_FRAGMENT_ID, new AboutFragment());
    }

    public void openFragment(BaseFragment fragment, int id) {
        fragmentNavigation.showFragment(id, fragment);
    }

    private void search() {
//        if (etSearch.getVisibility() == View.VISIBLE) {
//            etSearch.setVisibility(View.GONE);
//            btnSearch.setVisibility(View.GONE);
//            etSearch.clearFocus();
//            teleprinter.hideKeyboard();
//            navigation.setSelectedItemId(fragmentNavigation.bakPressed());
//        } else {
            etSearch.setVisibility(View.VISIBLE);
            btnSearch.setVisibility(View.VISIBLE);
            etSearch.requestFocus();
            teleprinter.showKeyboard(etSearch);
            openFragment(new SearchNewsListFragment(), Constants.SEARCH_FRAGMENT_ID);
//        }
    }

    public void hideSearch() {
        if (etSearch.getVisibility() == View.VISIBLE) {
            etSearch.setVisibility(View.GONE);
            btnSearch.setVisibility(View.GONE);
            etSearch.clearFocus();
            navigation.setSelectedItemId(fragmentNavigation.bakPressed());
        }
    }

    @OnClick(R.id.et_search)
    public void onSearchClicked() {
        teleprinter.hideKeyboard();
    }

    @OnClick(R.id.et_search)
    public void onSearchViewClicked() {
        teleprinter.showKeyboard(etSearch);
    }

    public void setToolbarTitle(String title) {
        tabTitle.setText(title);
    }
}


