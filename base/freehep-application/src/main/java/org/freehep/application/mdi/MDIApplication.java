package org.freehep.application.mdi;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import org.freehep.application.Application;
import org.freehep.application.PropertyUtilities;
import org.freehep.util.commanddispatcher.BooleanCommandState;
import org.freehep.util.commanddispatcher.CommandProcessor;

/**
 * Extends Application to provide MDI facilities.
 * An MDI application controls three types of sub-windows
 * <ul>
 * <li>Pages
 * <li>Consoles
 * <li>Controls
 * </ul>
 * There is one PageManager for each type of window. Different PageManagers
 * can be installed dynamically.
 *
 * @author  Tony Johnson (tonyj@slac.stanford.edu)
 * @version $Id: MDIApplication.java 8584 2006-08-10 23:06:37Z duns $
 */
public class MDIApplication extends Application
{
    /** Creates new MDIApplication */
    public MDIApplication(String appName)
    {
        super(appName);

        // By default only the page area is visible
        add(getPageManager().getEmbodiment());
    }
    protected void loadDefaultProperties(Properties app) throws IOException
    {
       super.loadDefaultProperties(app);

       InputStream in = MDIApplication.class.getResourceAsStream("MDIDefault.properties");
       app.load(in);
       in.close();
    }
    protected void saveUserProperties()
    {
        Properties user = getUserProperties();
        if (consoleSplit != null) PropertyUtilities.setInteger(user,"consoleSize",consoleSplit.getHeight() - consoleSplit.getDividerLocation());
        if (controlSplit != null) PropertyUtilities.setInteger(user,"controlSize",controlSplit.getDividerLocation());
        if (mdiToolBarHolder != null) mdiToolBarHolder.save(user);
        super.saveUserProperties();
    }
    public static final int TOOLBAR_DEFAULT = 0;
    public static final int TOOLBAR_INVISIBLE = 1;
    public static final int TOOLBAR_VISIBLE = 2;
    public static final int TOOLBAR_AUTO = 3;
    public static final int TOOLBAR_PROGRAM = 4;
    /**
     * Adds a new toolbar to the toolbar area.
     * You can attach any number of toolbars to an MDIApplication. Normally the
     * user gets to choose which toolbars are visible or not, but this is controlled
     * by the toolbar mode, which can be one of:
     * <ul>
     * <li>TOOLBAR_INVISIBLE - Toolbar is not shown
     * <li>TOOLBAR_VISIBLE - Toolbar is shown
     * <li>TOOLBAR_AUTO - Toolbar is shown only when at least one button is enabled
     * <li>TOOLBAR_PROGRAM - Toolbar visibility is controlled by the program, not the user (not recommended)
     * </ul>
     * The user can override the default toolBar more, unless it is set to TOOLBAR_PROGRAM. If mode is omitted, or
     * set to TOOLBAR_DEFAULT, it is controlled by the application properties.
     *
     * @param toolBar The JToolBar to add
     * @param name The name of the toolbar, used for user menus and the app/user properties file
     * @param mode The mode for this toolbar
     */
    public void addToolBar(JToolBar toolBar, String name, int mode)
    {
       if (mdiToolBarHolder == null)
       {
          mdiToolBarHolder = new ToolBarHolder();
          getToolBarHolder().add(mdiToolBarHolder,BorderLayout.NORTH);
       }
       if (mode == TOOLBAR_DEFAULT)
       {
          mode = PropertyUtilities.getInteger(getUserProperties(),"ToolBar."+name,mode);
       }
       mdiToolBarHolder.add(toolBar,name,mode);
    }
    public void addToolBar(JToolBar toolBar, String name)
    {
       addToolBar(toolBar, name, TOOLBAR_DEFAULT);
    }
    public void removeToolBar(JToolBar toolBar)
    {
       mdiToolBarHolder.remove(toolBar);
    }
    public PageManager getPageManager()
    {
        if (pageManager == null) setPageManager(createPageManager());
        return pageManager;
    }
    public void setPageManager(PageManager manager)
    {
        if (manager == pageManager) return;
        Component oldEmbodiment = null;
        if (pageManager != null)
        {
           oldEmbodiment = pageManager.getEmbodiment();
        }
        switchPageManager(pageManager,manager);
        
        // Now we need to switch the embodiments
        if (pageManager != null)
        {
            Container parent = oldEmbodiment.getParent();
            int index = 0;
            for (int i=0; i<parent.getComponentCount(); i++)
                if (parent.getComponent(i) == oldEmbodiment) { index = i; break; }
            parent.remove(index);
            Component page = manager.getEmbodiment();
            updateComponentTreeUI(page); // In case UI has changed 
            parent.add(page,index);
            parent.validate();
            getCommandProcessor().setChanged();
        }
        pageManager = manager;
    }
    public PageManager getControlManager()
    {
        if (controlManager == null) setControlManager(createControlManager());
        return controlManager;
    }
    public void setControlManager(PageManager manager)
    {
        if (manager == controlManager) return;
        switchPageManager(controlManager,manager);
        controlManager = manager;
    }
    public PageManager getConsoleManager()
    {
        if (consoleManager == null) setConsoleManager(createConsoleManager());
        return consoleManager;
    }
    public void setConsoleManager(PageManager manager)
    {
        if (manager == consoleManager) return;
        switchPageManager(consoleManager,manager);
        consoleManager = manager;
    }
    protected PageManager createPageManager()
    {
        return createManager("pageManager");
    }
    protected PageManager createControlManager()
    {
        return createManager("controlManager");
    }
    protected PageManager createConsoleManager()
    {
        PageManager p = createManager("consoleManager");
        p.setPageManagerType("Console");
        return p;
    }
    protected PageManager createManager(String type)
    {
        try
        {
           ClassLoader loader = Thread.currentThread().getContextClassLoader();
           if (loader == null) loader = getClass().getClassLoader();
           Class k = loader.loadClass(getUserProperties().getProperty(type));
           return (PageManager) k.newInstance();
        }
        catch (Exception x)
        {
            throw new InitializationException("Could not create PageManager: "+type,x);
        }
    }
    private void switchPageManager(PageManager oldManager, PageManager newManager)
    {
        newManager.setActive(true);
        if (oldManager != null)
        {
            List listeners = oldManager.getPageListenerList();
            oldManager.removeAllPageListeners();
            List l = oldManager.pages();
            for (Iterator i = listeners.iterator(); i.hasNext(); ) newManager.addPageListener((PageListener) i.next()); 
            newManager.init(l,oldManager.getSelectedPage());
            oldManager.setActive(false);
        }
        else newManager.addPageListener(pageListener); 
    }
    
    
    public void setSelectedPageManager(PageManager manager) {
        if (manager == selectedPageManager) return;
        if (selectedPageManager != null)
            getCommandTargetManager().remove(selectedPageManager.getCommandProcessor());           
        getCommandTargetManager().add(manager.getCommandProcessor());        
        selectedPageManager = manager;
    }
    
