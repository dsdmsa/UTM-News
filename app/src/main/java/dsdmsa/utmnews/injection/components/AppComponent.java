package dsdmsa.utmnews.injection.components;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;
import dsdmsa.utmnews.activityes.MainActivity;
import dsdmsa.utmnews.activityes.SplashScreenActivity;
import dsdmsa.utmnews.fragments.BookmarksFragment;
import dsdmsa.utmnews.fragments.CategoryNewsFragment;
import dsdmsa.utmnews.fragments.LatestNewsFragment;
import dsdmsa.utmnews.fragments.SearchFragment;
import dsdmsa.utmnews.fragments.TagNewsFragment;
import dsdmsa.utmnews.injection.modules.AppModule;
import dsdmsa.utmnews.injection.modules.NetworkModule;
import dsdmsa.utmnews.network.services.UtmServices;
import dsdmsa.utmnews.presenters.BookmarksFragmentPresenter;
import dsdmsa.utmnews.presenters.ClassificationPresenter;
import dsdmsa.utmnews.presenters.NewsPresenter;
import dsdmsa.utmnews.views.adapters.NewsAdapter;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

    SharedPreferences getPrefs();

    Context getContext();

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

}
