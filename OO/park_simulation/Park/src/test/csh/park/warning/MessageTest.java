package test.csh.park.warning; 

import csh.park.warning.Message;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

/** 
* Message Tester.
* 
* @author <Authors name> 
* @since <pre>十二月 9, 2015</pre> 
* @version 1.0 
*/ 
public class MessageTest {

@Before
public void before() throws Exception { 
} 
@Test
public void testConstructor() throws Exception{
    Message message_1 =new Message(Message.MessageType.not_found_in_system);
    Message message_2 =new Message(Message.MessageType.not_found_in_register);
    assertEquals(message_1.getMessageType(), Message.MessageType.not_found_in_system);
    assertEquals(message_2.getMessageType(), Message.MessageType.not_found_in_register);
}
@After
public void after() throws Exception { 
} 


} 
