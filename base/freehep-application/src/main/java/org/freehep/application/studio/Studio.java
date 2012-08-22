package org.freehep.application.studio;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.freehep.application.ApplicationEvent;
import org.freehep.application.mdi.InternalFramePageManager;
import org.freehep.application.mdi.MDIApplication;
import org.freehep.application.mdi.PageContext;
import org.freehep.application.mdi.PageManager;
import org.freehep.application.mdi.TabbedPageManager;
import org.freehep.util.FreeHEPLookup;
import org.freehep.util.commandline.CommandLine;
import org.freehep.xml.util.ClassPathEntityResolver;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.openide.util.Lookup;

/**
 *
 * @author tonyj
 * @version $Id: Studio.java 8584 2006-08-10 23:06:37Z duns $
 */
public class Studio extends MDIApplication
{
   private FreeHEPLookup lookup;
   private List loadedPlugins = new ArrayList();
   private EventSender sender = new EventSender();
   private boolean debugExtensions = System.getProperty("debugExtensions") != null;
   private SAXBuilder builder;
   private ExtensionClassLoader extensionLoader;
   public static final String LOADDIR = "loaddir";
   private boolean isApplicationVisible = false;
   private boolean isApplicationInitialized = false;
   private boolean atLeastOnePluginFailedToLoad = false;

