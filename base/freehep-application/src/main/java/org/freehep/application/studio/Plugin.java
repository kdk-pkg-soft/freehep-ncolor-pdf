package org.freehep.application.studio;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.freehep.xml.menus.XMLMenuBuilder;


/**
 * A base class which Plugins must extend
 */
public abstract class Plugin
{
   /**
    * Called to initialize the plugin. Note that at this time
    * other plugins may not have been loaded, and the GUI may not
    * yet be visible.
    */
   protected void init() throws Throwable
   {

   }
   /**
    * Called after all plugins have been loaded, but before the GUI has become visible
    */
   protected void postInit()
   {

   }
   /**
    * Called after all plugins have been loaded, and the GUI has become visible
    */
   protected void applicationVisible()
   {
      
   }
   /**
    * Test if the plugin can be shutdown. 
    * The default implementation always returns false, override as necessary.
    */
   public boolean canBeShutDown()
   {
      return false;
   }
   /**
    * Called to shutdown the plugin (if supported)
    */
   protected void shutdown()
   {
   }
   public Studio getApplication()
   {
      return app;
   }
   protected void addMenu(JMenuItem item, long location)
   {
      JMenuBar bar = app.getMenuBar();
      String loc = String.valueOf(location);
      addMenu(bar,loc,item);
   }
   private void addMenu(Container parent, String loc, JMenuItem item)
   {
      int l = loc.length() % 3;
      if (l == 0) l = 3;
      int ll = Integer.parseInt(loc.substring(0,l));

      Component[] c = parent instanceof JMenu ? ((JMenu) parent).getPopupMenu().getComponents() : parent.getComponents();
      for (int i=0; i<c.length; i++)
      {
         Component comp = c[i];
         if (comp instanceof JComponent)
         {
            JComponent child = (JComponent) comp;
            Object location = child.getClientProperty(XMLMenuBuilder.LOCATION_PROPERTY);
            if (!(location instanceof Integer)) continue;
            int locat = ((Integer) location).intValue();
            if (locat == ll)
            {
               String remainder = loc.substring(l);
               if (remainder.length() > 0 && child instanceof Container)
               {
                  addMenu((Container) comp,remainder,item);
                  return;
               }
               else throw new RuntimeException("Invalid location for addMenu");
            }
            else if (locat > ll)
            {
               ((Container) parent).add(item,i);
               item.putClientProperty(XMLMenuBuilder.LOCATION_PROPERTY,new Integer(ll));
               return;
            }
         }
      }
      ((Container) parent).add(item);
      item.putClientProperty(XMLMenuBuilder.LOCATION_PROPERTY,new Integer(ll));
   }
   void setContext(Studio app) throws Throwable
   {
      this.app = app;
      init();
   }
   void stop()
   {
      shutdown();
      app = null;
   }

   private Studio app;
}
