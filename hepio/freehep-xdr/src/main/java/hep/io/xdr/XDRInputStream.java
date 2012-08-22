package hep.io.xdr;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A class for reading XDR files. Not too hard to do in Java since the XDR format is very
 * similar to the Java native DataStream format, except for String and the fact that elements
 * (or an array of elements) are always padded to a multiple of 4 bytes.
 *
 * This class requires the user to call the pad method, to skip to the next
 * 4-byte boundary after reading an element or array of elements that may not
 * span a multiple of 4 bytes.
 * @author Tony Johnson (tonyj@slac.stanford.edu)
 * @version $Id: XDRInputStream.java 13677 2009-10-20 03:33:02Z tonyj $
 */
public class XDRInputStream extends DataInputStream implements XDRDataInput
{
   private CountedInputStream cin;
   private final static int SANITY_CHECK = Integer.getInteger("hep.io.xdr.sanityCheck",100000).intValue();

   public XDRInputStream(InputStream in)
   {
      super(new CountedInputStream(in));
      cin = (CountedInputStream) this.in;
   }

   public long getBytesRead()
   {
      return cin.getBytesRead();
   }

   /**
    * Sets a limit on the number of bytes that can be read from this file
    * before an EOF will be generated
    */
   public void setReadLimit(int bytes)
   {
      cin.setReadLimit(bytes);
   }

   public void clearReadLimit()
   {
      cin.clearReadLimit();
   }

   /**
    * Skips appropriate amount to bring stream to 4-byte boundary.
    */
   public void pad() throws IOException
   {
      int offset = (int) (getBytesRead() % 4);
      if (offset != 0)
         skipBytes(4 - offset);
   }

   public double[] readDoubleArray(double[] buffer) throws IOException
   {
      int l = readInt();
      if (l > SANITY_CHECK)
         throw new IOException("Array length failed sanity check: " + l);

      double[] result = buffer;
      if ((buffer == null) || (l > buffer.length))
         result = new double[l];
      for (int i = 0; i < l; i++)
         result[i] = readDouble();
      return result;
   }

   public float[] readFloatArray(float[] buffer) throws IOException
   {
      int l = readInt();
      if (l > SANITY_CHECK)
         throw new IOException("Array length failed sanity check: " + l);

      float[] result = buffer;
      if ((buffer == null) || (l > buffer.length))
         result = new float[l];
      for (int i = 0; i < l; i++)
         result[i] = readFloat();
      return result;
   }

   public int[] readIntArray(int[] buffer) throws IOException
   {
      int l = readInt();
      if (l > SANITY_CHECK)
         throw new IOException("Array length failed sanity check: " + l);

      int[] result = buffer;
      if ((buffer == null) || (l > buffer.length))
         result = new int[l];
      for (int i = 0; i < l; i++)
         result[i] = readInt();
      return result;
   }

   public String readString(int l) throws IOException
   {
      byte[] ascii = new byte[l];
      readFully(ascii);
      pad();
      return new String(ascii,"US-ASCII");
   }

   public String readString() throws IOException
   {
      int l = readInt();
      if (l > SANITY_CHECK)
         throw new IOException("String length failed sanity check: " + l);
      return readString(l);
   }

   private static final class CountedInputStream extends FilterInputStream
   {
      private long count = 0;
      private long limit = -1;
      private long mark = 0;

      CountedInputStream(InputStream in)
      {
         super(in);
      }

      public long getBytesRead()
      {
         return count;
      }

      public int available() throws IOException
      {
         return Math.min((int) (limit - count), super.available());
      }

      public synchronized void mark(int readlimit)
      {
         mark = count;
         super.mark(readlimit);
      }

      public int read() throws IOException
      {
         int available = checkLimit(1);
         int rc = super.read();
         if (rc >= 0)
            count++;
         return rc;
      }

      public int read(byte[] data) throws IOException
      {
         return read(data, 0, data.length);
      }

      public int read(byte[] data, int off, int len) throws IOException
      {
         int available = checkLimit(len);
         int rc = super.read(data, off, available);
         if (rc > 0)
            count += rc;
         return rc;
      }

      public synchronized void reset() throws IOException
      {
         count = mark;
         super.reset();
      }

      public long skip(long bytes) throws IOException
      {
         long available = checkLimit(bytes);
         long rc = super.skip(available);
         if (rc > 0)
            count += rc;
         return rc;
      }

      /**
       * Sets a limit on the number of bytes that can be read from this file
       * before an EOF will be generated
       */
      void setReadLimit(int bytes)
      {
         limit = count + bytes;
      }

      void clearReadLimit()
      {
         limit = -1;
      }

      private int checkLimit(int request) throws IOException
      {
         if (limit < 0)
            return request;
         else if (limit <= count)
            throw new EOFException();
         return Math.min(request, (int) (limit - count));
      }

      private long checkLimit(long request) throws IOException
      {
         if (limit < 0)
            return request;
         else if (limit <= count)
            throw new EOFException();
         return Math.min(request, limit - count);
      }
   }
}
