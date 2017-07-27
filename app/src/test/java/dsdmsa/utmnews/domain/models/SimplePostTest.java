package dsdmsa.utmnews.domain.models;

import org.junit.Assert;
import org.junit.Test;

public class SimplePostTest {
    @Test
    public void equals() throws Exception {
        SimplePost simplePost = new SimplePost();
        SimplePost simplePost1 = new SimplePost();

        simplePost.setId(1);
        simplePost1.setId(1);
        Assert.assertTrue(simplePost.equals(simplePost1));
        simplePost1.setId(2);
        Assert.assertFalse(simplePost.equals(simplePost1));
    }

}