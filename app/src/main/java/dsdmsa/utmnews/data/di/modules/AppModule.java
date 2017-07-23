package dsdmsa.utmnews.data.di.modules;


import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dsdmsa.utmnews.App;
import dsdmsa.utmnews.data.db.AppDb;
import dsdmsa.utmnews.domain.utils.Constants;

import static dsdmsa.utmnews.domain.utils.Constants.UTM_SHARED_PRESFS;

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
    AppDb provideAppDb(App context){
        return Room.databaseBuilder(context,AppDb.class, Constants.DATABASE).build();
    }
}
