package test.DS_source;

import DS_source.Sqlist;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

/**
 * Sqlist Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>十一月 8, 2015</pre>
 */
public class SqlistTest {
    /**
     * Method: insert(int insertInt)
     */
    @Test
    public void testInsert() throws Exception {
        Sqlist list=new Sqlist(20);
        list.insert(1);
        assertEquals(list.show(),"1");
        list.insert(2);
        assertEquals(list.show(),"12");
        list.insert(7);
        assertEquals(list.show(),"127");
        list.insert(40);
        assertEquals(list.show(),"12740");
        list.insert(34);
        assertEquals(list.show(),"1273440");
    }
    @Test
    public void testNewList(){
        Sqlist list=new Sqlist(2);
        list.insert(1);
        assertEquals(list.show(),"1");
        list.insert(2);
        assertEquals(list.show(),"12");
        list.insert(7);
        assertEquals(list.show(),"127");
        list.insert(40);
        assertEquals(list.show(),"12740");
    }
}
