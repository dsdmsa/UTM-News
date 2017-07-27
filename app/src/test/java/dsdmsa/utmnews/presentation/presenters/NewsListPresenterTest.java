package dsdmsa.utmnews.presentation.presenters;

import android.content.Context;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.RxImmediateSchedulerRule;
import dsdmsa.utmnews.domain.models.SimplePost;
import dsdmsa.utmnews.presentation.mvp.NewsContract$View$$State;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class NewsListPresenterTest {

    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();

    private NewsListPresenter newsListPresenter;
    @Mock
    private NewsContract$View$$State viewState;
    @Mock
    Context context;
    @Mock
    private List<SimplePost> simplePosts;
    @Mock private SimplePost simplePost;

    @Mock
    App app;

    @Before
    public void setUp() throws Exception {
        app = new App();
        app.initModules();
        newsListPresenter = new NewsListPresenter();
        newsListPresenter.setViewState(viewState);
    }

    @Test
    public void getNews() throws Exception {
        newsListPresenter.getNews(1);
        verify(viewState).showProgressDialog();
        verify(viewState).hideProgressDialog();
    }

    @Test
    public void refreshNewses() throws Exception {
        newsListPresenter.refreshNewses();
        verify(viewState).showProgressDialog();
        verify(viewState).hideProgressDialog();
    }

}