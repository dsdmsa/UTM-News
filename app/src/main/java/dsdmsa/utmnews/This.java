package dsdmsa.utmnews;


import android.app.Application;

import dsdmsa.utmnews.injection.components.AppComponent;
import dsdmsa.utmnews.injection.components.DaggerAppComponent;
import dsdmsa.utmnews.injection.components.DaggerNetworkComponent;
import dsdmsa.utmnews.injection.components.NetworkComponent;
import dsdmsa.utmnews.injection.modules.AppModule;
import dsdmsa.utmnews.injection.modules.NetworkModule;

public class This extends Application {

    public static AppComponent appComponent;
    public static NetworkComponent networkComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();//http://utm.md/wp-json/wp/v2/posts
        networkComponent = DaggerNetworkComponent.builder().networkModule(new NetworkModule("http://utm.md/")).build();

    }
}
