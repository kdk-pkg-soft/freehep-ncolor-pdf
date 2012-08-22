package hep.io.hbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


class Hbook
{
   private final static int VERSION = 2;
   private static boolean init = false;
   private static boolean[] luns;

   static boolean getCWDataBoolean(long buffer, int offset, int size)
   {
      return getCWDataInt(buffer, offset, size) != 0;
   }

   static native double getCWDataDouble(long buffer, int offset, int size);

   static native int getCWDataInt(long buffer, int offset, int size);

   static native long getCWDataLong(long buffer, int offset, int size);

   static native String getCWDataString(long buffer, int offset, int size);

   static synchronized native void setCWEvent(int id, int ievent);

   static native double getRWData(long buffer, int column);

   static synchronized native void setRWEvent(int id, int ievent, long buffer);

   static native void CWClearMap(int id, String block, long buffer);

   static native void CWMap(int id, String block, long buffer, String setName, int offset, int type);

   static native double[] CWgetMinMax(int id, long buffer, int offset, int type, int size, int fixed, int indexOffset);

   static native double[] CWrebin(int id, long buffer, int offset, int type, int size, int fixed, int indexOffset, int bins, double min, double max);

   static native double[] Hist1DData(int id, int bins);

   static native double[] Hist1DErrors(int id, int bins);

   static native double[][] Hist2DData(int id, int xbins, int ybins);

   static native double[][] Hist2DErrors(int id, int xbins, int ybins);

   static native double[] RWrebin(int id, int column, int size, int bins, double min, double max);

   static synchronized native long allocBuffer(int size);

   static synchronized void close(String s, int lun)
   {
      closeFile(s, lun);
      freeLun(lun);
   }

   static synchronized native void delete(int id);

   static synchronized native void freeBuffer(long buffer);

   static native int init();

   static synchronized HbookFileHObj openFile(String s, int recordSize) throws IOException
   {
      if (!init)
      {
         doInit();
      }

      CompositeHbookObject result = openFile(s, getLun(),recordSize);
      if (result == null)
      {
         throw new IOException("Could not open " + s);
      }
      return (HbookFileHObj) result;
   }

   static synchronized native int visitChildren(HbookObject h);

   private static synchronized int getLun()
   {
      for (int i = 10; i < luns.length; i++)
      {
         if (luns[i])
         {
            luns[i] = false;
            return i;
         }
      }
      throw new RuntimeException("No free luns");
   }

   private static native void closeFile(String s, int lun);

   private static void doInit()
   {
       Properties mavenProperties = new Properties();
       String propertyFileName = "/META-INF/maven/org.freehep/freehep-hbook/pom.properties";
       try {
           InputStream is = Hbook.class.getResourceAsStream(propertyFileName);
           if (is == null) throw new RuntimeException("Cannot find "+propertyFileName+" in jar file");
           mavenProperties.load(is);
       } catch (IOException ioe) {
           throw new RuntimeException("Problem resolving name of the native library", ioe);
       }
       
       String libName = mavenProperties.getProperty("artifactId","undefined")+"-"+
                        mavenProperties.getProperty("version", "undefined");
      try
      {
         System.out.println("Loading " + libName + " ...");
         System.loadLibrary(libName);

         int version = init();
         if (version != VERSION) throw new RuntimeException("Hbook library version mismatch, expected "+VERSION+" got "+version);
         luns = new boolean[100];
         for (int i = 0; i < luns.length; i++)
            luns[i] = true;
         init = true;
      }
      catch (Throwable t)
      {
         RuntimeException x = new RuntimeException("Error loading native library: " + libName);
         x.initCause(t);
         throw x;
      }
   }

   private static synchronized void freeLun(int lun)
   {
      luns[lun] = true;
   }

   private static native CompositeHbookObject openFile(String s, int lun, int recordSize);
   
   public static void main(String[] args) {
       doInit();
   }
}