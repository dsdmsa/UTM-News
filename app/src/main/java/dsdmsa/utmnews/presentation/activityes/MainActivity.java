package dsdmsa.utmnews.presentation.activityes;

import android.os.Bundle;
import android.os.PersistableBundle;
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

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import dsdmsa.utmnews.App;
import dsdmsa.utmnews.R;
import dsdmsa.utmnews.domain.utils.Constants;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        teleprinter = new Teleprinter(this);
        App.getAppComponent().inject(this);
        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_home:
                    hideSearch();
                    addFragment(new HomeFragment());
                    break;
                case R.id.menu_tags:
                    hideSearch();
                    addFragment(new TagListFragment());
                    break;
                case R.id.menu_bookmarks:
                    hideSearch();
                    addFragment(new BookmarksFragment());
                    break;
                case R.id.menu_search:
                    search();
                    break;
            }
            setToolbarTitle("");
            return true;
        });

        navigation.setSelectedItemId(R.id.menu_home);

        BottomNavigationViewHelper.disableShiftMode(navigation);

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

        if (navigation.getSelectedItemId() != R.id.menu_home){
            navigation.setSelectedItemId(R.id.menu_home);
            addFragment(new HomeFragment());
            return;
        }

        if (mBackPressed + TIME_INTERVAL < System.currentTimeMillis()) {
            Toasty.custom(this, getString(R.string.tab_again_exit_info), null,
                    ContextCompat.getColor(this, R.color.primary_light),
                    ContextCompat.getColor(this, R.color.primary_dark),
                    Toast.LENGTH_SHORT, false, true).show();
            mBackPressed = System.currentTimeMillis();
            return;
        }
        super.onBackPressed();
    }

    @OnClick(R.id.btn_about)
    public void onViewClicked() {
        addFragment(new AboutFragment());
    }

    private void search() {
        etSearch.setVisibility(View.VISIBLE);
        btnSearch.setVisibility(View.VISIBLE);
        etSearch.requestFocus();
        teleprinter.showKeyboard(etSearch);
        addFragment(new SearchNewsListFragment());
    }

    public void hideSearch() {
        if (etSearch.getVisibility() == View.VISIBLE) {
            etSearch.setVisibility(View.GONE);
            btnSearch.setVisibility(View.GONE);
            etSearch.clearFocus();
        }
    }

    @OnClick(R.id.btn_search)
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

    private HashMap<String, BaseFragment> fragmentHashMap = new HashMap<>();

    private void addFragment(BaseFragment fragment) {
        Timber.i(fragment.getName());
        for (BaseFragment baseFragment : fragmentHashMap.values()) {
            getSupportFragmentManager().beginTransaction().hide(baseFragment).commit();
        }
        if (fragmentHashMap.containsKey(fragment.getName())) {
            getSupportFragmentManager().beginTransaction().show(fragmentHashMap.get(fragment.getName())).commit();
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
            fragmentHashMap.put(fragment.getName(), fragment);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        getSupportFragmentManager().beginTransaction().remove(fragmentHashMap.get(HomeFragment.class.getSimpleName())).commit();
        fragmentHashMap.remove(HomeFragment.class.getSimpleName());
        addFragment(new HomeFragment());
        Timber.i(HomeFragment.class.getSimpleName());

    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        getSupportFragmentManager().beginTransaction().remove(fragmentHashMap.get(HomeFragment.class.getSimpleName())).commit();
//        fragmentHashMap.remove(HomeFragment.class.getSimpleName());
//        addFragment(new HomeFragment());
//        Timber.i(HomeFragment.class.getSimpleName());
//    }
}


