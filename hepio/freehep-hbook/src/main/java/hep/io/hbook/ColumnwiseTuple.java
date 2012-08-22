package hep.io.hbook;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/** Represents a column-wise tuple. 
 * The children of a RowwiseTuple will be instances of ColumnwiseTupleColumn
 * @see ColumnwiseTupleColumn
 */
public class ColumnwiseTuple extends Tuple {
    ColumnwiseTuple(CompositeHbookObject parent, String name, int length, int id, int idx) {
        super(parent,name,CWTUPLE,length);
        this.id = id;
        this.idx = idx;
    }

    int getID() {
        return id;
    }
    /** The id for this tuple */    
    public int id()
    {
       return idx;
    }

    ColumnwiseBlock getBlock(String name) {
        ColumnwiseBlock block = (ColumnwiseBlock) blocks.get(name);
        if (block != null) return block;
        
        block = new ColumnwiseBlock(id,name,this);
        blocks.put(name,block);
        return block;
    }
    public String toString() {
        String result = "CW Tuple: " + idx + " " +getName() +" ("+nChildren()+" columns "+getRows()+" rows)";
        return result;
    }
    void close() {
        super.close();
        Enumeration e = blocks.elements();
        while (e.hasMoreElements()) {
            ColumnwiseBlock b = (ColumnwiseBlock) e.nextElement();
            b.close();
        }
        Hbook.delete(id);
    }
    public void setCurrentRow(int row)
    {
       super.setCurrentRow(row);
       if (row == 1) clearMap();
       else if (mapCount > 0) Hbook.setCWEvent(id,ievent);
    }
    private void clearMap()
    {
        Enumeration e = blocks.elements();
        while (e.hasMoreElements()) {
           ColumnwiseBlock b = (ColumnwiseBlock) e.nextElement();
           b.clearMap();
        }
        mapCount = 0;
    }
    
    private int id;
    private int idx;
    private Hashtable blocks = new Hashtable();
    int mapCount = 0;
}
class ColumnwiseBlock {
    ColumnwiseBlock(int id, String name, ColumnwiseTuple owner) {
        this.name = name;
        this.id = id;
        this.owner = owner;
    }
    int allocSpace(ColumnwiseTupleColumn field, int space) {
        children.addElement(field);
        
        int oldSize = size;
        size += space;
        return oldSize;
    }
    void clearMap() {
        Enumeration e = children.elements();
        while (e.hasMoreElements()) {
            ColumnwiseTupleColumn field = (ColumnwiseTupleColumn) e.nextElement();
            field.isMapped = false;
        }
        
        if (buffer != 0) Hbook.CWClearMap(id, name,buffer);
    }
    void map(ColumnwiseTupleColumn field) {
        if (!field.isMapped)
        {
           if (buffer == 0) getBuffer();
           ColumnwiseTupleColumn index = field.index;
           if (index != null && !index.isMapped) {
               int offset = index.offset;
               String setName = "$SET:"+index.getName();
               Hbook.CWMap(id,name,buffer,setName,offset,index.type);
               index.isMapped = true;
           }
           int offset = field.offset;
           String setName = "$SET:"+field.getName();
           Hbook.CWMap(id, name,buffer,setName,offset,field.type);
           // Temporary work around for bug (CWMap sets event back to 1!)
           // A better fix would be to pass the event # into CWMap
           Hbook.setCWEvent(id,owner.ievent);
           field.isMapped = true;
           owner.mapCount++;
        }
    }
    long getBuffer() {
        if (buffer == 0) {
            buffer = Hbook.allocBuffer(size);
            if (owner.mapCount == 0) Hbook.CWClearMap(id, name,buffer);
        }
        return buffer;
    }
    public void close() {
        if (buffer != 0) Hbook.freeBuffer(buffer);
    }
    private Vector children = new Vector();
    private int id;
    private String name;
    private long buffer = 0; // Allocated in native interface
    private int size = 0;
    private ColumnwiseTuple owner;
}
