package dsdmsa.utmnews.data.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mock;

import dsdmsa.utmnews.App;
import dsdmsa.utmnews.RxImmediateSchedulerRule;
import dsdmsa.utmnews.domain.models.Post;
import dsdmsa.utmnews.domain.models.SimplePost;


public class PostUtilsTest {

    @ClassRule
    public static final RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();

    @Mock
    App app;

    @Before
    public void setUp() throws Exception {
        app = new App();
        app.initModules();
    }

    @Test
    public void toSimplePost() throws Exception {
        Post post = new Post();
        post.setId(1);
        SimplePost simplePost = PostUtils.toSimplePost(post);
        Assert.assertNotNull(post.getId());
        Assert.assertNotNull(simplePost.getId());
        Assert.assertTrue(post.getId() == simplePost.getId());
    }


    @Test
    public void setDate() throws Exception {

    }

}