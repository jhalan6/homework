package test.DS_application;

import DS_application.SystemTool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * SystemTool Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>十一月 8, 2015</pre>
 */
@RunWith(Parameterized.class)
public class SystemToolTest {
    private String result;
    private int intTest;
    private SystemTool.system castTo;

    public SystemToolTest(String result, int intTest, SystemTool.system castTo) {
        this.result = result;
        this.intTest = intTest;
        this.castTo = castTo;
    }

    /**
     * Method: castFromInt(system castTo, int intNumber)
     */
    @Test
    public void testCastFromInt() throws Exception {
        assertEquals(SystemTool.castFromInt(castTo, intTest), result);
    }

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][]{
                {"10101", 21, SystemTool.system.Binary},
                {"25", 21, SystemTool.system.Octonary},
                {"15", 21, SystemTool.system.Hexadecimal},
                {"21", 21, SystemTool.system.Decimal},
        });

    }

} 
