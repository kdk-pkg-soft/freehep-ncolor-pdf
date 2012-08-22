package org.freehep.application.studio.pluginmanager;

import java.awt.GridLayout;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

/**
 *
 * @author Tony Johnson (tonyj@slac.stanford.edu)
 * @version $Id: PluginDownload.java 8584 2006-08-10 23:06:37Z duns $
 */
class PluginDownload extends JPanel implements Runnable
{
   private Map files;
   private Map downloads;
   private Throwable status;
   
   PluginDownload(Map files)
   {
      super(new GridLayout(4,1));
      
      this.files = files;
      int n = files.size();
      label1 = new JLabel("File 1/"+n);
      label2 = new JLabel("Downloading ...");
      progress1 = new JProgressBar(0,n);
      progress2 = new JProgressBar();
      
      add(label1);
      add(progress1);
      add(label2);
      add(progress2);
   }
   Throwable getStatus()
   {
      return status;
   }
   void cleanUp()
   {
      downloads = null;
   }
   void commit()
   {
      try
      {
         System.out.println("commit"+downloads.size());
         for (Iterator iter = downloads.entrySet().iterator(); iter.hasNext(); )
         {
            Map.Entry entry = (Map.Entry) iter.next();
            ByteArrayOutputStream bytes = (ByteArrayOutputStream) entry.getValue();
            System.out.println("file="+entry.getKey());
            OutputStream out = new FileOutputStream((File) entry.getKey());
            bytes.writeTo(out);
            out.close();
         }
      }
      catch (IOException x)
      {
         x.printStackTrace();
      }
      finally
      {
         downloads = null;
      }
   }
   public void run()
   {
      try
      {
         downloads = new HashMap();
         Iterator iter = files.entrySet().iterator();
         for (int i=0; iter.hasNext(); i++)
         {
            Map.Entry entry = (Map.Entry) iter.next();
            final URL url = new URL(entry.getValue().toString());
            
            SwingUtilities.invokeLater(new Update(i,url));
            
            java.net.URLConnection connect = url.openConnection();
            InputStream in = connect.getInputStream();
            byte[] buffer = new byte[8196];
            OutputStream out = new ByteArrayOutputStream();
            try
            {
               for (;;)
               {
                  int l = in.read(buffer);
                  if (l<0) break;
                  out.write(buffer,0,l);
               }
               out.close();
               downloads.put(entry.getKey(),out);
            }
            catch (EOFException x)
            {

            }
            finally
            {
               in.close();
            }
         }
      }
      catch (Throwable t)
      {
         status = t;
      }
      finally
      {
         System.out.println("Download finished");
         SwingUtilities.invokeLater(new Update(files.size(),null));
      }
   }
   
   private class Update implements Runnable
   {
      private int n;
      private URL url;
      Update(int n, URL url)
      {
         this.n = n;
         this.url = url;
      }
      public void run()
      {
         label1.setText("File "+(n+1)+"/"+files.size());
         progress1.setValue(n);
         if (url == null)
         {
            JDialog dlg = (JDialog) SwingUtilities.getAncestorOfClass(JDialog.class,PluginDownload.this);
            System.out.println("dlg="+dlg);
            if (dlg != null) dlg.dispose();
         }
         else label2.setText("Downloading "+url+"...");
      }
   }
   private JLabel label1;
   private JLabel label2;
   private JProgressBar progress1;
   private JProgressBar progress2;
}
