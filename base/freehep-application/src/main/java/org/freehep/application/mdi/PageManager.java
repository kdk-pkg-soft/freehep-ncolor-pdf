package org.freehep.application.mdi;
import java.awt.Component;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.event.EventListenerList;
import org.freehep.application.Application;

import org.freehep.util.commanddispatcher.CommandProcessor;
import org.freehep.util.commanddispatcher.CommandState;
import org.freehep.util.commanddispatcher.CommandSourceAdapter;
import org.freehep.util.commanddispatcher.CommandTargetManager;

/**
 * A PageManager manages a set of pages.
 * @author  Tony Johnson (tonyj@slac.stanford.edu)
 * @version $Id: PageManager.java 8584 2006-08-10 23:06:37Z duns $
 */
public abstract class PageManager
{
   public PageContext openPage(Component c, String title, Icon icon)
   {
       return openPage(c, title, icon, null);
   }
   
   public PageContext openPage(Component c, String title, Icon icon, String type)
   {
      PageContext context = new PageContext(c,title,icon, type);
      context.setPageManager(this);
      pages.add(context);
      return context;
   }
   
   /**
    * Called whenever the page manager becomes, or ceases to be, in use.
    * Can be used to allocated and/or free-up resources used by the page manager.
    * @param active <CODE>true</CODE> if the page manager is becoming active, <CODE>false</CODE> if it is ceasing to be active
    */
   protected void setActive(boolean active)
   {
      
   }
   protected void firePageOpened(PageContext context)
   {
      ManagedPage mp = getManagedPage(context.getPage());
      if (mp != null) mp.setPageContext(context);
      firePageEvent(context,PageEvent.PAGEOPENED);
      getCommandProcessor().setChanged();
   }
   ManagedPage getManagedPage(Component c)
   {
      if (c instanceof ManagedPage) return (ManagedPage) c;
      else if (c instanceof JScrollPane)
      {
         Component cc = ((JScrollPane) c).getViewport().getView();
         if (cc instanceof ManagedPage) return (ManagedPage) cc;
      }
      return null;
   }
   public boolean closeAll()
   {
       return closeAll(null);
   }
   
   private boolean closeAll(String type)
   {
      Iterator i = new ArrayList(pages).iterator();
      while (i.hasNext())
      {
          PageContext tmpPage = (PageContext) i.next();
          if ( type == null || tmpPage.type().equals(type) ) 
              if (!close(tmpPage)) return false;
      }
      return true;
   }
   
   private boolean closeOther(PageContext page)
   {
       return closeOther(page,false);
   }
   
   private boolean closeOther(PageContext page, boolean byType)
   {
       Iterator i = new ArrayList(pages).iterator();
       while (i.hasNext())
       {
           PageContext tmpPage = (PageContext) i.next();
           if ( tmpPage != page )
               if ( !byType || tmpPage.type().equals(page.type()) )
                   if (!close(tmpPage) ) return false;
      }
      return true;
   }
   
   public int getPageCount()
   {
      return pages.size();
   }
   
   private int getPageCount(String type)
   {
       if ( type == null )
           return getPageCount();
       int count = 0;
       Iterator i = new ArrayList(pages).iterator();
       while (i.hasNext())
       {
           PageContext tmpPage = (PageContext) i.next();
           if ( tmpPage.type().equals(type) )
               count++;
      }
      return count;
   }

   public PageContext getSelectedPage()
   {
      return selected;
   }
   
   protected abstract void show(PageContext page);
   protected boolean close(PageContext page)
   {
      ManagedPage mp = getManagedPage(page.getPage());
      if (mp != null && !mp.close()) return false;
      pages.remove(page);
      if (page == selected) fireSelectionChanged(null);
      if (mp != null) mp.pageClosed();
      firePageEvent(page,PageEvent.PAGECLOSED);
      getCommandProcessor().setChanged();
      return true;
   }
      
   protected abstract void titleChanged(PageContext page);
   protected abstract void iconChanged(PageContext page);
   
   protected abstract Component getEmbodiment();
   protected CommandProcessor createCommandProcessor()
   {
      return new PageManagerCommandProcessor();
   }
   protected CommandProcessor getCommandProcessor()
   {
      if (commandProcessor == null) commandProcessor = createCommandProcessor();
      return commandProcessor;
   }
   public List pages()
   {
      return pages;
   }
   protected void init(List pages, PageContext selected)
   {
      this.pages = pages;
      this.selected = selected;
      for (Iterator i = pages.iterator(); i.hasNext();)
      {
         PageContext page = (PageContext) i.next();
         page.setPageManager(this);
      }
   }
   protected void fireSelectionChanged(PageContext context)
   {
       if ( selected != context ) {
      if (selected != null)
      {
         ManagedPage mp = getManagedPage(selected.getPage());
         if (mp != null) mp.pageDeselected();
         firePageEvent(selected,PageEvent.PAGEDESELECTED);
      }
      selected = context;
      if (context != null)
      {
         ManagedPage mp = getManagedPage(selected.getPage());
         if (mp != null) mp.pageSelected();
         firePageEvent(selected,PageEvent.PAGESELECTED);
      }
      getCommandProcessor().setChanged();
       }
   }
   protected void firePageEvent(PageContext context, int id)
   {       
      PageEvent event = new PageEvent(context,id);
//      System.out.println("Firing event "+event);
      if (listenerList != null)
      {
         PageListener[] listeners = (PageListener[]) listenerList.getListeners(PageListener.class);
         
         for (int i = 0; i<listeners.length; i++)
         {
            listeners[i].pageChanged(event);
         }
      }
      context.firePageEvent(event,id);
   }
   
