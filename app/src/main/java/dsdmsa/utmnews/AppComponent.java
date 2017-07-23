package dsdmsa.utmnews;

import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;
import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.data.di.modules.AppModule;
import dsdmsa.utmnews.data.di.modules.NetworkModule;
import dsdmsa.utmnews.data.network.services.UtmServices;
import dsdmsa.utmnews.domain.utils.SimplePostAdapter;
import dsdmsa.utmnews.presentation.activityes.MainActivity;
import dsdmsa.utmnews.presentation.activityes.SplashScreenActivity;
import dsdmsa.utmnews.presentation.fragments.BookmarksFragment;
import dsdmsa.utmnews.presentation.fragments.CategoryNewsFragment;
import dsdmsa.utmnews.presentation.presenters.BookmarksFragmentPresenter;
import dsdmsa.utmnews.presentation.presenters.BookmarksPresenter;
import dsdmsa.utmnews.presentation.presenters.CategoryNewsListPresenter;
import dsdmsa.utmnews.presentation.presenters.HomeFragmentPresenter;
import dsdmsa.utmnews.presentation.presenters.MainActivityPresenter;
import dsdmsa.utmnews.presentation.presenters.NewsListPresenter;
import dsdmsa.utmnews.presentation.presenters.NewsPresenter;
import dsdmsa.utmnews.presentation.presenters.SearchNewsListPresenter;
import dsdmsa.utmnews.presentation.presenters.TagNewsListPresenter;
import dsdmsa.utmnews.presentation.presenters.TagPresenter;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

    SharedPreferences getPrefs();

    App getApp();

    AppDb getAppDb();

    void inject(SplashScreenActivity __);

    void inject(MainActivity __);

    void inject(UtmServices __);

    void inject(NewsPresenter __);

    void inject(TagPresenter __);

    void inject(BookmarksFragmentPresenter __);

    void inject(BookmarksFragment __);

    void inject(CategoryNewsFragment __);

    void inject(NewsListPresenter newsListPresenter);

    void inject(HomeFragmentPresenter homeFragmentPresenter);

    void inject(CategoryNewsListPresenter categoryNewsListPresenter);

    void inject(BookmarksPresenter bookmarksPresenter);

    void inject(TagNewsListPresenter tagNewsListPresenter);

    void inject(MainActivityPresenter mainActivityPresenter);

    void inject(SearchNewsListPresenter searchNewsListPresenter);

    void inject(SimplePostAdapter simplePostAdapter);
}
