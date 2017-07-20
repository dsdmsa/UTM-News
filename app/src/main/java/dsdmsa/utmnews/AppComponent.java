package dsdmsa.utmnews;

import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;

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

    void inject(BookmarkNewsAdapter __);
}
