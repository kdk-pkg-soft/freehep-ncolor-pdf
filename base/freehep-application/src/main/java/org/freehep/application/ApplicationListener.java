/*
 * ApplicationListener.java
 *
 * Created on April 5, 2001, 12:15 PM
 */

package org.freehep.application;
import java.util.EventListener;

/**
 * Listen for ApplicationEvents
 * @author  Tony Johnson (tonyj@slac.stanford.edu)
 * @version $Id: ApplicationListener.java 8584 2006-08-10 23:06:37Z duns $
 */
public interface ApplicationListener extends EventListener
{
   void initializationComplete(ApplicationEvent e);
   //TODO: Maybe add this method back in at some future date (breaks backwards compatibility)
   //void applicationVisible(ApplicationEvent e);
   void aboutToExit(ApplicationEvent e);
}
