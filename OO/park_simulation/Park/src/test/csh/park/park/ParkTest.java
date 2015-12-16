package test.csh.park.park; 

import csh.park.park.Park;
import csh.park.ui.ParkFrame;
import csh.park.data.PublicData;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import static org.junit.Assert.*;

/** 
* Park Tester. 
* 
* @author <Authors name> 
* @since <pre>十二月 9, 2015</pre> 
* @version 1.0 
*/ 
public class ParkTest { 

@Before
public void before() throws Exception { 
} 
@Test
public void testPark() throws InterruptedException {
    int a=20;
    int n=a/2;
    ParkFrame parkFrame=new ParkFrame();
    Park park=new Park();
    for (int x=0;x<5;++x){
        for (int y=1;y<n+1;++y){
                assertEquals(park.getStatus(x,y),true);
        }
    }
    assertEquals(park.getStatus(2,n+1),true);
    assertEquals(park.getStatus(1,n+1),false);
    assertEquals(park.getStatus(0,n+1),false);
    assertEquals(park.getStatus(3,n+1),false);
    assertEquals(park.getStatus(4,n+1),false);
    assertEquals(park.getStatus(2,0),true);
    assertEquals(park.getStatus(1,0),false);
    assertEquals(park.getStatus(0,0),false);
    assertEquals(park.getStatus(3,0),false);
    assertEquals(park.getStatus(4,0),false);
}
@After
public void after() throws Exception { 
} 


} 
