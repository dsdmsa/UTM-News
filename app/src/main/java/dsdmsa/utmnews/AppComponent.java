package dsdmsa.utmnews;

import javax.inject.Singleton;

import dagger.Component;
import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.data.di.modules.AppModule;
import dsdmsa.utmnews.data.di.modules.NetworkModule;
import dsdmsa.utmnews.presentation.activityes.MainActivity;
import dsdmsa.utmnews.presentation.activityes.SplashScreenActivity;
import dsdmsa.utmnews.presentation.fragments.BookmarksFragment;
import dsdmsa.utmnews.presentation.fragments.CategoryNewsFragment;
import dsdmsa.utmnews.presentation.presenters.BookmarksFragmentPresenter;
import dsdmsa.utmnews.presentation.presenters.BookmarksPresenter;
import dsdmsa.utmnews.presentation.presenters.CategoryNewsListPresenter;
import dsdmsa.utmnews.presentation.presenters.HomeFragmentPresenter;
import dsdmsa.utmnews.presentation.presenters.NewsListPresenter;
import dsdmsa.utmnews.presentation.presenters.SearchNewsListPresenter;
import dsdmsa.utmnews.presentation.presenters.TagNewsListPresenter;
import dsdmsa.utmnews.presentation.presenters.TagPresenter;
import dsdmsa.utmnews.presentation.views.adapters.NewsAdapter;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

    AppDb getAppDb();

    void inject(SplashScreenActivity __);

    void inject(MainActivity __);

    void inject(TagPresenter __);

    void inject(BookmarksFragmentPresenter __);

    void inject(BookmarksFragment __);

    void inject(CategoryNewsFragment __);

    void inject(NewsListPresenter newsListPresenter);

    void inject(HomeFragmentPresenter homeFragmentPresenter);

    void inject(CategoryNewsListPresenter categoryNewsListPresenter);

    void inject(BookmarksPresenter bookmarksPresenter);

    void inject(TagNewsListPresenter tagNewsListPresenter);

    void inject(SearchNewsListPresenter searchNewsListPresenter);

    void inject(NewsAdapter newsAdapter);
}
