/*
 * PropertyUtilities.java
 *
 * Created on January 30, 2001, 4:25 PM
 */

package org.freehep.application;
import java.awt.Color;
import java.awt.Rectangle;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.table.AbstractTableModel;

/**
 * A set of static methods for operating on a Properties set
 * @see java.util.Properties
 * @author tonyj
 * @version $Id: PropertyUtilities.java 8584 2006-08-10 23:06:37Z duns $
 */
public abstract class PropertyUtilities
{
   /**
    * @param prop The Properties set
    * @param key the key used to store this property
    * @param def a default in case the property cannot be retrieved
    */
   public static Rectangle getRectangle(Properties prop, final String key, final Rectangle def)
   {
      try
      {
         final Rectangle result = new Rectangle();
         result.x = getInteger(prop,key.concat("-x"));
         result.y = getInteger(prop,key.concat("-y"));
         result.width = getInteger(prop,key.concat("-w"));
         result.height = getInteger(prop,key.concat("-h"));
         return result;
      }
      catch (Exception e)
      {
         return def;
      }
   }
   
   /**
    * @param prop The Properties set
    * @param key the key used to store this property
    * @param rect the value to store
    */
   public static void setRectangle(Properties prop, final String key, final Rectangle rect)
   {
      prop.put(key.concat("-x"), String.valueOf(rect.x));
      prop.put(key.concat("-y"), String.valueOf(rect.y));
      prop.put(key.concat("-w"), String.valueOf(rect.width));
      prop.put(key.concat("-h"), String.valueOf(rect.height));
   }
   
   /**
    * @param prop The Properties set
    * @param key the key used to store this property
    * @param def a default in case the property cannot be retrieved
    */
   public static Color getColor(Properties prop, final String key, final java.awt.Color def)
   {
      try
      {
         return new Color(getInteger(prop,key.concat("-r")), getInteger(prop,key.concat("-g")),
         getInteger(prop,key.concat("-b")));
      }
      catch (Exception e)
      {
         return def;
      }
   }
   
   /**
    * @param prop The Properties set
    * @param key the key used to store this property
    * @param c the value to store
    */
   public static void setColor(Properties prop, final String key, final Color c)
   {
      prop.put(key.concat("-r"), String.valueOf(c.getRed()));
      prop.put(key.concat("-g"), String.valueOf(c.getGreen()));
      prop.put(key.concat("-b"), String.valueOf(c.getBlue()));
   }
   
   /**
    * @param prop The Properties set
    * @param key the key used to store this property
    * @param def a default in case the property cannot be retrieved
    */
   public static Collection getStringCollection(Properties prop, final String key, final Collection def)
   {
      try
      {
          // NOTE Compatible with StringArrays
         final int length = getInteger(prop,key +"-length");
         final Collection result = new ArrayList(length);
         for (int i = 0; i < length; i++) {
            String value = prop.getProperty(key + "-" + i); 
            if (value != null) result.add(value);
         }
         return result;
      }
      catch (Exception e)
      {
         return def;
      }
   }
   
   /**
    * @param prop The Properties set
    * @param key the key used to store this property
    * @param sa the value to store
    */
   public static void setStringCollection(Properties prop, final String key, Collection sa)
   {
      // remove previous setting
       try
       {
           // NOTE Compatible with StringArrays
          final int length = getInteger(prop,key +"-length");
          for (int i = 0; i < length; i++) {
             prop.remove(key + "-" + i); 
          }
          prop.remove(key+"-length");
       }
       catch (Exception e) { }
       
      prop.put( key +"-length", sa == null ? "0" : String.valueOf(sa.size()) );      
      if (sa != null) {
          int k = 0;
          for (Iterator i = sa.iterator(); i.hasNext(); ) {
              prop.put(key +"-"+ k, (String)i.next());
              k++;
          }
      }
   }
   
   /**
    * @param prop The Properties set
    * @param key the key used to store this property
    * @param def a default in case the property cannot be retrieved
    */
   public static String[] getStringArray(Properties prop, final String key, final String[] def)
   {
      try
      {
         final String[] result = new String[getInteger(prop,key +"-length")];
         for (int i = 0; i < result.length; i++)
            result[i] = prop.getProperty(key +"-"+ i);
         return result;
      }
      catch (Exception e)
      {
         return def;
      }
   }
   
   /**
    * @param prop The Properties set
    * @param key the key used to store this property
    * @param sa the value to store
    */
   public static void setStringArray(Properties prop, final String key, String[] sa)
   {
      prop.put( key +"-length", sa == null ? "0" : String.valueOf(sa.length) );
      if (sa != null) for (int i = 0; i < sa.length; i++) prop.put(key +"-"+ i, sa[i]);
   }
   
   /**
    * @param prop The Properties set
    * @param key the key used to store this property
    * @param def a default in case the property cannot be retrieved
    */
   public static String getString(Properties prop, final String key, final String def)
   {
      try
      {
         final String s = prop.getProperty(key);
         return s == null ? def : s;
      }
      catch (Exception e)
      {
         return def;
      }
   }
   
   /**
    * @param prop The Properties set
    * @param key the key used to store this property
    * @param s the value to store
    */
   public static void setString(Properties prop, final String key, String s)
   {
      if (s == null) s="";
      prop.put(key, s);
   }
   
