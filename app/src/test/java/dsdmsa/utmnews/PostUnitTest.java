package dsdmsa.utmnews;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Post local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class PostUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


//    boolean hasPathWithGivenSum(Tree<Integer> t, int s) {
//
//
//    }

    int getSum(Tree<Integer> n) {
        if (n.left != null)
            return  n.value + getSum(n.left);
        if (n.right != null)
            return  n.value + getSum(n.right);
        return n.value;

    }

    class Tree<T> {
        Tree(T x) {
            value = x;
        }

        T value;
        Tree<T> left;
        Tree<T> right;
    }


}