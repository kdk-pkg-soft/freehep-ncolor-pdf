package hep.io.hbook;

/** Represents a 1D histogram read from an hbook file. */
public class OneDHistogram extends HbookObject
{
   private float max;
   private float min;
   private float mean;
   private float rms;
   private float equiv;
   private int nent;
   private int bins;
   private int id;
   private int idx;

   OneDHistogram(CompositeHbookObject parent, String name, int id, int idx, int bins, float min, float max, int nent, float mean, float rms, float equiv)
   {
      super(parent, name);

      this.id = id;
      this.idx = idx;
      this.min = min;
      this.max = max;
      this.bins = bins;
      this.mean = mean;
      this.rms = rms;
      this.equiv = equiv;
      this.nent = nent;
   }

   void close()
   {
      super.close();
      if (id >= 0)
      {
         Hbook.delete(id);
      }
      id = -1;
   }

   public void finalize()
   {
      close();
   }

   public String toString()
   {
      String result = "1D Histogram: " + idx + " " + getName() + " (min=" + min + " max=" + max + " bins=" +bins + ")";
      return result;
   }
   public float getXMin()
   {
      return min;
   }
   public float getXMax()
   {
      return max;
   }
   public float getXMean()
   {
      return mean;
   }
   public float getXRMS()
   {
      return rms;
   }   
   public int getXNBins()
   {
      return bins;
   }
   public float getXEquivBinEntries()
   {
      return equiv;
   }
   public int getNEntries()
   {
      return nent;
   }
   /**
    * Returns the contents of the bins, including the overflow and underflow bin
    */
   public double[] getBins()
   {
      return Hbook.Hist1DData(id, bins);
   }
   /**
    * Returns the error on the bins, including the overflow and underflow bin
    */
   public double[] getErrors()
   {
      return Hbook.Hist1DErrors(id, bins);
   }
   /** The id of the histogram */   
   public int id()
   {
      return idx;
   }   
}