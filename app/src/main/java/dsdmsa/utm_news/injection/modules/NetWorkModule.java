package dsdmsa.utm_news.injection.modules;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetWorkModule {

    private String endPoint;

    @Provides
    static Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Country.class, CountryTypeAdapter.INSTANCE)
                .registerTypeAdapter(new TypeToken<RealmList<RealmString>>(){}.getType(), RealmStringListTypeAdapter.INSTANCE)
                .registerTypeAdapter(new TypeToken<RealmList<RealmStringMapEntry>>(){}.getType(), RealmStringMapEntryListTypeAdapter.INSTANCE)
                .create();
    }

    @Provides
    @PerApplication
    static OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }

    @Provides
    @PerApplication
    static CountryApi provideCountryApi(Gson gson, OkHttpClient okHttpClient) {
        OkHttpClient.Builder httpClientBuilder = okHttpClient.newBuilder();

        if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(loggingInterceptor);
        }

        return new Retrofit.Builder()
                .baseUrl("https://restcountries.eu/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .callFactory(httpClientBuilder.build())
                .build().create(CountryApi.class);
    }

}
