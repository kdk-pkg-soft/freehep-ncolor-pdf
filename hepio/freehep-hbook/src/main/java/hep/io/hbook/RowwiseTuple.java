package hep.io.hbook;

/** Represents a row-wise tuple read from an hbook file.
 * The children of a RowwiseTuple will be instances of RowwiseTupleColumn
 * @see RowwiseTupleColumn
 */
public class RowwiseTuple extends Tuple {
    RowwiseTuple(CompositeHbookObject parent, String name, int length, int id, int idx) {
        super(parent,name,RWTUPLE,length);
        this.id = id;
        this.idx = idx;
    }
    int getID() {
        return id;
    }
    public String toString() {
        String result = "RW Tuple: " + idx + " " +getName() +" ("+nChildren()+" columns "+getRows()+" rows)";
        return result;
    }
    void close() {
        super.close();
        if (buffer != 0) Hbook.freeBuffer(buffer);
        Hbook.delete(id);
    }
    /** The id for this tuple */    
    public int id()
    {
       return idx;
    }
    long getBuffer()
    {
        if (buffer == 0)
        { 
           buffer = Hbook.allocBuffer(4*nChildren());
           Hbook.setRWEvent(id,ievent,buffer);
        }
        return buffer;
    }
    public void setCurrentRow(int row)
    {
       super.setCurrentRow(row);
       if (buffer != 0) Hbook.setRWEvent(id,ievent,buffer);
    }
    private int id;
    private int idx;
    long buffer = 0;
}