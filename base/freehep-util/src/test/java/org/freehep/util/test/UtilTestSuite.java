package org.freehep.util.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author Tony Johnson
 * @version $Id: UtilTestSuite.java 8584 2006-08-10 23:06:37Z duns $
 *
 */
public class UtilTestSuite extends TestCase
{
   
   private TestSuite suite;
   
   public UtilTestSuite(java.lang.String testName)
   {
      super(testName);
      suite = (TestSuite) suite();
   }
   
   protected TestSuite getSuite()
   {
      return suite;
   }
   
   public static Test suite()
   {
      TestSuite suite = new TestSuite();
      
      // Add all the test suites here
      
      suite.addTestSuite( DoubleHashtableTest.class );
      suite.addTestSuite( VersionComparatorTest.class );
      suite.addTestSuite( ScientificFormatTest.class );
      
      return suite;
   }
}
