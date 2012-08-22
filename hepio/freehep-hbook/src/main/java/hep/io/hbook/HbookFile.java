package hep.io.hbook;

import java.io.File;
import java.io.IOException;

/**
 * Class for reading an HBook/PAW file.
 * @author tonyj
 * @version $Id: HbookFile.java 8584 2006-08-10 23:06:37Z duns $
 */
public class HbookFile
{
   private HbookFileHObj hbook;
   /** Open an hbook file.
    * @param file The file to open
    * @throws IOException If an IOError occurs while opening the file
    */
   public HbookFile(File file) throws IOException
   {
      this(file.getAbsolutePath());
   }
   /** Open an hbook file.
    * @param fileName The name of the file to open
    * @throws IOException If an IOError occurs while opening the file
    */
   public HbookFile(String fileName) throws IOException
   {
      hbook = Hbook.openFile(fileName, 0);
   }
   /** Open an hbook file.
    * @param fileName The name of the file to open
    * @param recordSize The record size (0 for default)
    * @throws IOException If an IOError occurs while opening the file
    */
   public HbookFile(String fileName, int recordSize) throws IOException
   {
      hbook = Hbook.openFile(fileName, recordSize);
   }
   /** Close the file. No more operations can be performed on the file or any
    * of the objects obtained from the file after it is closed.
    * @throws IOException If an IOError occurs while closing the file
    */
   public void close() throws IOException
   {
      hbook.close();
   }
   /**
    * Returns the top level directory for this file. Use this to access objects
    * inside the HBook file.
    * @return The CompositeHbookObject representing the top level directory
    */
   public CompositeHbookObject getTopLevelDirectory()
   {
      return hbook;
   }
}
