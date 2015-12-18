import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

/**
 * The test class test.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */

public class test
{
    /**
     * Default constructor for test class test
     */
    public test()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void bla()
    {
        Stuhl_Gruppe stuhl_Gr1 = new Stuhl_Gruppe(8);
        stuhl_Gr1.zeige();
        stuhl_Gr1.aendereFarbe("blau");
    }
}
