package dsdmsa.utm_news;


import android.app.Application;

import dsdmsa.utm_news.injection.components.AppComponent;
import dsdmsa.utm_news.injection.components.DaggerAppComponent;
import dsdmsa.utm_news.injection.modules.AppModule;

public class This extends Application {

    public static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();

    }
}
