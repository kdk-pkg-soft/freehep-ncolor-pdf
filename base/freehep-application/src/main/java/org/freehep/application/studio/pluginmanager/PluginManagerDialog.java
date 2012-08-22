package org.freehep.application.studio.pluginmanager;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.freehep.application.studio.PluginInfo;
import org.freehep.application.studio.Studio;


class PluginManagerDialog extends JDialog implements ActionListener
{
   private DefaultMutableTreeNode loadedtree;
   private JButton install;
   private JButton remove;
   private JButton update;
   private JOptionPane updatepane;
   private JTree tree;
   private List installablePlugins;
   private List updatablePlugins;
   private Map updateMap;
   private PluginInfoPanel info;
   private PluginManager manager;
   private Studio app;

   PluginManagerDialog(Studio studio, PluginManager manager)
   {
      super();
      this.app = studio;
      this.manager = manager;

      //super.setHelpTopic("userInterface.pluginmanager");
      JPanel c = new JPanel(new BorderLayout());
      c.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

      JPanel panel = new JPanel(new GridLayout(1, 2, 5, 5));
      tree = new JTree();
      tree.setCellRenderer(new Renderer());
      tree.setRootVisible(false);
      tree.setVisibleRowCount(8);
      tree.getSelectionModel().addTreeSelectionListener(new TreeHandler());

      JScrollPane treescroll = new JScrollPane(tree);
      panel.add(treescroll);

      info = new PluginInfoPanel()
      {
         private JLabel updateLabel;

         void addExtraInfo(Object c1, Object c2)
         {
            add(new JLabel("Latest Version:"), c1);
            add(updateLabel = new JLabel(), c2);
         }

         void setExtraInfo(PluginInfo info)
         {
            String label = null;
            if (updateMap != null)
            {
               PluginInfo update = (PluginInfo) updateMap.get(info);
               if (update != null)
               {
                  label = update.getVersion();
               }
            }
            updateLabel.setText(label);
         }
      };

      info.setBorder(BorderFactory.createTitledBorder("Plugin Info"));
      panel.add(info);

      JPanel buttons = new JPanel();
      remove = new JButton("Remove selected plugins");
      remove.addActionListener(this);
      remove.setActionCommand("remove");
      buttons.add(remove);
      update = new JButton("Update installed plugins");
      update.addActionListener(this);
      update.setActionCommand("update");
      buttons.add(update);
      install = new JButton("Install a plugin");
      install.addActionListener(this);
      install.setActionCommand("install");
      buttons.add(install);

      remove.setEnabled(false);
      install.setEnabled(false);
      update.setEnabled(false);

      c.add(BorderLayout.SOUTH, buttons);
      c.add(BorderLayout.CENTER, panel);
      setContentPane(c);

      updateTree();

      List available = manager.getAvailablePlugins();
      if (available != null)
      {
         processAvailablePlugins(available);
      }
   }

   public void actionPerformed(ActionEvent e)
   {
      Object source = e.getSource();
      if (source == install)
      {
         JDialog dlg = new PluginInstallDialog(app, this);
         dlg.pack();
         dlg.setVisible(true);
      }

      //		}else if(e.getActionCommand()=="remove"){
      //			if(remove.isEnabled()){
      //				try{
      //					removePlugins();
      //				}catch(IOException io){
      //					System.out.println(io + "Exception occured while trying to remove plugins.");
      //				}
      //			}
      //		}
      else if (source == update)
      {
         Map downloads = new HashMap();
         String base = app.getUserExtensionsDir(); // FixMe!

         // Build the list of files to download
         for (Iterator i = updatablePlugins.iterator(); i.hasNext();)
         {
            PluginInfo info = (PluginInfo) i.next();
            Map files = info.getFiles();
            for (Iterator iter = files.entrySet().iterator(); iter.hasNext();)
            {
               Map.Entry entry = (Map.Entry) iter.next();
               File key = new File(base, entry.getKey().toString());
               downloads.put(key, entry.getValue());
            }
         }

         PluginDownload download = new PluginDownload(downloads);
         Thread t = new Thread(download);
         t.start();
         JOptionPane.showMessageDialog(this, download, "Downloading...", JOptionPane.PLAIN_MESSAGE);

         Throwable status = download.getStatus();
         System.out.println("Done! status=" + status);
         if (status != null)
         {
            JOptionPane.showMessageDialog(this, "Download failed: " + status, "Download error", JOptionPane.ERROR_MESSAGE);
            download.cleanUp();
         }
         else
         {
            download.commit();


            // Add the new jars to the list of URL's
            JOptionPane.showMessageDialog(this, "You must restart JAS for these changed to take effect");
         }
      }
   }