   /**
    * Add a page listener to receive notifications of user initiated changes
    *
    * @param listener The PageListener to install
    */
   public void addPageListener(PageListener listener)
   {
      if (listenerList == null) listenerList = new EventListenerList();
      listenerList.add(PageListener.class,listener);
   }
   /**
    * Remove a previously installed PageListener
    *
    * @param listener The PageListener to remove
    */
   public void removePageListener(PageListener listener)
   {
      listenerList.remove(PageListener.class,listener);
   }
   List getPageListenerList()
   {
      if (listenerList == null) return Collections.EMPTY_LIST;
      else return Arrays.asList(listenerList.getListeners(PageListener.class));
   }
   void removeAllPageListeners()
   {
      listenerList = null;
   }

   protected JPopupMenu modifyPopupMenu(JPopupMenu menu,Component source,Point p) {
       
       CommandTargetManager cm = Application.getApplication().getCommandTargetManager();
       ((MDIApplication)Application.getApplication()).setSelectedPageManager(this);

       String pageType = getSelectedPage() != null ? getSelectedPage().type() : null ;
       boolean hasType = pageType != null;

       JComponent closeMenu = new JMenu("Close");
       
       if ( menu.getComponentCount() > 0 )
           menu.addSeparator();
       else
           closeMenu = menu;
       
       JMenuItem close = new JMenuItem(makeTitle("Close",false));
       close.setActionCommand("closePage");
       cm.add(new CommandSourceAdapter(close));
       closeMenu.add(close);

       JMenuItem closeAll = new JMenuItem(makeTitle("Close All", true));
       closeAll.setActionCommand("closeAllPages");
       cm.add(new CommandSourceAdapter(closeAll));
       closeMenu.add(closeAll);
       
       if ( hasType ) {
           JMenuItem closeAllByType = new JMenuItem(makeTitle("Close All "+pageType, true));
           closeAllByType.setActionCommand("closeAllPagesByType");
           cm.add(new CommandSourceAdapter(closeAllByType));
           closeMenu.add(closeAllByType);
       }
       
       JMenuItem closeOther = new JMenuItem(makeTitle("Close Other", true));
       closeOther.setActionCommand("closeOtherPages");
       cm.add(new CommandSourceAdapter(closeOther));
       closeMenu.add(closeOther);

       if ( hasType ) {
           JMenuItem closeOtherByType = new JMenuItem(makeTitle("Close Other "+pageType, true));
           closeOtherByType.setActionCommand("closeOtherPagesByType");
           cm.add(new CommandSourceAdapter(closeOtherByType));
           closeMenu.add(closeOtherByType);
       }

       if ( menu != closeMenu )
           menu.add(closeMenu);
       return menu;
   }
   
   private String makeTitle(String title, boolean plural) {
       if ( pageManagerType() == null )
           return title;
       title = title+" "+pageManagerType();
       if ( plural )
           title += "s";
       return title;
   }
   
   protected String pageManagerType() {
       return pageManagerType;
   }
   
   public void setPageManagerType(String type) {
       pageManagerType = type;
   }
   
   
   public class PageManagerCommandProcessor extends CommandProcessor
   {
       
      public void onCloseAllPages()
      {
         closeAll();
      }
      public void enableCloseAllPages(CommandState state)
      {
         state.setEnabled(getPageCount() > 1);
      }
      public void onClosePage()
      {
         close(getSelectedPage());
      }
      public void enableClosePage(CommandState state)
      {
         state.setEnabled(getSelectedPage() != null);
      }
      public void onCloseOtherPages()
      {
         closeOther(getSelectedPage());
      }
      public void enableCloseOtherPages(CommandState state)
      {
         state.setEnabled(getPageCount() > 1);
      }
      
      public void onCloseAllPagesByType()
      {
         closeAll(getSelectedPage().type());
      }
      public void enableCloseAllPagesByType(CommandState state)
      {
         state.setEnabled(getPageCount(getSelectedPage().type()) > 1);
      }
      public void onCloseOtherPagesByType()
      {
         closeOther(getSelectedPage(), true);
      }
      public void enableCloseOtherPagesByType(CommandState state)
      {
         state.setEnabled(getPageCount(getSelectedPage().type()) > 1);
      }
   }

   private String pageManagerType = null;
   private CommandProcessor commandProcessor;
   protected EventListenerList listenerList;
   private List pages = new ArrayList();
   private PageContext selected;
   
}
