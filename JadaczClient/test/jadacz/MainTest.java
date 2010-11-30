/*
 * MainTest.java
 * JUnit based test
 *
 * Created on 11 grudzieñ 2006, 22:29
 */

package jadacz;

import junit.framework.*;

/**
 *
 * @author Cyber
 */
public class MainTest extends TestCase {
    
    public MainTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    /**
     * Test of main method, of class jadacz.Main.
     */
    public void testMain() {
        System.out.println("main");
        
        String[] args = null;
        
        Main.main(args);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
