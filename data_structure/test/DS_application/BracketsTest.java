package test.DS_application;

import static org.junit.Assert.*;

import DS_application.Brackets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Brackets Tester.
 *
 * @author jhalan6
 * @version 1.0
 * @since <pre>十一月 7, 2015</pre>
 */
@RunWith(Parameterized.class)
public class BracketsTest {
    private String toTest;
    private boolean result;

    public BracketsTest(String toTest, boolean result) {
        this.toTest = toTest;
        this.result = result;
    }

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][]{
                {"[(2+3))", false},
                {"[2+3])", false},
                {"()}", false},
                {"[(2+3)]", true}
        });
    }

    /**
     * Method: checkBrackets(String s)
     */
    @Test
    public void testCheckBrackets() throws Exception {
        assertEquals(Brackets.checkBrackets(toTest), result);
    }

} 
