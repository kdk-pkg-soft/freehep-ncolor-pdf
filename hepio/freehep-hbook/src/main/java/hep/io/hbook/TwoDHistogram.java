package hep.io.hbook;
   
/** Represents a 2D histogram read from an hbook file */
public class TwoDHistogram extends HbookObject
{
   private float xmax;
   private float xmin;
   private float ymax;
   private float ymin;
   private int nent;
   private int id;
   private int idx;
   private int xbins;
   private int ybins;
   private float xmean, ymean, xrms, yrms, xequiv, yequiv;
      
   TwoDHistogram(CompositeHbookObject parent, String name, int id, int idx, int xbins, float xmin, float xmax, int ybins, float ymin, float ymax, int nent, float xmean, float ymean, float xrms, float yrms, float xequiv, float yequiv)
   {
      super(parent, name);

      this.id = id;
      this.idx = idx;
      this.xmin = xmin;
      this.xmax = xmax;
      this.xbins = xbins;
      this.ymin = ymin;
      this.ymax = ymax;
      this.ybins = ybins;
      this.nent = nent;
      this.xmean = xmean;
      this.ymean = ymean;
      this.xrms = xrms;
      this.yrms = yrms;
      this.xequiv = xequiv;
      this.yequiv = yequiv;
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
      String result = "2D Histogram: " + idx + " " + getName();
      return result;
   }
   public float getXMin()
   {
      return xmin;
   }
   public float getXMax()
   {
      return xmax;
   }
   public int getXNBins()
   {
      return xbins;
   }
   public float getYMin()
   {
      return ymin;
   }
   public float getYMax()
   {
      return ymax;
   }
   public int getYNBins()
   {
      return ybins;
   }   
   public float getXMean()
   {
      return xmean;
   }
   public float getYMean()
   {
      return ymean;
   }
   public float getXRMS()
   {
      return xrms;
   }
   public float getYRMS()
   {
      return yrms;
   }
   public float getXEquivBinEntries()
   {
      return xequiv;
   }
   public float getYEquivBinEntries()
   {
      return yequiv;
   }
   public int getNEntries()
   {
      return nent;
   }
   /** Get the contents of the bins, including underflow and overflow bins */   
   public double[][] getBins()
   {
      return Hbook.Hist2DData(id, xbins, ybins);
   }
   /** Get the bin errors, including underflow and overflow bins */   
   public double[][] getErrors()
   {
      return Hbook.Hist2DErrors(id, xbins, ybins);
   }
   /** Get the ID for this histogram */   
   public int id()
   {
      return idx;
   }
}