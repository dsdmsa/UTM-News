package dsdmsa.utm_news.injection.components;

import javax.inject.Singleton;

import dagger.Component;
import dsdmsa.utm_news.activityes.SplashScreenActivity;
import dsdmsa.utm_news.injection.modules.AppModule;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(SplashScreenActivity __);
}
