package dsdmsa.utm_news.injection.components;

import javax.inject.Singleton;

import dagger.Component;
import dsdmsa.utm_news.activityes.SplashScreenActivity;
import dsdmsa.utm_news.activityes.main.MainActivity;
import dsdmsa.utm_news.injection.modules.AppModule;
import dsdmsa.utm_news.views.navigator.AboutWidget;
import dsdmsa.utm_news.views.navigator.CategoryWidget;
import dsdmsa.utm_news.views.navigator.CollectionsBookmarkWidget;
import dsdmsa.utm_news.views.navigator.FeedbackWidget;
import dsdmsa.utm_news.views.navigator.HomeWidget;
import dsdmsa.utm_news.views.navigator.SearchWidget;
import dsdmsa.utm_news.views.navigator.SettingsWidget;

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
}
