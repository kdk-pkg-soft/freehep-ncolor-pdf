package org.freehep.application.studio.pluginmanager;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import org.freehep.application.studio.ExtensionClassLoader;
import org.freehep.application.studio.PluginInfo;
import org.freehep.application.studio.Studio;

/**
 *
 * @author Tony Johnson (tonyj@slac.stanford.edu)
 * @version $Id: PluginInstallDialog.java 8584 2006-08-10 23:06:37Z duns $
 */

class PluginInstallDialog extends JDialog implements ListSelectionListener, ActionListener
{
   PluginInstallDialog(Studio app, PluginManagerDialog parent)
   {
      super(parent);
      this.app = app;
      plugins = parent.getInstallablePlugins();
      
      JPanel c = new JPanel(new BorderLayout(10,10));
      c.setBorder(BorderFactory.createEmptyBorder(10,10,5,10));
      c.add(new JLabel("Select Plugins to install"),BorderLayout.NORTH);
      
      //JPanel p = new JPanel(new GridLayout(1,2,5,5));
      
      table = new JTable(new DataModel());
      table.setTableHeader(null);
      table.setShowGrid(false);
      TableColumn col = table.getColumn(table.getColumnName(0));
      col.setMaxWidth(col.getMinWidth());
      table.setPreferredScrollableViewportSize(table.getPreferredSize());
      c.add(new JScrollPane(table),BorderLayout.WEST);
      
      table.getSelectionModel().addListSelectionListener(this);
      table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      
      info = new PluginInfoPanel();
      info.setBorder(BorderFactory.createTitledBorder("Plugin Info"));
      c.add(info,BorderLayout.CENTER);
      //c.add(p,BorderLayout.CENTER);
      
      JPanel bottom = new JPanel(new BorderLayout());
      JPanel cb = new JPanel(new BorderLayout());
      String dir = app.getUserExtensionsDir();
      cb1 = new JRadioButton("Install in user directory ("+dir+")");
      File df = new File(dir);
      if (!df.exists()) df.mkdirs();
      cb1.setEnabled(dir != null && df.canWrite());
      cb1.setSelected(true);
      cb.add(cb1,BorderLayout.NORTH);
      dir = app.getSystemExtensionsDir();
      cb2 = new JRadioButton("Install in system directory ("+dir+")");
      df = new File(dir);
      if (!df.exists()) df.mkdirs();
      cb2.setEnabled(dir != null && df.canWrite());
      cb.add(cb2,BorderLayout.SOUTH);
      ButtonGroup rg = new ButtonGroup();
      rg.add(cb1);
      rg.add(cb2);
      bottom.add(cb,BorderLayout.NORTH);
      
      install = new JButton("Install");
      install.addActionListener(this);
      install.setEnabled(false);
      close = new JButton("Close");
      close.addActionListener(this);
      JPanel buttonPanel = new JPanel();
      buttonPanel.add(install);
      buttonPanel.add(close);
      bottom.add(buttonPanel,BorderLayout.SOUTH);
      c.add(bottom,BorderLayout.SOUTH);
      
      setContentPane(c);
   }
   public void valueChanged(ListSelectionEvent e)
   {
      if (e.getValueIsAdjusting()) return;
      int index = table.getSelectedRow();
      if (index >= 0) info.setPlugin((PluginInfo) plugins.get(index));
      else info.setPlugin(null);
   }
   public void actionPerformed(ActionEvent e)
   {
      Object source = e.getSource();
      if (source == close)
      {
         dispose();
      }
      else
      {
         Map downloads = new HashMap();
         String base = cb1.isSelected() ? app.getUserExtensionsDir() : app.getSystemExtensionsDir();
         // Build the list of files to download
         for (int i=0; i<plugins.size(); i++ )
         {
            if (onOff.get(i))
            {
               PluginInfo info = (PluginInfo) plugins.get(i);
               Map files = info.getFiles();
               for (Iterator iter = files.entrySet().iterator(); iter.hasNext(); )
               {
                  Map.Entry entry = (Map.Entry) iter.next();
                  File key = new File(base,entry.getKey().toString());
                  downloads.put(key, entry.getValue());
               }
            }
         }
         PluginDownload download = new PluginDownload(downloads);
         Thread t = new Thread(download);
         t.start();
         JOptionPane.showMessageDialog(this,download,"Downloading...",JOptionPane.PLAIN_MESSAGE);

         Throwable status = download.getStatus();
         System.out.println("Done! status="+status);
         if (status != null)
         {
            JOptionPane.showMessageDialog(this,"Download failed: "+status,"Download error",JOptionPane.ERROR_MESSAGE);
            download.cleanUp();
         }
         else 
         {
            download.commit();
            // Add the new jars to the list of URL's
            ExtensionClassLoader loader = app.getExtensionLoader();
            for (Iterator i = downloads.keySet().iterator(); i.hasNext(); )
            {
               try
               {
                  loader.addURL(((File) i.next()).toURL());
               }
               catch (java.net.MalformedURLException x) { x.printStackTrace(); }
            }
            // Load the new plugins
            app.loadPlugins(plugins,loader);
         }
      }
   }
   
   private JButton install;
   private JButton close;
   private JRadioButton cb1, cb2;
   private BitSet onOff;
   private JTable table;
   private PluginInfoPanel info;
   private List plugins;
   private Studio app;
   
   private class DataModel extends AbstractTableModel
   {
      DataModel()
      {
         onOff = new BitSet(plugins.size());
      }
      public int getColumnCount()
      {
         return 2;
      }
      public int getRowCount()
      {
         return plugins.size();
      }
      public Object getValueAt(int rowIndex, int columnIndex)
      {
         if (columnIndex == 0) return new Boolean(onOff.get(rowIndex));
         else return ((PluginInfo) plugins.get(rowIndex)).getName();
      }
      public void setValueAt(Object aValue, int rowIndex, int columnIndex)
      {
         onOff.set(rowIndex,((Boolean) aValue).booleanValue());
         install.setEnabled(!onOff.isEmpty());
      }
      public Class getColumnClass(int columnIndex)
      {
         return columnIndex == 0 ? Boolean.class : String.class;
      }
      public boolean isCellEditable(int rowIndex, int columnIndex)
      {
         return columnIndex == 0;
      }
   }
}