   protected Studio(String name)
   {
      super(name);
      // For the moment at least we will use JDOM for parsing the plugin.xml files
      builder = new SAXBuilder(true);
      builder.setEntityResolver(new ClassPathEntityResolver("plugin.dtd",Studio.class));
      
      getLookup().add(new TabbedPageManager(),"Tabbed Panes");
      getLookup().add(new InternalFramePageManager(),"Internal Frames");
   }
   private Studio()
   {
      this("Studio");
   }
   protected FreeHEPLookup createLookup()
   {
      return FreeHEPLookup.instance();
   }
   public EventSender getEventSender()
   {
      return sender;
   }
   public FreeHEPLookup getLookup()
   {
      if (lookup == null) lookup = createLookup();
      return lookup;
   }
   public void stopPlugin(PluginInfo plugin)
   {
      Plugin plug = plugin.getPlugin();
      if (plug == null || !plug.canBeShutDown()) throw new IllegalArgumentException("Plugin can not be shutdown");

      plug.stop();
      plugin.setPlugin(null);
   }
   public void startPlugin(PluginInfo plugin) throws Throwable
   {
      Plugin plug = initializePlugin(plugin,extensionLoader);
      revalidate();
   }
   private Plugin initializePlugin(PluginInfo plugin, ClassLoader loader) throws Throwable
   {
      try
      {
         getAppProperties().putAll(plugin.getProperties());
         Class c = loader.loadClass(plugin.getMainClass());
         Plugin plug = (Plugin) c.newInstance();
         plug.setContext(this);
         plugin.setPlugin(plug);

         if (isApplicationInitialized) plug.postInit();
         if (isApplicationVisible) plug.applicationVisible();   
         plugin.setErrorStatus(null);
         return plug;
      }
      catch (Throwable t)
      {
         plugin.setErrorStatus(t);
         throw t;
      }
   }
   /**
    * Return the list of installed plugins.
    * Each element in the list will be an instance of PluginInfo
    * @see PluginInfo
    */
   public List getPlugins()
   {
      return loadedPlugins;
   }
   public static void main(String[] args)
   {
      new Studio().createFrame(args).setVisible(true);
   }
   protected CommandLine createCommandLine()
   {
      CommandLine cl = super.createCommandLine();
      // register standard options
      cl.addOption("extdir",null,"directory","Sets the directory to scan for plugins");
      return cl;
   }
   protected void init()
   {
      super.init();
      setStatusMessage("Loading extensions...");
      loadExtensions();
      // Now load the real page manager
      setStatusMessage("Setting page manager...");
      setPageManager(createRealPageManager());
      if (atLeastOnePluginFailedToLoad) error("At least one plugin failed to load, see Plugin Manager for details");
   }
   protected PageManager createRealPageManager()
   {
      String name = getUserProperties().getProperty("pageManagerName","Tabbed Panes");
      Lookup.Template template = new Lookup.Template(PageManager.class,name,null);
      Lookup.Result result = getLookup().lookup(template);
      if (!result.allInstances().isEmpty())
      {
         return (PageManager) result.allInstances().iterator().next();
      }
      else 
      {
         // Previously we used the class name as pageManager, so this is just for
         // backward compatibility.
         try
         {
            return super.createPageManager();
         }
         catch (Throwable t)
         {
            // Last chance, use whatever page manager we can find.
            PageManager pm = (PageManager) getLookup().lookup(PageManager.class);
            if (pm != null) return pm;
            return new TabbedPageManager();
         }
      }
   }
   private void loadExtensions()
   {
      Map extensionClasspath = new TreeMap();
      Set plugins = new LinkedHashSet();
      
      // We look for extensions:
      //    a) In the directory specified by the org.freehep.application.studio.user.extensions property
      //    b) In the directory specified by the org.freehep.application.studio.group.extensions property
      //    c) In the directory specified by the org.freehep.application.studio.system.extensions property
      // The following defaults apply if the property is not specified
      //    a) {user.home}/.FreeHEPPlugins
      //    b) none
      //    c) {java.home}/.FreeHEPPlugins
      
      // Note, we scan system dirs first, because files/plugins found later replace earlier ones
      String[] extDirs =
      {
         getSystemExtensionsDir(),
         getGroupExtensionsDir(),
         getUserExtensionsDir()
      };
      scanExtensionDirectories(extDirs,extensionClasspath,plugins);
      
      // Create the extension Loader.
      
      URL[] urls = new URL[extensionClasspath.size()];
      extensionClasspath.values().toArray(urls);
      extensionLoader = new ExtensionClassLoader(urls);
      createLookup().setClassLoader(extensionLoader);
      // Make sure the extensionClassLoader is set as the contextClassLoader
      // so that services etc can be looked up in jar files from the extension
      // directory.
      Runnable lola = new Runnable()
      {
         public void run()
         {
            Thread.currentThread().setContextClassLoader(extensionLoader);
         }
      };
      lola.run(); // for the main (this) thread
      SwingUtilities.invokeLater(lola); // for the event dispatch thread
      
      // Now try loading the plugins
      loadPlugins(new ArrayList(plugins),extensionLoader);
   }
   public List buildPluginList(InputStream in, File loadDir) throws IOException
   {
      Properties user = getUserProperties();
      List result = new ArrayList();
      try
      {
         Document doc = builder.build(in);
         Element root = doc.getRootElement();
         for (Iterator iter = root.getChildren().iterator(); iter.hasNext(); )
         {
            Element node = (Element) iter.next();
            PluginInfo plugin = new PluginInfo(node);
            plugin.setLoadDirectory(loadDir);
            plugin.loadUserProperties(user);
            result.add(plugin);
            if (debugExtensions) System.out.println("\t\tPlugin: "+plugin.getName());
         }
      }
      catch (JDOMException x)
      {
         if (debugExtensions) x.printStackTrace();
      }
      finally
      {
         in.close();
      }
      return result;
   }
   public void loadPlugins(List plugins, ClassLoader loader)
   {
      Iterator iter = plugins.iterator();

      while (iter.hasNext())
      {
         PluginInfo plugin = (PluginInfo) iter.next();
         loadedPlugins.add(plugin);
         if (plugin.isLoadAtStart())
         {
            try
            {
               setStatusMessage("Loading "+plugin.getName()+"...");
               if (debugExtensions) System.out.println("Loading plugin: "+plugin.getName());
               long start = System.currentTimeMillis();
               initializePlugin(plugin, loader);
               plugin.setErrorStatus(null);
               long stop = System.currentTimeMillis();
               if (debugExtensions) System.out.println("Done loading in : "+(stop-start)+"ms");
            }
            catch (Throwable t)
            {
               if (debugExtensions) System.err.println("Unable to load plugin "+plugin.getName());
               plugin.setErrorStatus(t);
               atLeastOnePluginFailedToLoad = true;
            }
         }
      }
      // plugins may have added menus etc, so for good measure!
      revalidate();
   }
   private void scanExtensionDirectories(String[] dirs, Map extensionClasspath, Set plugins)
   {
      for (int i=0; i<dirs.length; i++)
      {
         if (dirs[i] == null) continue;
         File extdir = new File(dirs[i]);
         if (debugExtensions) System.out.println("Seaching for extensions in: "+extdir);
         if (extdir.isDirectory()) scanExtensionDirectory(extdir,extensionClasspath,plugins);
      }
   }
   private void scanExtensionDirectory(File extdir, Map extensionClasspath, Set plugins)
   {
      String[] files = extdir.list();
      for (int i=0; i<files.length; i++)
      {
         if (files[i].endsWith(".jar"))
         {
            // Try to open the jar file, and see if it has a plugin manifest
            
            File f = new File(extdir,files[i]);
            if( f.length() > 0)
            {
               try
               {
                  if (debugExtensions) System.out.println("\tFound: "+files[i]);
                  JarFile jarFile = new JarFile(f);
                  JarEntry manifest = jarFile.getJarEntry("PLUGIN-inf/plugins.xml");
                  if (manifest != null )
                  {
                     InputStream in = jarFile.getInputStream(manifest);
                     List newPlugins = buildPluginList(in, extdir);
                     boolean rc = plugins.removeAll(newPlugins); // JAS-274
                     plugins.addAll(newPlugins);
                  }
                  jarFile.close();
                  extensionClasspath.put(f.getName(),f.toURI().toURL());
               }
               catch (IOException x)
               {
                  System.err.println("Extension jar file "+files[i]+" could not be loaded"+x);
               }
            }
            else
            { // Files with length 0 have been flagged for deletion by the plugin manager.
               f.delete();
            }
         }
      }
   }
   public String getUserExtensionsDir()
   {
      String extdir = getCommandLine().getOption("extdir");
      if (extdir != null) return extdir;
      return getAppProperties().getProperty("org.freehep.application.studio.user.extensions","{user.home}/.FreeHEPPlugins");
   }
   public String getGroupExtensionsDir()
   {
      return getAppProperties().getProperty("org.freehep.application.studio.group.extensions");
   }
   public String getSystemExtensionsDir()
   {
      return getAppProperties().getProperty("org.freehep.application.studio.system.extensions","{java.home}/FreeHEPPlugins");
   }
   public ExtensionClassLoader getExtensionLoader()
   {
      return extensionLoader;
   }
   
