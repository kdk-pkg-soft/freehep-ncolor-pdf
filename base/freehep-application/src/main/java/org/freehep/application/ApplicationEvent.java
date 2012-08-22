/*
 * ApplicationEvent.java
 *
 * Created on April 5, 2001, 12:12 PM
 */

package org.freehep.application;
import java.util.EventObject;

/**
 * An event fired by the application at various times.
 * Can be used to allow other components to do any cleanup, including saving user preferences
 * into the user properties.
 * @author  Tony Johnson (tonyj@slac.stanford.edu)
 * @version $Id: ApplicationEvent.java 8584 2006-08-10 23:06:37Z duns $
 */
public class ApplicationEvent extends EventObject
{
   public static int INITIALIZATION_COMPLETE = 0;
   public static int APPLICATION_EXITING = 1;
   public static int APPLICATION_VISIBLE = 2;
   /** Creates new ApplicationEvent */
   ApplicationEvent(Application app, int id)
   {
      super(app);
      this.id = id;
   }
   public Application getApplication()
   {
      return (Application) getSource();
   }
   public int getID()
   {
      return id;
   }
   private int id;
}
