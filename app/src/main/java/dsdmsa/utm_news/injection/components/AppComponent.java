package dsdmsa.utm_news.injection.components;

import javax.inject.Singleton;

import dagger.Component;
import dsdmsa.utm_news.activityes.SplashScreenActivity;
import dsdmsa.utm_news.injection.modules.AppModule;
import dsdmsa.utm_news.views.navigator.HomeWidget;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(SplashScreenActivity __);

    void inject(HomeWidget __);
}
