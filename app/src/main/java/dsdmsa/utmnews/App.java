package dsdmsa.utmnews;


import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import dsdmsa.utmnews.injection.components.AppComponent;
import timber.log.Timber;

public class App extends Application {

    public static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
//        appComponent = DaggerAppComponent.builder()
//                .appModule(new AppModule(this))
//                .networkModule(new NetworkModule(END_POINT))
//                .build();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return;
            }
            LeakCanary.install(this);
        }
    }


}
