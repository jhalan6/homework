package test.DS_source;

import DS_source.Stack;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

/**
 * Stack Tester.
 *
 * @author jhalan6
 * @version 1.0
 * @since <pre>十一月 8, 2015</pre>
 */
public class StackTest {
    /**
     * Method: isEmpty()
     */
    @Test
    public void testIsEmpty() throws Exception {
        Stack<String> stack=new Stack<String>(10);
        assertEquals(stack.isEmpty(),true);
        stack.push("aa");
        assertEquals(stack.isEmpty(),false);
    }

    /**
     * Method: isFull()
     */
    @Test
    public void testIsFull() throws Exception {
        Stack<String> stack=new Stack<String>(1);
        assertEquals(stack.isFull(),false);
        stack.push("aa");
        assertEquals(stack.isFull(),true);
    }

    /**
     * Method: getTop()
     */
    @Test
    public void testGetTop() throws Exception {
        Stack<String> stack=new Stack<String>(1);
        assertEquals(stack.getTop(),-1);
        stack.push("aa");
        assertEquals(stack.getTop(),0);
    }

    /**
     * Method: pop()
     */
    @Test
    public void testPop() throws Exception {
        Stack<String> stack=new Stack<String>(2);
        stack.push("aa");
        stack.push("bb");
        assertEquals(stack.pop(),"bb");
        assertEquals(stack.checkTop(),"aa");
    }

    /**
     * Method: checkTop()
     */
    @Test
    public void testCheckTop() throws Exception {
        Stack<String> stack=new Stack<String>(2);
        assertEquals(stack.checkTop(),null);
        stack.push("aa");
        assertEquals(stack.checkTop(),"aa");
    }
}
