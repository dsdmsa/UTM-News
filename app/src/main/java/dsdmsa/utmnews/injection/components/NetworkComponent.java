package dsdmsa.utmnews.injection.components;


import javax.inject.Singleton;

import dagger.Component;
import dsdmsa.utmnews.fragments.LatestNewsFragment;
import dsdmsa.utmnews.fragments.presenter.CategoryPresenter;
import dsdmsa.utmnews.fragments.presenter.NewsPresenter;
import dsdmsa.utmnews.injection.modules.NetworkModule;
import dsdmsa.utmnews.network.controlers.UtmController;

@Singleton
@Component(modules = {NetworkModule.class})
public interface NetworkComponent {
    void inject(LatestNewsFragment __);

    void inject(UtmController __);

    void inject(NewsPresenter __);

    void inject(CategoryPresenter __);
}
