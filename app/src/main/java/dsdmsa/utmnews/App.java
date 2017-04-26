package dsdmsa.utmnews;


import android.app.Application;

import dsdmsa.utmnews.injection.components.AppComponent;
import dsdmsa.utmnews.injection.components.DaggerAppComponent;
import dsdmsa.utmnews.injection.modules.AppModule;
import dsdmsa.utmnews.injection.modules.NetworkModule;
import dsdmsa.utmnews.utils.Utils;
import io.realm.Realm;

import static dsdmsa.utmnews.utils.Constants.END_POINT;
import static dsdmsa.utmnews.utils.Constants.IN_INTERNET_AVAIBLE;

public class App extends Application {

    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(END_POINT))
                .build();

        if (Utils.isOnline(this)) {
            App.getAppComponent().getPrefs().edit().putBoolean(IN_INTERNET_AVAIBLE, true).apply();
        } else {
            App.getAppComponent().getPrefs().edit().putBoolean(IN_INTERNET_AVAIBLE, false).apply();
        }
        
        Realm.init(this);

//        if (BuildConfig.DEBUG) {
//            Timber.plant(new Timber.DebugTree());
//            if (LeakCanary.isInAnalyzerProcess(this)) {
//                return;
//            }
//            LeakCanary.install(this);
//        }
    }
}
