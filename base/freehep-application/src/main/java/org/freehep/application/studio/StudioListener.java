package org.freehep.application.studio;

import java.util.EventListener;
import java.util.EventObject;

/**
 *
 * @author tonyj
 */
public interface StudioListener extends EventListener
{
   void handleEvent(EventObject event);
}
