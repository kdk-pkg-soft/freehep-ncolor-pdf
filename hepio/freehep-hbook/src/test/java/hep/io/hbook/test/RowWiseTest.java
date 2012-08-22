package hep.io.hbook.test;

import hep.io.hbook.CompositeHbookObject;
import hep.io.hbook.HbookFile;
import hep.io.hbook.RowwiseTuple;
import hep.io.hbook.RowwiseTupleColumn;
import org.freehep.util.*;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

/**
 *
 * @author Tony Johnson
 */
public class RowWiseTest extends TestCase
{
   private HbookFile hbook;
   public RowWiseTest(String name)
   {
      super(name);
   }
   public void testRowWiseTuple() throws IOException
   {
      CompositeHbookObject top = hbook.getTopLevelDirectory();
      
      assertEquals(top.nChildren(),5);
      
      RowwiseTuple tuple = (RowwiseTuple) top.getChild(1);
      int n = tuple.nChildren();
      assertEquals(n,10);

      RowwiseTuple tuple2 = (RowwiseTuple) top.getChild(2);
      assertEquals(tuple2.nChildren(),8);
      assertEquals(tuple.getRows(),5888);
      
      assertEquals(mean(tuple,0),8915629,1);
      assertEquals(mean(tuple,1),10.38145,0.00001);
      assertEquals(mean(tuple,2),0,.00001);
      assertEquals(mean(tuple,3),.0021680,0.0000001);

      RowwiseTuple tuple3 = (RowwiseTuple) top.getChild(3);
      assertEquals(tuple3.nChildren(),3);
      assertEquals(tuple3.getRows(),1000);
      assertEquals(mean(tuple3,0),-1.5054,0.0001);
      assertEquals(mean(tuple3,1),-2.4667,0.0001);
      assertEquals(mean(tuple3,2),71.975,.001);

//      String testDataDir = "C:\\Documents and Settings\\Tony Johnson\\My Documents\\My Data\\test";
//      File f = new File(testDataDir,"pawdemo.hbook");
//      HbookFile hbook2 = new HbookFile(f);
//      OneDHistogram h100 =  (OneDHistogram) hbook2.getTopLevelDirectory().getChild("MULTIPLICITY - WEIGHTED");
//      assertEquals(h100.getXMean(),10.079,0.001);
//
//      assertEquals(mean(tuple,1),10.38145,0.00001);
//      assertEquals(mean(tuple,2),0,.00001);
//      assertEquals(mean(tuple,3),.0021680,0.0000001);
//      
//      hbook2.close();
   }
   
   private double mean(RowwiseTuple tuple, int col)
   {
      double mean = 0;
      int r = tuple.getRows();
      for (int i=0; i<r; i++)
      {
         tuple.setCurrentRow(i+1);
         mean += ((RowwiseTupleColumn) tuple.getChild(col)).getDouble();
      }     
      return mean/r;
   }
   
   protected void tearDown() throws Exception
   {
      hbook.close();
   }
   
   protected void setUp() throws Exception
   {
       String testDataFile = System.getProperty("org.freehep.hbook.test.data.rowwise.hbook");
       String localRepository = System.getProperty("localRepository");
       if (localRepository != null) testDataFile = StringUtilities.replace("${localRepository}", localRepository, testDataFile);
       File f = new File(testDataFile);
      hbook = new HbookFile(f);
   }   
}
