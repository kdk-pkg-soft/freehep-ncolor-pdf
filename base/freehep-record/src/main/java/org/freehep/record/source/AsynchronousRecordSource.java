package org.freehep.record.source;

/**
 * A mixin interface that can be implemented by RecordSource's. Typical uses would be
 * for live sources (e.g. sampling data from a running experiment) or reading data over
 * a slow connection.
 * @version $Id: AsynchronousRecordSource.java 8584 2006-08-10 23:06:37Z duns $
 */
public interface AsynchronousRecordSource extends SequentialRecordSource
{
   /**
    * When non blocking is set all methods which would normally block, such as next()
    * previous(), skip(), goToRecord(), will instead return immediately. The caller is
    * then resposnsible to use this interface to check when the requested record is ready
    * before calling getCurrentObject() etc.
    *
    * If non-blocking is not enabled then AsynchronousRecordSources will behave exactly
    * as non-asynchronous sources.
    * @param value <code>true</code> to enable non-blocking mode, <code>false</code> to disable.
    *
    */
   void setNonBlocking(boolean value);
   /** 
    * Test if non-blocking mode is enabled.
    * @return <code>true</code> if non-blocking mode is enabled.
   boolean isNonBlocking();
   /**
    * Returns true is the requested record is available.
    */
   boolean isRecordReady();
   /**
    * This method will block until the requested record is ready
    */
   void waitForRecordReady() throws InterruptedException;
   /**
    * Add a record listener that will be notified when the record is ready
    */
   void addRecordListener(RecordReadyListener l);
   /**
    * Remove a record listener
    */
   void removeRecordListener(RecordReadyListener l);
}
