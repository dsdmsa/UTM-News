package dsdmsa.utmnews.injection.modules;


import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import dsdmsa.utmnews.App;

import static dsdmsa.utmnews.utils.Constants.UTM_SHARED_PRESFS;

@Module
public class AppModule {
    private App mApp;

    public AppModule(App mAApp) {
        this.mApp = mAApp;
    }

    @Provides
    Context provideContext() {
        return mApp;
    }

    @Provides
    SharedPreferences provideSharedPrefs(Context context) {
        return context.getSharedPreferences(UTM_SHARED_PRESFS, Context.MODE_PRIVATE);
    }
}
