/*
 * EventSender.java
 *
 * Created on September 20, 2002, 5:07 PM
 */

package org.freehep.application.studio;
import java.util.EventObject;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author  tonyj
 */
public class EventSender
{
   EventSender()
   {
   }
   public void broadcast(EventObject event)
   {
      //FIXME: needs to be much more efficient
      for (Iterator i = listeners.iterator(); i.hasNext(); )
      {
         ListenerEntry l = (ListenerEntry) i.next();
         if (l.getEntryClass().isAssignableFrom(event.getClass()))
         {
            l.getListener().handleEvent(event);
         }
      }
   }
   public boolean hasListeners(Class c)
   {
      // FIXME:
      return true;
   }
   public void addEventListener(StudioListener l, Class c)
   {
      listeners.add(new ListenerEntry(l,c));
   }
   public void removeEventListener(StudioListener l, Class c)
   {
      listeners.remove(new ListenerEntry(l,c));
   }
   private Set listeners = new HashSet();
   private class ListenerEntry
   {
      private StudioListener l;
      private Class c;
      ListenerEntry(StudioListener l, Class c)
      {
         this.l = l;
         this.c = c;
      }
      public boolean equals(Object o)
      {
         if (o instanceof ListenerEntry)
         {
            ListenerEntry that = (ListenerEntry) o;
            return this.l == that.l && this.c == that.c; 
         }
         else return false;
      }
      public int hashCode()
      {
         return l.hashCode() + c.hashCode();
      }
      Class getEntryClass()
      {
         return c;
      }
      StudioListener getListener()
      {
         return l;
      }
   }
}