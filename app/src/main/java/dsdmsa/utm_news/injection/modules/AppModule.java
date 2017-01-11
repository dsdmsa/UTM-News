package dsdmsa.utm_news.injection.modules;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dsdmsa.utm_news.This;
import dsdmsa.utm_news.utils.Navigator;

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
}
