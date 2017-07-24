package dsdmsa.utmnews.presentation.activityes;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.data.brodcast.ConnectionChangeReceiver;
import dsdmsa.utmnews.data.network.api.UtmApi;
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
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import timber.log.Timber;

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
    UtmApi utmApi;

    @Inject
    protected FragmentNavigation fragmentNavigation;

    @BindView(R.id.btn_search)
    ImageView btnSearch;

    private long mBackPressed;

    private ConnectionChangeReceiver receiver;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        fragmentNavigation.init(getSupportFragmentManager(), R.id.fragment_container);
        receiver = new ConnectionChangeReceiver();
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        super.onResume();
    }

    @Override
    protected void onPause() {
        fragmentNavigation.onPause();
        unregisterReceiver(receiver);
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        App.getAppComponent().inject(this);
        fragmentNavigation.init(getSupportFragmentManager(), R.id.fragment_container);

        fragmentNavigation.showFragment(R.id.menu_home, new HomeFragment());

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
                return true;
            }
        });

        RxTextView.textChanges(etSearch)
                .debounce(Constants.DEBOUNCH_TIMEOUT, TimeUnit.MILLISECONDS)
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(CharSequence charSequence) throws Exception {
                        return !charSequence.toString().isEmpty();
                    }
                })
                .map(new Function<CharSequence, String>() {
                    @Override
                    public String apply(CharSequence charSequence) throws Exception {
                        return charSequence.toString();
                    }
                })
                .subscribeWith(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        Timber.d("send " + value);
                        EventBus.getDefault().post(value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

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

    @OnClick(R.id.btn_about)
    public void onViewClicked() {
        fragmentNavigation.showFragment(Constants.ABOUT_FRAGMENT_ID, new AboutFragment());
    }

    public void openFragment(BaseFragment fragment, int id) {
        fragmentNavigation.showFragment(id, fragment);
    }

    private void search() {
        if (etSearch.getVisibility() == View.VISIBLE) {
            etSearch.setVisibility(View.GONE);
            btnSearch.setVisibility(View.GONE);
            etSearch.clearFocus();
            navigation.setSelectedItemId(fragmentNavigation.bakPressed());
        } else {
            etSearch.setVisibility(View.VISIBLE);
            btnSearch.setVisibility(View.VISIBLE);
            etSearch.requestFocus();
            openFragment(new SearchNewsListFragment(), Constants.SEARCH_FRAGMENT_ID);
        }
    }

    private void hideSearch() {
        if (etSearch.getVisibility() == View.VISIBLE) {
            etSearch.setVisibility(View.GONE);
            btnSearch.setVisibility(View.GONE);
            etSearch.clearFocus();
            navigation.setSelectedItemId(fragmentNavigation.bakPressed());
        }
    }

}


