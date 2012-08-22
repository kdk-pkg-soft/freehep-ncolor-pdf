/*
 * RowwiseTupleField.java
 *
 * Created on November 1, 2002, 10:48 PM
 */

package hep.io.hbook;

/** A column in a rowwise Tuple
 * @author tonyj
 * @version $Id: RowwiseTupleColumn.java 8584 2006-08-10 23:06:37Z duns $
 */
public class RowwiseTupleColumn extends TupleColumn {
    RowwiseTupleColumn(CompositeHbookObject parent, String name, int column, float min, float max) {
        super(parent, name);
        
        this.min = min;
        this.max = max;
        this.column = column;
    }
    /** The current value of this column */    
    public double getDouble() {
        return Hbook.getRWData(((RowwiseTuple) getParent()).getBuffer(), column);
    }
    /** The minimum value in this column */    
    public double getMin()
    {
       return min;
    }
    /** The maximum value in this column */    
    public double getMax()
    {
       return max;
    }
    public String toString() {
        String result = "RW Tuple Field: "+getName()+" (min="+min+" max="+max+")";
        return result;
    }
    private int column;
    private double min;
    private double max;
}