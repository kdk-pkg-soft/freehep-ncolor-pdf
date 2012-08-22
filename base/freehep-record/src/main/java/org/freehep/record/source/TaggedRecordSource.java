package org.freehep.record.source;

import java.util.List;

/** 
 * A record source which allows access to records by record tags. 
 * @version $Id: TaggedRecordSource.java 8584 2006-08-10 23:06:37Z duns $
 */
public interface TaggedRecordSource extends RandomAccessRecordSource
{
   /** Access a record identified by a RecordTag
    * @param tag The tag to access
    * @throws NoSuchRecordException If the specified record does not exist
    */   
   void goToRecord(RecordTag tag) throws NoSuchRecordException;
   /** Get the RecordTag for the current record.
    * @return The current records tag, or <CODE>null</CODE> if there is no current record.
    */   
   RecordTag currentTag();
   /** Access a list of all the tags in this record source. Not all
    * TaggedRecordSource's will support this method, so it may return <CODE>null</CODE>.
    * @return A list of RecordTags, or <CODE>null</CODE>.
    */   
   List tags();
}