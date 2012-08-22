package org.freehep.record.source;

import java.util.EventListener;

public interface RecordReadyListener extends EventListener
{
   void nextRecordReady(RecordReadyEvent event);
}
