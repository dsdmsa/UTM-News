package dsdmsa.utmnews;


import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import dsdmsa.utmnews.injection.components.AppComponent;
import dsdmsa.utmnews.injection.components.DaggerAppComponent;
import dsdmsa.utmnews.injection.modules.AppModule;
import dsdmsa.utmnews.injection.modules.NetworkModule;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

import static dsdmsa.utmnews.utils.Constants.END_POINT;
import static dsdmsa.utmnews.utils.Constants.REALM_UTM_DB;
import static dsdmsa.utmnews.utils.Constants.SCHEMA_VERSION;

public class App extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(END_POINT))
                .build();

        RealmConfiguration realmConfiguration =
                new RealmConfiguration.Builder(this)
                        .name(REALM_UTM_DB)
                        .schemaVersion(SCHEMA_VERSION)
                        .build();

        Realm.setDefaultConfiguration(realmConfiguration);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return;
            }
            LeakCanary.install(this);
        }
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
