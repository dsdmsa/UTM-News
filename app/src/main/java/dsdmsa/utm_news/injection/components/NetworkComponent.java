package dsdmsa.utm_news.injection.components;


import javax.inject.Singleton;

import dagger.Component;
import dsdmsa.utm_news.fragments.LatestNewsFragment;
import dsdmsa.utm_news.fragments.presenter.NewsPresenter;
import dsdmsa.utm_news.injection.modules.NetworkModule;
import dsdmsa.utm_news.network.controlers.UtmController;

@Singleton
@Component(modules = {NetworkModule.class})
public interface NetworkComponent {
    void inject(LatestNewsFragment __);

    void inject(UtmController __);

    void inject(NewsPresenter __);
}
