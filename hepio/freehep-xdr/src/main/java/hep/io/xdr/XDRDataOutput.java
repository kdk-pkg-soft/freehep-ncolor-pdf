package hep.io.xdr;

import java.io.Closeable;
import java.io.DataOutput;
import java.io.Flushable;
import java.io.IOException;


/**
 * An interface implemented by output streams that support XDR.
 * @author Tony Johnson (tonyj@slac.stanford.edu)
 * @version $Id: XDRDataOutput.java 13659 2009-10-09 23:23:47Z tonyj $
 */
public interface XDRDataOutput extends DataOutput, Closeable, Flushable
{
   void pad() throws IOException;

   void writeDoubleArray(double[] array) throws IOException;

   void writeDoubleArray(double[] array, int start, int n) throws IOException;

   void writeFloatArray(float[] array) throws IOException;

   void writeFloatArray(float[] array, int start, int n) throws IOException;

   void writeIntArray(int[] array) throws IOException;

   void writeIntArray(int[] array, int start, int n) throws IOException;

   /**
    * Write a string preceeded by its (int) length
    */
   void writeString(String string) throws IOException;

   /**
    * Write a string (no length is written)
    */
   void writeStringChars(String string) throws IOException;
}
