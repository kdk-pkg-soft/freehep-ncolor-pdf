package org.freehep.application.studio;
import java.net.URL;
import java.net.URLClassLoader;

/**
 *
 * @author tonyj
 */
public class ExtensionClassLoader extends URLClassLoader
{
   public ExtensionClassLoader(URL[] urls)
   {
      // Delegate to classloader that loaded this class, 
      // othewise this doesn't work in webstart.
      super(urls,ExtensionClassLoader.class.getClassLoader());
   }
   public void addURL(URL url)
   {
      super.addURL(url);
   }
}
