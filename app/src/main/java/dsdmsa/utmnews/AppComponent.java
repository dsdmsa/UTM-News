package dsdmsa.utmnews;

import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;
import dsdmsa.utmnews.data.di.modules.AppModule;
import dsdmsa.utmnews.data.di.modules.NetworkModule;
import dsdmsa.utmnews.data.network.services.UtmServices;
import dsdmsa.utmnews.presentation.activityes.MainActivity;
import dsdmsa.utmnews.presentation.activityes.SplashScreenActivity;
import dsdmsa.utmnews.presentation.fragments.BookmarksFragment;
import dsdmsa.utmnews.presentation.fragments.CategoryNewsFragment;
import dsdmsa.utmnews.presentation.fragments.LatestNewsFragment;
import dsdmsa.utmnews.presentation.fragments.SearchFragment;
import dsdmsa.utmnews.presentation.fragments.TagNewsFragment;
import dsdmsa.utmnews.presentation.presenters.BookmarksFragmentPresenter;
import dsdmsa.utmnews.presentation.presenters.CategoryNewsListPresenter;
import dsdmsa.utmnews.presentation.presenters.ClassificationPresenter;
import dsdmsa.utmnews.presentation.presenters.HomeFragmentPresenter;
import dsdmsa.utmnews.presentation.presenters.NewsListPresenter;
import dsdmsa.utmnews.presentation.presenters.NewsPresenter;
import dsdmsa.utmnews.presentation.views.adapters.NewsAdapter;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

    SharedPreferences getPrefs();

    App getApp();

    void inject(SplashScreenActivity __);

    void inject(MainActivity __);

    void inject(LatestNewsFragment __);

    void inject(UtmServices __);

    void inject(NewsPresenter __);

    void inject(NewsAdapter __);

    void inject(ClassificationPresenter __);

    void inject(BookmarksFragmentPresenter __);

    void inject(BookmarksFragment __);

    void inject(CategoryNewsFragment __);

    void inject(SearchFragment __);

    void inject(TagNewsFragment __);

    void inject(NewsListPresenter newsListPresenter);

    void inject(HomeFragmentPresenter homeFragmentPresenter);

    void inject(CategoryNewsListPresenter categoryNewsListPresenter);
}
