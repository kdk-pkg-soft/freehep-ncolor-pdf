/*
 * ColumnwiseTupleField.java
 *
 * Created on November 1, 2002, 12:41 PM
 */

package hep.io.hbook;

import java.util.StringTokenizer;

/** A column in a ColumnwiseTuple
 * @author tonyj
 * @version $Id: ColumnwiseTupleColumn.java 8584 2006-08-10 23:06:37Z duns $
 */
public class ColumnwiseTupleColumn extends TupleColumn {
    ColumnwiseTupleColumn(ColumnwiseTuple parent, String name, String full, String block,
    int column, int nsub, int type, int size, int elem) {
        super(parent, name);
        
        this.min = 0;
        this.max = 0;
        this.column = column;
        
        this.full = full;
        this.nsub = nsub;
        this.size = size;
        this.elem = elem;
        this.block = parent.getBlock(block);
        this.offset = this.block.allocSpace(this,size*elem);
        this.fixed = 1;
        this.type = type;
        
        // Parse apart the full name to find the array indexes
        if (nsub > 0) {
            dimension = new int[nsub];
            StringTokenizer t = new StringTokenizer(full,"[]",false);
            t.nextToken(); // skip first token (variable name)
            for (int i=0; i<nsub; i++) {
                String token = t.nextToken();
                if (token == null) throw new RuntimeException("Paw columnwise tuple parse failure "+full);
                try {
                    dimension[i] = Integer.parseInt(token);
                    fixed *= dimension[i];
                }
                catch (NumberFormatException x) {
//                    ColumnwiseTuple tuple = (ColumnwiseTuple) getParent();
                    ColumnwiseTupleColumn field = (ColumnwiseTupleColumn) parent.getChild(token);
                    if (field == null) throw new RuntimeException("Paw columnwise tuple parse failure "+full);
                    dimension[i] = -field.column;
                    index = field;
                }
            }
        }
    }
    public String toString() {
        String result = "CW Tuple Field: ("+getColumnClass().getName()+") "+full;
        if (minMaxKnown) result += " (min="+min+" max="+max+")";
        return result;
    }
    /** Get the value of this column as a double */    
    public double getDouble() {
        if (type == 2 || type == 3) return getInt();
        else if (type != 1) throw new RuntimeException("getDouble called for non-float data: "+getName());
        if (elem > 1) throw new RuntimeException("getDouble called for array data: "+getName());
        if (!isMapped) block.map(this);
        return Hbook.getCWDataDouble(block.getBuffer(),offset,size);
    }
    /** Get the value of this column as an int */    
    public int getInt() {
        if (type != 2 && type != 3) throw new RuntimeException("getInt called for non-integer data: "+getName());
        if (elem > 1) throw new RuntimeException("getInt called for array data: "+getName());
        if (!isMapped) block.map(this);
        return Hbook.getCWDataInt(block.getBuffer(),offset,size);
    }
    /** Get the value of this column as a boolean */    
    public boolean getBoolean() {
        if (type != 4) throw new RuntimeException("getBoolean called for non-boolean data: "+getName());
        if (elem > 1) throw new RuntimeException("getBoolean called for array data: "+getName());
        if (!isMapped) block.map(this);
        return Hbook.getCWDataBoolean(block.getBuffer(),offset,size);
    }
    /** Get the value of this column as a String */    
    public String getString() {
        if (type != 5) throw new RuntimeException("getString called for non-string data: "+getName());
        if (elem > 1) throw new RuntimeException("getString called for array data: "+getName());
        if (!isMapped) block.map(this);
        
        return Hbook.getCWDataString(block.getBuffer(),offset,size);
    }
    /** Get the value of this column as an PawArray */    
    public PawArray getObject() {
        if (array == null) {
            if      (type == 1) array = new DoubleArray();
            else if (type == 4) array = new BooleanArray();
            else if (type == 5) array = new StringArray();
            else                array = new IntegerArray();
        }
        return array;
    }
    /** Get the type of this column
     * @return The java class representing the column type
     */    
    public Class getColumnClass() {
        if (elem > 1) return arrayClasses[type];
        else          return classes[type];
    }