   public void onCancel()
   {
      dispose();
   }

   public void onOK()
   {
      dispose();
   }

   protected void updateTree()
   {
      DefaultMutableTreeNode treeRoot = new DefaultMutableTreeNode();
      DefaultTreeModel treeModel = new DefaultTreeModel(treeRoot, true);

      java.util.List loadedplugins = app.getPlugins();
      loadedtree = new DefaultMutableTreeNode("Loaded Plugins", true);
      treeModel.insertNodeInto(loadedtree, treeRoot, 0);

      DefaultMutableTreeNode node = null;

      if (loadedplugins != null)
      {
         for (int i = 0; i < loadedplugins.size(); i++)
         {
            node = new DefaultMutableTreeNode(loadedplugins.get(i), false);
            treeModel.insertNodeInto(node, loadedtree, 0);
         }
      }

      treeRoot.insert(loadedtree, 0);

      tree.setModel(treeModel);
      for (int i = 0; i < tree.getRowCount(); i++)
         tree.expandRow(i);

      remove.setEnabled(false);

      //if(pluginmanager.getNotInstalledPluginList().size() >0)install.setEnabled(true);
      //else install.setEnabled(false);
      //if(pluginmanager.getOutDatedPluginList().size() >0)update.setEnabled(true);
      //else update.setEnabled(false);
   }

   java.util.List getInstallablePlugins()
   {
      return installablePlugins;
   }

   /**
    * Called once the list of available plugins are available
    */
   void processAvailablePlugins(List available)
   {
      // Loop over new plugins, and match them up with already existing 
      // plugins
      Map loadedMap = new HashMap();
      java.util.List loadedplugins = app.getPlugins();
      for (Iterator i = loadedplugins.iterator(); i.hasNext();)
      {
         PluginInfo info = (PluginInfo) i.next();
         loadedMap.put(info.getName(), info);
      }
      installablePlugins = new ArrayList();
      updatablePlugins = new ArrayList();
      updateMap = new HashMap();
      for (Iterator i = available.iterator(); i.hasNext();)
      {
         PluginInfo info = (PluginInfo) i.next();
         PluginInfo old = (PluginInfo) loadedMap.get(info.getName());
         if (old != null)
         {
            updateMap.put(old, info);
            if (app.versionNumberCompare(info.getVersion(), old.getVersion()) > 0)
            {
               updatablePlugins.add(info);
            }
         }
         else
         {
            installablePlugins.add(info);
         }
      }
      install.setEnabled(!installablePlugins.isEmpty());
      update.setEnabled(!updatablePlugins.isEmpty());
   }

