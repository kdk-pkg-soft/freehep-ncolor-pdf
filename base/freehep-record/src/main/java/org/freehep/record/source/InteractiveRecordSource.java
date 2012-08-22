package org.freehep.record.source;

/**
 * Some methods useful when a record source is used in an interactive environment.
 * @version $Id: InteractiveRecordSource.java 8584 2006-08-10 23:06:37Z duns $
 */
public interface InteractiveRecordSource
{
   /** Test if there are more records available. Note that not all record sources know
    * how many records are available, so the fact that hasNext returns true should be
    * taken to mean that there <I>may</I> be more records available. Calling next()
    * may still result in a NoSuchRecordException even if hasNext() returns true.
    * @return <code>true</code> if there are (may be) more records available.
    */   
   boolean hasNext();
   /** Test if a previous record is available. 
    * If the record source does not allow moving backwards this will always return false.
    * @return true if a previous record is available.
    */   
   boolean hasPrevious();
   
   /** Steps back to the previous record.
    * @throws NoSuchRecordException If no previous record is available.
    */   
   void previous() throws NoSuchRecordException;
   
   /** Skip a certain number of records. There is no gaurantee that this is more
    * efficient that reading all the intervening records.
    * @throws NoSuchRecordException If there are not enough records to skip
    * @param index The number of records to skip. Must be >= 0.
    */   
   void skip(int index) throws NoSuchRecordException;
}
