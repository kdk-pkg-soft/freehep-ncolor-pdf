package org.freehep.record.source;

import java.io.IOException;

/**
 * The base interface that all record sources must implement.
 * @version $Id: SequentialRecordSource.java 8584 2006-08-10 23:06:37Z duns $
 */

public interface SequentialRecordSource
{
   /** A value that may be returned by getEstimatedSize */
   public final static int UNKNOWN = -1;
   /** Returns the number of records in this record source, if known.
    * <p>
    * This is allowed to be an approximation (for example the number of events may be
    * known but not the total number of records, or the expected number of records may
    * be known, but the actual number may be different). The primary use of this
    * method is for giving the user feedback on how much of a record source has been
    * read, it should not be used as the limit for a for loop. Use hasNext() instead.
    * @return The approximate number of records, or UNKNOWN.
    */
   long getEstimatedSize();
   /** Get the current record.
    * @return The current record, or <CODE>null</CODE> if no current record.
    */
   Object getCurrentRecord() throws NoSuchRecordException, IOException, EndOfSourceException;
   /**
    * Releases any resources associated with the specified record.
    */
   void releaseRecord(Object record);
   /** Go to the next record. Note that record sources are initially positioned before
    * the first record, so next() must be called once before calling
    * getCurrentRecord().
    */
   void next() throws IOException;

   /** Find out what type of records are returned by this record source. All records
    * returned are gauranteed to be of this type. This method should return
    * Object.class if nothing more is known.
    * @return The class of records returned by this record source.
    */
   Class getRecordClass();
   /**
    * Get the (human readable) name of this record source. This is typically the file
    * name for RecordSources associated with files.
    * @return The record source name.
    */
   String getSourceName();
   /**
    * Repositions the record source before the first record.
    */
   void rewind() throws IOException;
   /**
    * Close the record source and free any associated resources
    */
   void close() throws IOException;
}