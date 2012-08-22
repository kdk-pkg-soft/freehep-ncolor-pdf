package org.freehep.record.source;

import java.util.EventObject;

/**
 *
 * @version $Id: RecordReadyEvent.java 8584 2006-08-10 23:06:37Z duns $
 */
public class RecordReadyEvent extends EventObject
{
   RecordReadyEvent(AsynchronousRecordSource source)
   {
      super(source);
   }
}
