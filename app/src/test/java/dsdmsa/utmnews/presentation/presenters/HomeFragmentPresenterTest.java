package dsdmsa.utmnews.presentation.presenters;

import android.content.ContextWrapper;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.RxImmediateSchedulerRule;
import dsdmsa.utmnews.presentation.mvp.HomeContract$View$$State;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class HomeFragmentPresenterTest {

    private HomeFragmentPresenter presenter;

    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();

    @Mock
    HomeContract$View$$State view$$State;
    @Mock
    App app;
//    @Mock
//    Context ctx;
    @Mock
    ContextWrapper contextWrapper;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        app = new App();
        app.initModules();
        presenter = new HomeFragmentPresenter();
        presenter.setViewState(view$$State);
    }


    @Test
    public void getCategories() throws Exception {
//        when(ctx.getString(R.string.categories)).thenReturn("Se încarcă categoriile");

        presenter.getCategories();
        verify(view$$State).showProgressDialog();
        verify(view$$State).showInfoMessage("Se încarcă categoriile");
        verify(view$$State).hideProgressDialog();
    }

}