   private void removePlugins() throws IOException
   {
      //		TreePath[] selectednodes = tree.getSelectionPaths();
      //        DefaultMutableTreeNode node = null;
      //        PluginInfo plugleaf;
      //		File removefile;
      //		String[] options = {"OK"};
      //		boolean successfulremove =false;
      //		int optionbutton;
      //		
      //			if(selectednodes !=null){
      //loopA:	    for(int i=0;i<selectednodes.length;i++){
      //				node = (DefaultMutableTreeNode) selectednodes[i].getLastPathComponent();
      //				if(node.getParent()==loadedtree){
      //							plugleaf = (PluginInfo) node.getUserObject();
      //							if(plugleaf.removeable==true){
      //								
      //								removefile=new File(plugleaf.path);										
      //								if(!removefile.canWrite()){
      //									optionbutton = JOptionPane.showOptionDialog(c,"You do not have write access to "+removefile.getAbsolutePath()+".The plugin "+plugleaf.name+" cannot be removed." , "Removing Plugins", JOptionPane.DEFAULT_OPTION , JOptionPane.ERROR_MESSAGE,null,options,options[0]);
      //									continue loopA;
      //								}else if(plugleaf.installable == false) optionbutton = JOptionPane.showConfirmDialog(c,"The plugin "+plugleaf.name+" cannot be reinstalled by JAS. If you still wish to remove it click 'Ok'." , "Removing Plugin", JOptionPane.OK_CANCEL_OPTION , JOptionPane.WARNING_MESSAGE);
      //								else optionbutton = JOptionPane.OK_OPTION;	
      //								
      //								if(optionbutton ==  JOptionPane.OK_OPTION){
      //									for(int j=0;j<plugleaf.files.length;j++){
      //										
      //										removefile =  new File(plugleaf.path,plugleaf.files[j]);
      //									
      //										FileOutputStream out = new FileOutputStream(removefile);
      //										try{
      //											out.write(null);//null  pointer here to get 0 byte file
      //										}catch(NullPointerException np){
      //											//System.out.println(np);
      //											//do nothing, is this a bad idea?	
      //										}
      //											
      //										out.close();
      //									}
      //									successfulremove = true;
      //									plugleaf.setState(PluginProperties.NOTINSTALLED);
      //									
      //								}
      //							}else	optionbutton = JOptionPane.showOptionDialog(c,"The plugin "+plugleaf.name+" is not removeable." , "Removing Plugins", JOptionPane.DEFAULT_OPTION , JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);
      //					}
      //				}
      //			
      //				if(successfulremove==true)optionbutton = JOptionPane.showOptionDialog(c,"For these changes to take effect you will need to restart JAS." , "Removing Plugins", JOptionPane.DEFAULT_OPTION , JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);
      //					
      //			}
      //		updateTree();
      //		setVisible(true);
      //
   }

   class Renderer extends DefaultTreeCellRenderer
   {
      public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
      {
         super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
         if (value instanceof DefaultMutableTreeNode)
         {
            Object info = ((DefaultMutableTreeNode) value).getUserObject();
            if (info instanceof PluginInfo)
            {
               PluginInfo plugleaf = (PluginInfo) info;
               String label = plugleaf.getName();
               if (updateMap != null)
               {
                  PluginInfo update = (PluginInfo) updateMap.get(info);
                  if ((update != null) && (app.versionNumberCompare(update.getVersion(), plugleaf.getVersion()) > 0))
                  {
                     label += " (update available)";
                  }
               }
               setText(label);
            }
         }
         setIcon(null);
         return this;
      }
   }

   class TreeHandler implements TreeSelectionListener
   {
      public void valueChanged(TreeSelectionEvent evt)
      {
         TreePath selection = evt.getPath();
         DefaultMutableTreeNode node;

         if (selection == null)
         {
            node = null;
         }
         else
         {
            node = (DefaultMutableTreeNode) selection.getLastPathComponent();
         }

         if ((node != null) && node.isLeaf() && node.getUserObject() instanceof PluginInfo)
         {
            PluginInfo plugprops = (PluginInfo) node.getUserObject();
            info.setPlugin(plugprops);
         }
         else
         {
            info.setPlugin(null);
         }

         node = null;

         TreePath[] checkselection = tree.getSelectionPaths();
         if (checkselection != null)
         {
checkloop: for (int i = 0; i < checkselection.length; i++)
            {
               node = (DefaultMutableTreeNode) checkselection[i].getLastPathComponent();
               if (node.getUserObject() instanceof PluginInfo)
               {
                  remove.setEnabled(true);
                  break checkloop;
               }
               else if (node == loadedtree)
               {
                  remove.setEnabled(false);
               }
            }
         }
      }
   }
}