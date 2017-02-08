package dsdmsa.utmnews.injection.components;

import javax.inject.Singleton;

import dagger.Component;
import dsdmsa.utmnews.activityes.SplashScreenActivity;
import dsdmsa.utmnews.activityes.main.MainActivity;
import dsdmsa.utmnews.injection.modules.AppModule;
import dsdmsa.utmnews.views.adapters.NewsAdapter;
import dsdmsa.utmnews.views.navigator.AboutWidget;
import dsdmsa.utmnews.views.navigator.BaseWidget;
import dsdmsa.utmnews.views.navigator.CategoryWidget;
import dsdmsa.utmnews.views.navigator.CollectionsBookmarkWidget;
import dsdmsa.utmnews.views.navigator.FeedbackWidget;
import dsdmsa.utmnews.views.navigator.HomeWidget;
import dsdmsa.utmnews.views.navigator.SearchWidget;
import dsdmsa.utmnews.views.navigator.SettingsWidget;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(SplashScreenActivity __);

    void inject(MainActivity __);

    void inject(HomeWidget __);

    void inject(AboutWidget __);

    void inject(SearchWidget __);

    void inject(CollectionsBookmarkWidget __);

    void inject(CategoryWidget __);

    void inject(SettingsWidget __);

    void inject(FeedbackWidget __);

    void inject(NewsAdapter __);

    void inject(BaseWidget __);
}
