package org.freehep.application.mdi;

/**
 * An optional interface that can be implemented by pages managed by a 
 * PageManager. Used by the PageManager to inform pages of changes.
 * @author Tony Johnson (tonyj@slac.stanford.edu)
 * @version $Id: ManagedPage.java 8584 2006-08-10 23:06:37Z duns $
 */
public interface ManagedPage 
{
   /**
    * Called BEFORE the page is closed. Page can veto the close operation
    * by returning false.
    * @return false to veto the page close operation.
    */
   boolean close();
   /**
    * Called after the page is created to set its page context.
    * @param context The PageContext for this page.
    */
   void setPageContext(PageContext context);
   /**
    * Called when this page becomes the current "selected" page
    */
   void pageSelected();
   /**
    * Called when this page is no longer the "selected" page
    */
   void pageDeselected();
   /**
    * Called when this page is iconized
    */
   void pageIconized();
   /**
    * Called when this page is deiconized
    */
   void pageDeiconized();
   /**
    * Called after this page has been closed.
    */
   void pageClosed();
}
