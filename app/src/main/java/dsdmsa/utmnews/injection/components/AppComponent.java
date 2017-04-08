package dsdmsa.utmnews.injection.components;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;
import dsdmsa.utmnews.activityes.MainActivity;
import dsdmsa.utmnews.activityes.SplashScreenActivity;
import dsdmsa.utmnews.fragments.LatestNewsFragment;
import dsdmsa.utmnews.injection.modules.AppModule;
import dsdmsa.utmnews.injection.modules.NetworkModule;
import dsdmsa.utmnews.network.controlers.UtmServices;
import dsdmsa.utmnews.presenters.LatestNewsPresenter;
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

    void inject(LatestNewsPresenter __);

    void inject(NewsAdapter __);

}
