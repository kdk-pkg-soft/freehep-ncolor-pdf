package org.freehep.util.test;

import junit.framework.*;
import org.freehep.util.VersionComparator;

/**
 *
 * @author Tony Johnson
 * @version $Id: VersionComparatorTest.java 8584 2006-08-10 23:06:37Z duns $
 */
public class VersionComparatorTest extends TestCase
{
   
   public VersionComparatorTest(java.lang.String testName)
   {
      super(testName);
   }
   
   public static Test suite()
   {
      TestSuite suite = new TestSuite(VersionComparatorTest.class);
      return suite;
   }
   
   /**
    * Test of versionNumberCompare method, of class org.freehep.util.VersionComparator.
    */
   public void testVersionNumberCompare()
   {
      VersionComparator v = new VersionComparator();
      assertTrue(v.compare("1", "2") < 0);
      assertTrue(v.compare("2", "1") > 0);
      assertTrue(v.compare("2", "2") == 0); 
      assertTrue(v.compare("2.1", "2.1") == 0); 
      assertTrue(v.compare("2.....1", "2.1") == 0); 
      assertTrue(v.compare("2.1.0", "2.1") == 0); // Correct?
      assertTrue(v.compare("2.1.1", "2.1") > 0);
      assertTrue(v.compare("2.1.1", "2.1.2") < 0); 

      assertTrue(v.compare("2.1.1rc1", "2.1.1") < 0); 
      assertTrue(v.compare("2.1.1rc1", "2.1.1rc2") < 0); 
      assertTrue(v.compare("2.1.1rc1", "2.1.1rc1") == 0); 
      
      assertTrue(v.compare("2.1.1beta1", "2.1.1rc1") < 0); 
      assertTrue(v.compare("2.1.1alpha1", "2.1.1beta1") < 0); 
   }
   
   public static void main(java.lang.String[] args)
   {
      junit.textui.TestRunner.run(suite());
   }
}
