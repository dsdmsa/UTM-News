package dsdmsa.utmnews;


import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import dsdmsa.utmnews.data.di.components.DaggerAppComponent;
import dsdmsa.utmnews.data.di.modules.AppModule;
import dsdmsa.utmnews.data.di.modules.NetworkModule;
import dsdmsa.utmnews.domain.utils.Utils;
import io.realm.Realm;

import static dsdmsa.utmnews.domain.utils.Constants.END_POINT;
import static dsdmsa.utmnews.domain.utils.Constants.IN_INTERNET_AVAIBLE;

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

        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return;
            }
            LeakCanary.install(this);
        }
    }
}
