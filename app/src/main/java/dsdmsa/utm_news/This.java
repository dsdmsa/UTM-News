package dsdmsa.utm_news;


import android.app.Application;

import dsdmsa.utm_news.injection.components.AppComponent;
import dsdmsa.utm_news.injection.components.DaggerAppComponent;
import dsdmsa.utm_news.injection.components.DaggerNetworkComponent;
import dsdmsa.utm_news.injection.components.NetworkComponent;
import dsdmsa.utm_news.injection.modules.AppModule;
import dsdmsa.utm_news.injection.modules.NetworkModule;

public class This extends Application {

    public static AppComponent appComponent;
    public static NetworkComponent networkComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        networkComponent = DaggerNetworkComponent.builder().networkModule(new NetworkModule("https://http://utm.md/")).build();

    }
}
