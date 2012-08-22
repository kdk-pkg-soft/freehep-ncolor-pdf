package org.freehep.record.source;

/** 
 * A tag that identifies records in a TaggedRecordSet. 
 * @version $Id: RecordTag.java 8584 2006-08-10 23:06:37Z duns $
 */
public interface RecordTag
{
   /** Obtain a user readable name for the record corresponding to this tag.
    * @return The human readable name.
    */   
   String humanReadableName();
}