    public PageManager selectedPageManager() {
        return selectedPageManager;
    }
    
    private class MDIPageListener implements PageListener
    {
        public void pageChanged(PageEvent event)
        {
            int id = event.getID();
            if (id == PageEvent.PAGEOPENED)
            {
                PageManager manager = event.getPageContext().getPageManager();
                if (manager.getPageCount() == 1)
                {
                    if (manager == controlManager) showControl(true);
                    if (manager == consoleManager) showConsole(true);
                }
            }
            else if (id == PageEvent.PAGECLOSED)
            {
                PageManager manager = event.getPageContext().getPageManager();
                if (manager.getPageCount() == 0)
                {
                    if (manager == controlManager) showControl(false);
                    if (manager == consoleManager) showConsole(false);
                }
            }
        }
    }
    private void showControl(boolean show)
    {
        if (show && controlSplit == null)
        {
            Component pages = pageManager.getEmbodiment();
            Container parent = pages.getParent();
            parent.remove(pages);
            Component control = controlManager.getEmbodiment();
            updateComponentTreeUI(control); // In case UI has changed 
            controlSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,control,pages);
            int pixels = PropertyUtilities.getInteger(getUserProperties(),"controlSize",-1);
            if (pixels > 0) controlSplit.setDividerLocation(pixels);
            parent.add(controlSplit);
            revalidate();
        }
        else if (!show && controlSplit != null)
        {
            PropertyUtilities.setInteger(getUserProperties(),"controlSize",controlSplit.getDividerLocation());
            Component pages = pageManager.getEmbodiment();
            Container parent = controlSplit.getParent();
            parent.remove(controlSplit);
            controlSplit.removeAll();
            controlSplit = null;
            parent.add(pages);
            revalidate();
        }
    }
    private void showConsole(boolean show)
    {
        if (show && consoleSplit == null)
        {
            Component old = getComponent(0);
            final int height = old.getHeight();
            remove(old);
            final int pixels = PropertyUtilities.getInteger(getUserProperties(),"consoleSize",-1);

            Component console = consoleManager.getEmbodiment();
            updateComponentTreeUI(console); // In case UI has changed 
            consoleSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT,old,console)
            {
               // If the splitpane is not yet layed out, its height will be 0
               // in this case we need to postpone setting the divider location
               // until later.
               int consoleSize = height > 0 ? -1 : pixels;
               public void doLayout()
               {
                  if (consoleSize > 0)
                  {
                     int height = getHeight();
                     setDividerLocation(height - consoleSize);
                     consoleSize = -1;
                  }
                  super.doLayout();
               }               
            };
            consoleSplit.setResizeWeight(1.0);
            if (pixels > 0 && height>0) consoleSplit.setDividerLocation(height - pixels);

            add(consoleSplit);
            revalidate();
        }
        else if (!show && consoleSplit != null)
        {
            int consoleSize = consoleSplit.getHeight() - consoleSplit.getDividerLocation();
            PropertyUtilities.setInteger(getUserProperties(),"consoleSize",consoleSize);
            Component old = consoleSplit.getTopComponent();
            remove(consoleSplit);
            consoleSplit.removeAll();
            consoleSplit = null;
            add(old);
            revalidate();
        }
    }
    protected CommandProcessor createCommandProcessor()
    {
        return new MDICommandProcessor();
    }
    
    protected class MDICommandProcessor extends ApplicationCommandProcessor
    {
        public void onTabbedPanes(boolean state)
        {
            if (state) 
            {
                setPageManager(new TabbedPageManager());
                getUserProperties().setProperty("pageManager",getPageManager().getClass().getName());
            }
        }
        public void enableTabbedPanes(BooleanCommandState state)
        {
            state.setEnabled(true);
            state.setSelected(getPageManager() instanceof TabbedPageManager);
        }
        public void onInternalFrames(boolean state)
        {
            if (state) 
            {
                setPageManager(new InternalFramePageManager());
                getUserProperties().setProperty("pageManager",getPageManager().getClass().getName());
            }
        }
        public void enableInternalFrames(BooleanCommandState state)
        {
            state.setEnabled(true);
            state.setSelected(getPageManager() instanceof InternalFramePageManager);
        }
    }
    private PageListener pageListener = new MDIPageListener();
    private PageManager pageManager;
    private PageManager controlManager;
    private PageManager consoleManager;
    private JSplitPane consoleSplit;
    private JSplitPane controlSplit;
    private ToolBarHolder mdiToolBarHolder;
    private PageManager selectedPageManager;
}