    /** The minimum value for this column */    
    public double getMin() {
        if (!minMaxKnown) getMinMax();
        return min;
    }
    /** The maximum value for this column */    
    public double getMax() {
        if (!minMaxKnown) getMinMax();
        return max;
    }
    private void getMinMax() {
        ColumnwiseTuple parent = (ColumnwiseTuple) getParent();
        int id = parent.getID();
        
        block.map(this);
        
        double[] mm = Hbook.CWgetMinMax(id,block.getBuffer(),offset,type,size,fixed,index != null ? index.offset : -1);
        min = mm[0];
        max = mm[1];
        minMaxKnown = true;
    }

    private PawArray array;
    private String full;
    private ColumnwiseBlock block;
    private int nsub;
    private int size;
    private int elem;
    int offset;
    private int[] dimension;
    private int column;
    private int fixed;
    ColumnwiseTupleColumn index;
    private double min;
    private double max;
    private boolean minMaxKnown = false;
    boolean isMapped = false;
    private static final Class[] classes = {null,Double.TYPE,Integer.TYPE, Integer.TYPE, Boolean.TYPE, String.class};
    private static final Class[] arrayClasses = {null,hep.io.hbook.PawDoubleArray.class,hep.io.hbook.PawIntegerArray.class, hep.io.hbook.PawIntegerArray.class, hep.io.hbook.PawBooleanArray.class, hep.io.hbook.PawStringArray.class};
    
