package org.freehep.application.studio;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.jdom.Element;

/**
 * Provides a description of a plugin. 
 * The plugin may or may not be downloaded or started.
 * @author tonyj
 * @version $Id: PluginInfo.java 8584 2006-08-10 23:06:37Z duns $
 */
public class PluginInfo
{
   /**
    * Builds a PluginInfo from a JDOM element
    */
   public PluginInfo(Element node)
   {
      Element info = node.getChild("information");
      name = info.getChildTextNormalize("name");
      author = info.getChildTextNormalize("author");
      String temp = info.getChildTextNormalize("category");
      category = temp==null ? NOCATEGORY : temp.split("\\."); 

      version = info.getChildTextNormalize("version");
      defaultLoadAtStart = loadAtStart = info.getChild("load-at-start") != null;
      mainClass = node.getChild("plugin-desc").getAttributeValue("class");
      List desc = info.getChildren("description");
      for (Iterator i = desc.iterator(); i.hasNext(); )
      {
         Element d = (Element) i.next();
         String type = d.getAttributeValue("type");
         String text = d.getTextNormalize();
         if (type != null && type.equals("short")) title = text;
         else description = text;
      }
      
      Element resources = node.getChild("resources");
      if (resources != null)
      {
         List fileList = resources.getChildren("file");
         if (!fileList.isEmpty()) files = new HashMap();
         for (Iterator i = fileList.iterator(); i.hasNext(); )
         {
            Element f = (Element) i.next();
            String href = f.getAttributeValue("href");
            String location = f.getAttributeValue("location");
            files.put(location, href);
         }
         List propList = resources.getChildren("property");
         if (!propList.isEmpty()) properties = new HashMap();
         for (Iterator i = propList.iterator(); i.hasNext(); )
         {
            Element f = (Element) i.next();
            String name = f.getAttributeValue("name");
            String value = f.getAttributeValue("value");
            properties.put(name, value);
         }  
      }
   }
   public boolean equals(Object o)
   {
      if (o instanceof PluginInfo)
      {
         return name.equals(((PluginInfo) o).name);
      }
      return false;
   }
   public int hashCode()
   {
      return name.hashCode();
   }
   String getMainClass()
   {
      return mainClass;
   }
   public String getName()
   {
      return name;
   }
   public String getAuthor()
   {
      return author;
   }
   public String getVersion()
   {
      return version;
   }
   public String getTitle()
   {
      return title;
   }
   public String getDescription()
   {
      return description;
   }
   public boolean isLoadAtStart()
   {
      return loadAtStart;
   }
   public void setLoadAtStart(boolean loadAtStart)
   {
      this.loadAtStart = loadAtStart;
   }
   public Map getFiles()
   {
      return files == null ? Collections.EMPTY_MAP : files;
   }
   public Map getProperties()
   {
      return properties == null ? Collections.EMPTY_MAP : properties;
   }
   public void setLoadDirectory(File dir)
   {
      this.loadDir = dir;
   }
   public File getLoadDirectory()
   {
      return loadDir;
   }
   void setPlugin(Plugin plugin)
   {
      this.plugin = plugin;
   }
   public Plugin getPlugin()
   {
      return plugin;
   }
   public String[] getCategory()
   {
      return category;
   }
   void loadUserProperties(Properties user)
   {
      String prop = user.getProperty("loadAtStart."+name);
      if (prop != null) loadAtStart = Boolean.valueOf(prop).booleanValue();
   }
   void saveUserProperties(Properties user)
   {
      String key = "loadAtStart."+name;
      if (loadAtStart == defaultLoadAtStart) user.remove(key);
      else user.setProperty(key,String.valueOf(loadAtStart));
   }
   void setErrorStatus(Throwable t)
   {
      errorStatus = t;
   }
   public Throwable getErrorStatus()
   {
      return errorStatus;
   }
   private Throwable errorStatus;
   private Plugin plugin;
   private File loadDir;
   private String name;
   private String author;
   private String version;
   private String mainClass;
   private String title;
   private String description;
   private String[] category;
   private Map files;
   private Map properties;
   private boolean loadAtStart;
   private boolean defaultLoadAtStart;
   private static final String[] NOCATEGORY = new String[0];      
}