package dsdmsa.utm_news.injection.modules;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dsdmsa.utm_news.BuildConfig;
import dsdmsa.utm_news.network.controlers.UtmController;
import dsdmsa.utm_news.network.services.UtmServices;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    private String endPoint;

    public NetworkModule(String endPoint) {
        this.endPoint = endPoint;
    }

    @Singleton
    @Provides
    public Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }

    @Singleton
    @Provides
    public UtmServices provideUtmServices(Gson gson, OkHttpClient okHttpClient) {
        OkHttpClient.Builder httpClientBuilder = okHttpClient.newBuilder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(loggingInterceptor);
        }

        return new Retrofit.Builder()
                .baseUrl(endPoint)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .callFactory(httpClientBuilder.build())
                .build().create(UtmServices.class);
    }

    @Singleton
    @Provides
    public UtmController providesUtmController(UtmServices services){
        return new UtmController(services);
    }

}
