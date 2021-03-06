/*
 * AreaNormalizer.java
 *
 * Created on January 23, 2001, 6:09 PM
 */

package jas.hist.normalization;
import jas.hist.DataSource;
import jas.hist.Rebinnable1DHistogramData;

/**
 * Calculates a normalization factor based on the area under a data set.
 * @author tonyj
 * @version $Id: AreaNormalizer.java 11553 2007-06-05 22:06:23Z duns $
 */
public class AreaNormalizer extends DataSourceNormalizer
{
/** Create an AreaNormalizer
 * @param data The data source
 */    
    public AreaNormalizer(DataSource data) 
    {
        super(data);
        init();
    }
    protected double calculateNormalization()
    {
        if (source instanceof Rebinnable1DHistogramData)
        {
            Rebinnable1DHistogramData data = (Rebinnable1DHistogramData) source;
            int nBins = data.getBins();
            double xMin = data.getMin();
            double xMax = data.getMax();
            double[][] bins = data.rebin(nBins,xMin,xMax,false,hurry);
            double[] y = bins[0];
            double area = 0;
            for (int i=0; i<y.length; i++) area += y[i];
            double binWidth = (xMax - xMin)/nBins;
            area *= binWidth;
            return area > 0 ? area : 1;
        }
        else return 1;
    }
}