   protected void fireInitializationComplete(ApplicationEvent event)
   {
      super.fireInitializationComplete(event);
      getEventSender().broadcast(event);
      for (Iterator i = loadedPlugins.iterator(); i.hasNext(); )
      {
         PluginInfo info = (PluginInfo) i.next();
         Plugin plugin = info.getPlugin();
         if (plugin != null) plugin.postInit();
      }
      isApplicationInitialized = true;
   }
   protected void fireApplicationVisible(ApplicationEvent event)
   {
      super.fireApplicationVisible(event);
      getEventSender().broadcast(event);
      for (Iterator i = loadedPlugins.iterator(); i.hasNext(); )
      {
         PluginInfo info = (PluginInfo) i.next();
         Plugin plugin = info.getPlugin();
         if (plugin != null) plugin.applicationVisible();
      }
      isApplicationVisible = true;
   }  
   protected void fireAboutToExit(ApplicationEvent event)
   {
      for (Iterator i = loadedPlugins.iterator(); i.hasNext(); )
      {
         PluginInfo info = (PluginInfo) i.next();
         Properties user = getUserProperties();
         info.saveUserProperties(user);
      }
      super.fireAboutToExit(event);
      getEventSender().broadcast(event);
   }
   
   protected PageManager createPageManager()
   {
      // We initially create a dummy page manager, so we can delay creating the 
      // real page manager until after the plugins have been loaded (so that 
      // a plugin can register a plugin manager)
      return new DummyPageManager();
   }
   private static class DummyPageManager extends PageManager
   {
      private JPanel panel = new JPanel();
      protected Component getEmbodiment()
      {
         return panel;
      }
      
      protected void iconChanged(PageContext page)
      {
      }
      
      protected void show(PageContext page)
      {
      }
      
      protected void titleChanged(PageContext page)
      {
      }  
   }
}