   /**
    * @param prop The Properties set
    * @param key the key used to store this property
    * @param def a default in case the property cannot be retrieved
    */
   public static boolean getBoolean(Properties prop, final String key, final boolean def)
   {
      final String value = prop.getProperty(key);
      return value==null ? def : value.equalsIgnoreCase("true");
   }
   
   /**
    * @param prop The Properties set
    * @param key the key used to store this property
    * @param value the value to store
    */
   public static void setBoolean(Properties prop, final String key, final boolean value)
   {
      prop.put(key,String.valueOf(value));
   }
   
   /**
    * @exception NumberFormatException if the property retrieved cannot be converted to <code>int</code>
    * @param prop The Properties set
    * @param key the key used to store this property
    */
   public static int getInteger(Properties prop, final String key) throws NumberFormatException
   {
      return Integer.parseInt(prop.getProperty(key));
   }
   
   /**
    * @param prop The Properties set
    * @param key the key used to store this property
    * @param def a default in case the property cannot be retrieved
    */
   public static int getInteger(Properties prop, final String key, final int def)
   {
      try
      {
         final String s = prop.getProperty(key);
         return s == null ? def : Integer.parseInt(s);
      }
      catch (Exception e)
      {
         return def;
      }
   }
   /**
    * @param prop The Properties set
    * @param key the key used to store this property
    * @param i the value to store
    */
   public static void setInteger(Properties prop, final String key, final int i)
   {
      prop.put(key, String.valueOf(i));
   }
   
   /**
    * @param prop The Properties set
    * @param key the key used to store this property
    * @param f the value to store
    */
   public static void setFloat(Properties prop, final String key, final float f)
   {
      prop.put(key, String.valueOf(f));
   }
   
   /**
    * @param prop The Properties set
    * @param key the key used to store this property
    * @param def a default in case the property cannot be retrieved
    */
   public static float getFloat(Properties prop, final String key, final float def)
   {
      try
      {
         final String s = prop.getProperty(key);
         return s == null ? def : Float.parseFloat(s);
      }
      catch (Exception e)
      {
         return def;
      }
   }
   public static void setURL(Properties prop, final String key, final URL url)
   {
      prop.put(key,url.toString());
   }
    /**
     * Load a URL from a properties file. If the URL begins with / it is taken to be a
     * system resource (i.e. on the classpath).
     */
   public static URL getURL(Properties prop, final String key, final URL def)
   {
      String p = prop.getProperty(key);
      if (p == null) return def;
      try
      {
         return new URL(p);
      }
      catch (java.net.MalformedURLException x)
      {
         URL url = Application.getApplication().getClass().getResource(p);
         return url == null ? def : url;
      }
   }
   /**
    * Translates a string by substituting tokens of the form {name} to the
    * value of property name in the properties set.
    * @param prop The properties set
    * @param in The string to be translated
    * @return The resulting string.
    */
   public static String translate(Properties prop,String in)
   {
      if (in == null) return null;
      StringBuffer out = null; // avoid creating if unnecessary
      int l = in.length();
      int pos = 0;
      while (pos < l)
      {
         int start = in.indexOf('{',pos);
         if (start < 0) break;
         int end = in.indexOf('}',start);
         if (end < 0 || end-start < 2) break;
         
         if (out == null) out = new StringBuffer(in.substring(0,start));
         else out.append(in.substring(pos,start));
         
         String value = prop.getProperty(in.substring(start+1,end));
         if (value != null) out.append(value);
         pos = end+1;
      }
      if (out == null) return in;
      if (pos<l) out.append(in.substring(pos));
      return out.toString();
   }
    /**
     * Creates a TableModel from a property set
     */
   public static class PropertyTable extends AbstractTableModel implements Comparator
   {
      private List list;
      PropertyTable(Properties prop)
      {
         list = new ArrayList(prop.entrySet());
         Collections.sort(list,this);
      }
      public int compare(Object o1, Object o2)
      {
         Map.Entry entry1 = (Map.Entry) o1;
         Map.Entry entry2 = (Map.Entry) o2;
         String name1 = entry1.getKey().toString();
         String name2 = entry2.getKey().toString();
         return name1.compareTo(name2);
      }
      public int getRowCount() { return list.size(); }
      public int getColumnCount() { return 2; }
      public Object getValueAt(int row, int column)
      {
         Map.Entry entry = (Map.Entry) list.get(row);
         return column == 0 ? entry.getKey() : entry.getValue();
      }
      public String getColumnName(int col)
      {
         return col == 0 ? "Property" : "Value";
      }
   }
   /**
    * A Properties object whose values and defaults are automatically translated
    * if they contain {prop} tokens.
    * @see #translate(Properties,String)
    */
   // TODO: Protect against recursive translation?
   public static class TranslatedProperties extends Properties
   {
      public TranslatedProperties()
      {
         super();
      }
      public TranslatedProperties(Properties def)
      {
         super(def);
      }
      public String getProperty(String key)
      {
         return PropertyUtilities.translate(this,super.getProperty(key));
      }
      public String getProperty(String key, String def)
      {
         return PropertyUtilities.translate(this,super.getProperty(key,def));
      }
   }
}