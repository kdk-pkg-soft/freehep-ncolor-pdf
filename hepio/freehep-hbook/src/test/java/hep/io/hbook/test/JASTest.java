package hep.io.hbook.test;
import hep.io.hbook.ColumnwiseTuple;
import hep.io.hbook.ColumnwiseTupleColumn;
import hep.io.hbook.CompositeHbookObject;
import hep.io.hbook.HbookFile;

import java.io.File;
import java.io.IOException;

import org.freehep.util.StringUtilities;

import junit.framework.TestCase;

/**
 *
 * @author tonyj
 */
public class JASTest extends TestCase
{
   private HbookFile hbook;
   public JASTest(String name)
   {
      super(name);
   }
   
   public void testColumnWiseTuple() throws IOException
   {
      CompositeHbookObject file = hbook.getTopLevelDirectory();
      ColumnwiseTuple tuple = (ColumnwiseTuple) file.getChild("ORANGE");
      assertEquals(337,tuple.nChildren());
      assertEquals(100,tuple.getRows());

      ColumnwiseTupleColumn col = (ColumnwiseTupleColumn) tuple.getChild("Tltw_4");
      assertEquals("Tltw_4",col.getName());
      long total = 0;
      for (int i=1; i<=tuple.getRows(); i++)
      {
         tuple.setCurrentRow(i);
         total += col.getInt();
      }
      assertEquals(33685231556L,total);
      total = 0;
      for (int i=1; i<=tuple.getRows(); i++)
      {
         tuple.setCurrentRow(i);
         for (int j=0; j<tuple.nChildren(); j++)
         {
            ColumnwiseTupleColumn col2 = (ColumnwiseTupleColumn) tuple.getChild(j);
            if (col2.getColumnClass() == Double.TYPE) col2.getDouble();
         }
         total += col.getInt();
      }
      assertEquals(33685231556L,total); // Test for JAS-32
   }
   protected void tearDown() throws Exception
   {
      hbook.close();
   }
   
   protected void setUp() throws Exception
   {
       String testDataFile = System.getProperty("org.freehep.hbook.test.data.JAS.hbook");
       String localRepository = System.getProperty("localRepository");
       if (localRepository != null) testDataFile = StringUtilities.replace("${localRepository}", localRepository, testDataFile);
       File f = new File(testDataFile);
      hbook = new HbookFile(f);
   }
}