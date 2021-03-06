package dsdmsa.utmnews;


import android.app.Application;
import android.os.StrictMode;

import com.crashlytics.android.Crashlytics;
import com.squareup.leakcanary.LeakCanary;

import dsdmsa.utmnews.data.di.modules.AppModule;
import dsdmsa.utmnews.data.di.modules.NetworkModule;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

import static dsdmsa.utmnews.domain.utils.Constants.END_POINT;

public class App extends Application {

    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        initModules();
        enabledStrictMode();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return;
            }
            LeakCanary.install(this);
        }
    }

    public void initModules() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(END_POINT))
                .build();
    }
    // TODO: 8/9/17 remove from manifest configuration changes and handle corectly it

    private static void enabledStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }

}
