/*
 * AboutPanel.java
 * Created on January 31, 2001, 12:50 PM
 */

package org.freehep.application;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * An about box for use by Applications.
 * By default includes buttons for users to check System/Application/User properties
 * @author  tonyj
 * @version $Id: AboutDialog.java 8584 2006-08-10 23:06:37Z duns $
 */
public class AboutDialog extends javax.swing.JDialog
{
   private Application app;
    /**
     * Creates new AboutDialog.
     * @param app The application owning the AboutDialog
     */
   public AboutDialog(Application app)
   {
      super((javax.swing.JFrame) javax.swing.SwingUtilities.getAncestorOfClass(javax.swing.JFrame.class,app));
      this.app = app;
      final java.util.Properties props = app.getUserProperties();
      final JPanel panel = new JPanel(new java.awt.BorderLayout());
      
      java.net.URL image = PropertyUtilities.getURL(props,"aboutImage",null);
      if (image != null)
      {
         javax.swing.ImageIcon icon = new javax.swing.ImageIcon(image);
         javax.swing.JLabel l = new javax.swing.JLabel(icon);
         l.setBackground(java.awt.Color.white);
         l.setOpaque(true);
         panel.add(l);
      }
      else
      {
         String text = props.getProperty("aboutLabel","<html><h1>{title}</h1>");
         panel.add(new javax.swing.JLabel(text));
      }
      setTitle(app.getFullVersion());
      setModal(true);
      final JPanel info = createInfoPanel();
      if (info != null) panel.add(info, java.awt.BorderLayout.EAST);
      info.setVisible(PropertyUtilities.getBoolean(props,"aboutShowInfoPanel",true));
      
      javax.swing.Action toggleInfoPanel = new javax.swing.AbstractAction()
      {
         public void actionPerformed(java.awt.event.ActionEvent e)
         {
            if (info != null) 
            {
               PropertyUtilities.setBoolean(props,"aboutShowInfoPanel",!info.isVisible());
               info.setVisible(!info.isVisible());
               if (!info.isVisible()) panel.requestFocus();
               pack();
            }
         }
      };
      panel.getInputMap(JComponent.WHEN_FOCUSED).put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P,java.awt.event.InputEvent.CTRL_MASK),"toggleInfoPanel");
      panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P,java.awt.event.InputEvent.CTRL_MASK),"toggleInfoPanel");
      panel.getActionMap().put("toggleInfoPanel",toggleInfoPanel);
      setContentPane(panel);
      setResizable(false);
   }
    /**
     * Override to customize the InfoPanel.
     * By default the InfoPanel contains buttons allowing the user to view
     * the System/Application/User properties.
     */
   protected JPanel createInfoPanel()
   {
      return new org.freehep.application.AboutDialog.InfoPanel();
   }
   protected class InfoPanel extends JPanel implements java.awt.event.ActionListener
   {
      private JButton b1, b2, b3;
      public InfoPanel()
      {
         setBorder(javax.swing.BorderFactory.createTitledBorder("Properties"));
         setLayout(new java.awt.BorderLayout());
         JPanel p = new JPanel(new java.awt.GridLayout(0,1));
         b1 = new JButton("System...");
         b2 = new JButton("Application...");
         b3 = new JButton("User...");
         try
         {
            System.getProperties();
         }
         catch (SecurityException x)
         {
            b1.setEnabled(false);
         }
         b1.addActionListener(this);
         b2.addActionListener(this);
         b3.addActionListener(this);
      
         p.add(b1);
         p.add(b2);
         p.add(b3);
         
         add(p,java.awt.BorderLayout.NORTH);
      }
      public void actionPerformed(java.awt.event.ActionEvent e)
      {
         JButton source = (JButton) e.getSource();
         java.util.Properties prop;
         if      (source == b1) prop = System.getProperties();
         else if (source == b2) prop = app.getAppProperties();
         else                   prop = app.getUserProperties();
         
         javax.swing.JTable table = new javax.swing.JTable(new org.freehep.application.PropertyUtilities.PropertyTable(prop));
         String title = source.getText();
         if (title.endsWith("...")) title = title.substring(0,title.length()-3);
         title += " Properties";
         javax.swing.JDialog dlg = new javax.swing.JDialog(AboutDialog.this,title);
         dlg.setContentPane(new javax.swing.JScrollPane(table));
         dlg.setModal(true);
         dlg.pack();
         dlg.setLocationRelativeTo(AboutDialog.this);
         dlg.setVisible(true);
      }
   }
}

