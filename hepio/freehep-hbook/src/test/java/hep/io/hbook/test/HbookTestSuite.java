package hep.io.hbook.test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author The AIDA team @ SLAC.
 *
 */
public class HbookTestSuite extends TestCase {
    
    private TestSuite suite;
    
    public HbookTestSuite(java.lang.String testName) {
        super(testName);        
        suite = (TestSuite) suite();
    }
    
    public TestSuite getSuite() {
        return suite;
    }
        
    public static junit.framework.Test suite() {
        TestSuite suite = new TestSuite();
        
        // Add all the test suites here	
	
        suite.addTestSuite( PawDemoTest.class );	
        suite.addTestSuite( JASTest.class );
        suite.addTestSuite( RowWiseTest.class );
        
        return suite;
    }
}
