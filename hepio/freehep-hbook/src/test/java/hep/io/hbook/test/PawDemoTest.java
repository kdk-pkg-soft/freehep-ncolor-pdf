package hep.io.hbook.test;
import hep.io.hbook.CompositeHbookObject;
import hep.io.hbook.HbookFile;
import hep.io.hbook.HbookObject;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import org.freehep.util.StringUtilities;

import junit.framework.TestCase;

/**
 *
 * @author tonyj
 */
public class PawDemoTest extends TestCase
{
   private HbookFile hbook;
   private int nObjects;
   public PawDemoTest(String name)
   {
      super(name);
   }
   
   public void testReadFile() throws IOException
   {
      CompositeHbookObject file = hbook.getTopLevelDirectory();
      assertEquals(53,file.nChildren());
      list(file.getChildren());
      assertEquals(280,nObjects);
   }
   private void list(Enumeration e)
   {
      while (e.hasMoreElements())
      {
         HbookObject o = (HbookObject) e.nextElement();
         nObjects++;
         if (o instanceof CompositeHbookObject) list(((CompositeHbookObject) o).getChildren());
      }
   }
   protected void tearDown() throws Exception
   {
      hbook.close();
   }
   
   protected void setUp() throws Exception
   {
      String testDataFile = System.getProperty("org.freehep.hbook.test.data.pawdemo.hbook");
      String localRepository = System.getProperty("localRepository");
      if (localRepository != null) testDataFile = StringUtilities.replace("${localRepository}", localRepository, testDataFile);
      File f = new File(testDataFile);
      hbook = new HbookFile(f);
   }
}