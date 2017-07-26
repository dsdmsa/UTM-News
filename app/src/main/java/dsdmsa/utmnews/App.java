package dsdmsa.utmnews;


import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import dsdmsa.utmnews.data.di.modules.AppModule;
import dsdmsa.utmnews.data.di.modules.NetworkModule;
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

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(END_POINT))
                .build();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return;
            }
            LeakCanary.install(this);
        }
    }
}
