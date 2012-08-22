package hep.io.hbook;

/** A base class for all tuples read from an hbook file. */
public abstract class Tuple extends CompositeHbookObject
{
   int ievent;
   private int length;

   Tuple(CompositeHbookObject parent, String name, int type, int length)
   {
      super(parent, name, type);
      this.length = length;
   }
   /** Get the number of rows in the tuple.
    * @return The number of rows
    */   
   public int getRows()
   {
      return length;
   }
   /** Get the current row.
    * @return The current row, in the range 1 to nRows
    */   
   public int getCurrentRow()
   {
      return ievent;
   }
   /** Set the current row.
    * @param row The new current row (1-nRows)
    */
   public void setCurrentRow(int row)
   {
      if (row<=0 || row>length) throw new ArrayIndexOutOfBoundsException("Illegal row "+row+" it should be between 1 and "+length);
      this.ievent = row;
   }
}
