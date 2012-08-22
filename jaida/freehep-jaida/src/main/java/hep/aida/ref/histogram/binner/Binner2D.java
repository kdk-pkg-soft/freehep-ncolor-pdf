/*
 * OneDBinner.java
 *
 * Created on July 18, 2002, 5:02 PM
 */

package hep.aida.ref.histogram.binner;

/**
 *
 * @author  The AIDA team at SLAC
 *
 */
public interface Binner2D {
    
    public void fill(int xBin, int yBin, double x, double y, double weight);    
    public void clear();
    public int entries(int xBin, int yBin);
    public double height(int xBin, int yBin);
    public double plusError(int xBin, int yBin);
    public double minusError(int xBin, int yBin);
    public double meanX(int xBin, int yBin);
    public double rmsX(int xBin, int yBin);
    public double meanY(int xBin, int yBin);
    public double rmsY(int xBin, int yBin);
    public void setBinContent(int xBin, int yBin, int entries, double height, double plusError, double minusError, double meanX, double rmsX, double meanY, double rmsY);
    public void scale(double scaleFactor);
}
