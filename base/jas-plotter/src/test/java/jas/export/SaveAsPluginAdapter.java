package jas.export;

import jas.hist.SaveAsPlugin;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import org.freehep.util.export.ExportFileType;

/**
 *
 * @author tonyj
 * @version $Id: SaveAsPluginAdapter.java 11553 2007-06-05 22:06:23Z duns $
 */
public class SaveAsPluginAdapter implements SaveAsPlugin
{
   private ExportFileType eft;
   /** Creates a new instance of SaveAsPluginAdapter */
   public SaveAsPluginAdapter(ExportFileType eft)
   {
      this.eft = eft;
   }
   public File adjustFilename(File file)
   {
      return eft.adjustFilename(file, null);
   } 
   public FileFilter getFileFilter()
   {
      return eft.getFileFilter();
   }
   public JPanel getOptionsPanel()
   {
      return eft.createOptionPanel(null);
   }
   public boolean hasOptions()
   {
      return eft.hasOptionPanel();
   }
   public void saveAs(Component c, OutputStream out, File file, Component dialogParent) throws IOException
   {
      eft.exportToFile(file, c, dialogParent, null, "");
   }
   public boolean supportsClass(Object o)
   {
      return true;
   }
   public void saveOptions(Properties props)
   {
//      eft.saveOptions(props);
   }
   public void restoreOptions(Properties props)
   {
//      eft.restoreOptions(props);
   }
}
