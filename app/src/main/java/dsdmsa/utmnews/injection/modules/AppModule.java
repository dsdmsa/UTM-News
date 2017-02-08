package dsdmsa.utmnews.injection.modules;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dsdmsa.utmnews.This;
import dsdmsa.utmnews.utils.Navigator;
import dsdmsa.utmnews.utils.SharedPrefs;

@Module
public class AppModule {
    private This mThis;

    public AppModule(This mAThis) {
        this.mThis = mAThis;
    }

    @Provides
    Context provideContext(){
        return mThis;
    }

    @Provides
    @Singleton
    Navigator privideNavigator(Context context){
        return new Navigator(context);
    }

    @Provides
    SharedPrefs provideSharedPrefs(Context context){
        return new SharedPrefs(context);
    }
}