    private class Array implements PawArray {
        public int getNDimensions() {
            return nsub;
        }
        public int getDimension(int x) {
            int n = dimension[x];
            if (n < 0) n = index.getInt();
            return n;
        }
        int calcOffset(int i) {
            int[] ii = {i};
            return calcOffset(ii);
        }
        int calcOffset(int i, int j) {
            int[] ii = {i,j};
            return calcOffset(ii);
        }
        int calcOffset(int i, int j, int k) {
            int[] ii = {i,j,k};
            return calcOffset(ii);
        }
        int calcOffset(int[] i) {
            if (nsub != i.length) throw new RuntimeException("Wrong number of subscripts for PawArray: "+getName());
            int off = offset;
            int siz = size;
            for (int j=0; j<nsub; j++) {
                int index = i[j];
                int d = getDimension(j);
                if (index < 1 || index > d) throw new RuntimeException("PawArray index out of range: "+getName()+" index="+index);
                off += (index-1)*siz;
                siz *= d;
            }
            return off;
        }
        int calcSize() {
            int siz = 1;
            for (int j=0; j<nsub; j++) {
                siz *= getDimension(j);
            }
            return siz;
        }
    }
    private class DoubleArray extends Array implements PawDoubleArray {
        public double getDouble(int i) {
            if (!isMapped) block.map(ColumnwiseTupleColumn.this);
            return Hbook.getCWDataDouble(block.getBuffer(),calcOffset(i),size);
        }
        public double getDouble(int i, int j) {
            if (!isMapped) block.map(ColumnwiseTupleColumn.this);
            return Hbook.getCWDataDouble(block.getBuffer(),calcOffset(i,j),size);
        }
        public double getDouble(int i, int j, int k) {
            if (!isMapped) block.map(ColumnwiseTupleColumn.this);
            return Hbook.getCWDataDouble(block.getBuffer(),calcOffset(i,j,k),size);
        }
        public double getDouble(int[] i) {
            if (!isMapped) block.map(ColumnwiseTupleColumn.this);
            return Hbook.getCWDataDouble(block.getBuffer(),calcOffset(i),size);
        }
        public double[] getAsJavaArray() {
            double[] result = new double[calcSize()];
            if (!isMapped) block.map(ColumnwiseTupleColumn.this);
            
            int off = offset;
            for (int i=0; i<result.length; i++) {
                result[i] =  Hbook.getCWDataDouble(block.getBuffer(),off,size);
                off += size;
            }
            
            return result;
        }
    }
    private class IntegerArray extends Array implements PawIntegerArray {
        public int getInt(int i) {
            if (!isMapped) block.map(ColumnwiseTupleColumn.this);
            return Hbook.getCWDataInt(block.getBuffer(),calcOffset(i),size);
        }
        public int getInt(int i, int j) {
            if (!isMapped) block.map(ColumnwiseTupleColumn.this);
            return Hbook.getCWDataInt(block.getBuffer(),calcOffset(i,j),size);
        }
        public int getInt(int i, int j, int k) {
            if (!isMapped) block.map(ColumnwiseTupleColumn.this);
            return Hbook.getCWDataInt(block.getBuffer(),calcOffset(i,j,k),size);
        }
        public int getInt(int[] i) {
            if (!isMapped) block.map(ColumnwiseTupleColumn.this);
            return Hbook.getCWDataInt(block.getBuffer(),calcOffset(i),size);
        }
        public int[] getAsJavaArray() {
            int[] result = new int[calcSize()];
            if (!isMapped) block.map(ColumnwiseTupleColumn.this);
            
            int off = offset;
            for (int i=0; i<result.length; i++) {
                result[i] =  Hbook.getCWDataInt(block.getBuffer(),off,size);
                off += size;
            }
            
            return result;
        }
    }
    private class StringArray extends Array implements PawStringArray {
        public String getString(int i) {
            if (!isMapped) block.map(ColumnwiseTupleColumn.this);
            return Hbook.getCWDataString(block.getBuffer(),calcOffset(i),size);
        }
        public String getString(int i, int j) {
            if (!isMapped) block.map(ColumnwiseTupleColumn.this);
            return Hbook.getCWDataString(block.getBuffer(),calcOffset(i,j),size);
        }
        public String getString(int i, int j, int k) {
            if (!isMapped) block.map(ColumnwiseTupleColumn.this);
            return Hbook.getCWDataString(block.getBuffer(),calcOffset(i,j,k),size);
        }
        public String getString(int[] i) {
            if (!isMapped) block.map(ColumnwiseTupleColumn.this);
            return Hbook.getCWDataString(block.getBuffer(),calcOffset(i),size);
        }
        public String[] getAsJavaArray() {
            String[] result = new String[calcSize()];
            if (!isMapped) block.map(ColumnwiseTupleColumn.this);
            
            int off = offset;
            for (int i=0; i<result.length; i++) {
                result[i] =  Hbook.getCWDataString(block.getBuffer(),off,size);
                off += size;
            }
            
            return result;
        }
    }
    private class BooleanArray extends Array implements PawBooleanArray {
        public boolean getBoolean(int i) {
            if (!isMapped) block.map(ColumnwiseTupleColumn.this);
            return Hbook.getCWDataBoolean(block.getBuffer(),calcOffset(i),size);
        }
        public boolean getBoolean(int i, int j) {
            if (!isMapped) block.map(ColumnwiseTupleColumn.this);
            return Hbook.getCWDataBoolean(block.getBuffer(),calcOffset(i,j),size);
        }
        public boolean getBoolean(int i, int j, int k) {
            if (!isMapped) block.map(ColumnwiseTupleColumn.this);
            return Hbook.getCWDataBoolean(block.getBuffer(),calcOffset(i,j,k),size);
        }
        public boolean getBoolean(int[] i) {
            if (!isMapped) block.map(ColumnwiseTupleColumn.this);
            return Hbook.getCWDataBoolean(block.getBuffer(),calcOffset(i),size);
        }
        public boolean[] getAsJavaArray() {
            boolean[] result = new boolean[calcSize()];
            if (!isMapped) block.map(ColumnwiseTupleColumn.this);
            
            int off = offset;
            for (int i=0; i<result.length; i++) {
                result[i] =  Hbook.getCWDataBoolean(block.getBuffer(),off,size);
                off += size;
            }
            
            return result;
        }
    }
}
