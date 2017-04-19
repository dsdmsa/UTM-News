package dsdmsa.utmnews.injection.modules;


import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dsdmsa.utmnews.App;
import dsdmsa.utmnews.repository.PostRepository;

import static dsdmsa.utmnews.utils.Constants.UTM_SHARED_PRESFS;

@Module
public class AppModule {
    private App mApp;

    public AppModule(App mAApp) {
        this.mApp = mAApp;
    }

    @Provides
    App provideContext() {
        return mApp;
    }

    @Provides
    SharedPreferences provideSharedPrefs(App context) {
        return context.getSharedPreferences(UTM_SHARED_PRESFS, Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    PostRepository providesPostRepository(){
        return new PostRepository();
    }
}
