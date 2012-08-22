package org.freehep.application;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


/**
 * This class is ideal if you want to have a text field for user input but
 * also have a list of recently selected items available to choose from.
 * Some notes on using this class:
 * <ul>
 *  <li>The default button will be clicked on the root pane of this
 *  component if the value of the constructor parameter <code>clickDefault</code>
 *  is <code>true</code>.  This behaviour may be desirable because
 *  the default button is normally "clicked" when the user clicks on
 *  the "Enter" key, but this behaviour doesn't normally happen unless
 *  you add an ActionListener to the text field.</li>
 *  <li>Calling <code>addActionListener(ActionListener)</code> on this
 *  object causes the ActionListener to be added to the text field.</li>
 *  <li>A KeyEvent is generated for every time a key is pressed in the
 *  field, and all listeners are notified.  KeyEvents normally generated
 *  by the JComboBox will not be sent by this class; they are suppressed.</li>
 *  <li>A ChangeEvent is generated any time the text in the field changes.
 *  Therefore, if you want to update a button state (for example) each time the
 *  text changes, simply implement a ChangeListener and add it to the
 *  listener list for this object.  Otherwise, you would have to add a
 *  KeyListener and an ItemListener, and it's better just to have one.</li>
 * </ul>
 * It is important to point this out because the KeyEvents, ActionEvents,
 * and ChangeEvents described above are not how a normal combo
 * box will generate them.
 * <p>
 * Basically, a ChangeEvent is a dual purpose event.  One is sent to listeners
 * both when a KeyEvent is generated from the text field, and one is sent when
 * an ItemEvent is generated (which happens when an item is selected from the
 * drop-down list).  This means that somebody who wants to know when the actual
 * text showing has changed only needs to implement ChangeListener instead of
 * both ItemListener and KeyListener.
 * @author Jonas Gifford
 * @version $Id: RecentItemTextField.java 8584 2006-08-10 23:06:37Z duns $
 */

public class RecentItemTextField extends JComboBox
{
   public RecentItemTextField()
   {
      this(null,4,false);
   }
   /**
    * Creates a RecentItemTextField with the given list of drop-down items.  You are
    * responsible for keeping and providing an updated list.
    *  @param dropDownItems the items to show in the drop-down list (can safely be <code>null</code>)
    *  @param clickDefault whether the default button should be clicked when Enter is pressed
    *  @see javax.swing.JRootPane#defaultButton
    */
   public RecentItemTextField(String[] dropDownItems,boolean clickDefault)
   {
      m_clickDefault = clickDefault;
      init(dropDownItems);
   }
   /**
    * Creates a RecentItemTextField with a dropDown list that will be stored in
    * the user properties object for the Application.  Invoke the <code>saveState()</code>
    * method to have the list be updated to include the selected item.
    *  @param key the key that the drop-down items will be stored by
    *  @param nItems the maximum number of items that will be stored on the drop-down list
    *  @param clickDefault whether the default button should be clicked when Enter is pressed
    *  @see Application#getUserProperties()
    *  @see javax.swing.JRootPane#defaultButton
    */
   public RecentItemTextField(String key,int nItems,boolean clickDefault)
   {
      m_clickDefault = clickDefault;
      Application app = Application.getApplication();
      m_prop = app == null ? null : app.getUserProperties();
      m_nItems = nItems;
      m_key = key;
      init(key != null && m_prop != null ? PropertyUtilities.getStringArray(m_prop,key,null) : null);
   }
   /**
    * Creates a RecentItemTextField with a dropDown list that will be stored in
    * the user properties object for the Application.  Invoke the <code>saveState()</code>
    * method to have the list be updated to include the selected item.
    *  @param key the key that the drop-down items will be stored by
    *  @param lengthKey the key that maps to the maximum number of items that will be stored on the drop-down list
    *  @param clickDefault whether the default button should be clicked when Enter is pressed
    *  @see Application#getUserProperties()
    *  @see javax.swing.JRootPane#defaultButton
    */
   public RecentItemTextField(String key,String lengthKey,boolean clickDefault)
   {
      this(key, PropertyUtilities.getInteger(Application.getApplication().getUserProperties(),lengthKey, 4), clickDefault);
   }
   private void init(String[] dropDownItems)
   {
      m_dropDownItems = dropDownItems;
      if (m_dropDownItems != null)
         for (int i = 0; i < m_dropDownItems.length; i++)
            addItem(m_dropDownItems[i]);
      setEditable(true);
      m_textField = (JTextField) getEditor().getEditorComponent();
      m_textField.addKeyListener(myListener);
      m_textField.getDocument().addDocumentListener(myListener);
   }
   public void addKeyListener(KeyListener kl)
   {
      if (m_textField != null)
         m_textField.addKeyListener(kl);
   }
   /**
    * If a key was supplied to the constructor, the drop-down list will be updated to
    * include the currently selected item.
    */
   public void saveState()
   {
      if (m_key != null && m_prop != null)
         PropertyUtilities.setStringArray(m_prop, m_key, updateStringArray(m_dropDownItems, getText(), m_nItems));
   }
   /** Returns the text currently showing in the text field. */
   public String getText()
   {
      return m_textField.getText();
   }
   /** Sets the text showing in the text field. */
   public void setText(String s)
   {
      if (s.equals(getItemAt(0))) return; // it's already there; nothing to do
      removeItem(s); // if the item is already there, just move it to the top
      insertItemAt(s, 0); // put the new text at the top
      setSelectedIndex(0);
   }
   
   private void fireStateChanged()
   {
      ChangeListener[] listeners = (ChangeListener[]) listenerList.getListeners(ChangeListener.class);
      for (int i = listeners.length-1; i >= 0; i--)
      {
         if (m_changeEvent == null) m_changeEvent = new ChangeEvent(this);
         listeners[i].stateChanged(m_changeEvent);
      }
   }
   /**
    * Change listeners will be notified any time the text visible in the text field changes.
    * This is equivalent to having a KeyListener and an ItemListener.
    *  @param l the ChangeListener to add
    */
   public void addChangeListener(ChangeListener l)
   {
      listenerList.add(ChangeListener.class, l);
   }
   /**
    * Adds an the given ActionListener to the text field.
    */
   public void addActionListener(ActionListener l)
   {
      m_textField.addActionListener(l);
   }
   /** This method is protected as an implementation side effect. */
   protected void fireItemStateChanged(ItemEvent event)
   {
      super.fireItemStateChanged(event); // allow normal ItemEvent firing
      fireStateChanged(); // when an ItemEvent is generated, also notify listeners of ChangeEvent
   }
   /** Requests focus for the text field of the box. */
   public void requestFocus()
   {
      m_textField.requestFocus();
   }
   
   /**
    * Set the maximum width of the box.  The default is used if this method is not called.
    *  @param maxWidth give a number in pixels, or <code>-1</code> to use default
    */
   public void setMaxWidth(int maxWidth)
   {
      m_maxWidth = maxWidth;
   }
   /**
    * Set the minimum width of the box.  The default is used if this method is not called.
    *  @param minWidth give a number in pixels, or <code>-1</code> to use default
    */
   public void setMinWidth(int minWidth)
   {
      m_minWidth = minWidth;
   }
   /**
    * Incorporates the maximum width if it has been set.
    *  @see #setMaxWidth(int)
    */
   public Dimension getMaximumSize()
   {
      Dimension d = super.getMaximumSize();
      if (m_maxWidth > 0 && m_maxWidth < d.width) d.width = m_maxWidth;
      return d;
   }
   /**
    * Incorporates the minimum width if it has been set.
    *  @see #setMinWidth(int)
    */
   public Dimension getMinimumSize()
   {
      Dimension d = super.getMinimumSize();
      if (m_minWidth > 0 && m_minWidth > d.width) d.width = m_minWidth;
      return d;
   }
   /**
    * Incorporates the minimum and maximum widths if they have been set.
    *  @see #setMinWidth(int)
    *  @see #setMaxWidth(int)
    */
   public Dimension getPreferredSize()
   {
      Dimension d = super.getPreferredSize();
      if (m_minWidth > 0 && m_minWidth > d.width) d.width = m_minWidth;
      if (m_maxWidth > 0 && m_maxWidth < d.width) d.width = m_maxWidth;
      return d;
   }
   /**
    * Show an input dialog, with a record of recent entries.
    * @param owner The dialog owner
    * @param message The message to display
    * @param key The key under which the recent items are stored in the user properties
    * @see javax.swing.JOptionPane#showInputDialog(Component,Object)
    */
   public static String showInputDialog(Component owner, Object message, String key)
   {
      return showInputDialog(owner,message,"Input",JOptionPane.QUESTION_MESSAGE,key);
   }
   /**
    * Show an input dialog, with a record of recent entries.
    * @param owner The dialog owner
    * @param message The message to display
    * @param title The dialog title
    * @param messageType The message type
    * @param key The key under which the recent items are stored in the user properties
    * @see JOptionPane#showInputDialog(Component,Object,String,int)
    */
   public static String showInputDialog(Component owner, Object message, String title, int messageType, String key)
   {
      RecentItemTextField field = new RecentItemTextField(key,4,true);
      JPanel mpanel = new JPanel(new BorderLayout());
      if (message instanceof Component) mpanel.add((JComponent) message);
      else mpanel.add(new JLabel(message.toString()));
      mpanel.add(field,BorderLayout.SOUTH);
      int rc = JOptionPane.showOptionDialog(owner,mpanel,title,JOptionPane.OK_CANCEL_OPTION,messageType,null,null,null);
      if (rc == JOptionPane.OK_OPTION)
      {
         field.saveState();
         return field.getText();
      }
      else return null;
   }
   /**
    * This is a utility method for updating a string array of recently used items.
    * Supply it with an old array and a new item.  If the new item was already in the
    * old array, then it is simply moved to the beginning.  If it was not in the old
    * array then it is placed at the front and the other items are shuffled back.
    * The method will return an array of a maximum size given by the <code>nStored</code> parameter.
    *  @param oldArray the array to update (may safely be <code>null</code>)
    *  @param newString the new item to include
    *  @param nStored the maximum size of the updated array
    *  @return the updated array
    */
   private static String[] updateStringArray(String[] oldArray, String newString, int nStored)
   {
      if (newString == null) return oldArray;
      if (oldArray != null && oldArray.length > 0)
      {
         if (oldArray[0].equals(newString)) return oldArray;
         /*
          * If the new String is already at the front, then
          * there is nothing left to do.
          */
         for (int i = 1; i < oldArray.length; i++)
         /*
          * Begin checking from the second element
          * onwards: is the String already there?
          */
         {
            if (oldArray[i].equals(newString))
            /*
             * If it is already there, put
             * the new one at the front and
             * shuffle the others backward.
             */
            {
               while (i > 0)
               {
                  oldArray[i] = oldArray[i-1];
                  i--;
               }
               oldArray[0] = newString;
               return oldArray;
            }
         }
         if (oldArray.length < nStored)
         {
            String[] result = new String[oldArray.length + 1];
            result[0] = newString;
            for (int i = 0; i < oldArray.length; i++)
               result[i + 1] = oldArray[i];
            return result;
         }
         else
         {
            for (int i = nStored-1; i > 0; i--)
               oldArray[i] = oldArray[i-1];
            oldArray[0] = newString;
            return oldArray;
         }
      }
      else
      {
         oldArray = new String[1];
         oldArray[0] = newString;
         return oldArray;
      }
   }
   
   /**
    * Getter for property key.
    * @return Value of property key.
    */
   public String getKey()
   {
      return m_key;
   }
   
   /**
    * Setter for property key.
    * @param key New value of property key.
    */
   public void setKey(String key)
   {
      m_key = key;
      if (key != null && m_prop != null) init(PropertyUtilities.getStringArray(m_prop,key,null));
   }
   
   /**
    * Getter for property numberOfItems.
    * @return Value of property numberOfItems.
    */
   public int getNumberOfItems()
   {
      return m_nItems;
   }
   
   /**
    * Setter for property numberOfItems.
    * @param numberOfItems New value of property numberOfItems.
    */
   public void setNumberOfItems(int numberOfItems)
   {
      m_nItems = numberOfItems;
   }
   
   /**
    * Getter for property clickDefault.
    * @return Value of property clickDefault.
    */
   public boolean isClickDefault()
   {
      return m_clickDefault;
   }
   
   /**
    * Setter for property clickDefault.
    * @param clickDefault New value of property clickDefault.
    */
   public void setClickDefault(boolean clickDefault)
   {
      m_clickDefault = clickDefault;
   }
   
   private int m_minWidth = -1, m_maxWidth = -1; // -1 indicates use default
   private int m_nItems;
   private String[] m_dropDownItems;
   private String m_key = null;
   private JTextField m_textField;
   private ChangeEvent m_changeEvent = null;
   private Properties m_prop = null;
   private boolean m_clickDefault;
   private MyListener myListener = new MyListener();
   
   private class MyListener implements KeyListener, DocumentListener
   {
      public void keyReleased(KeyEvent e){}
      public void keyTyped(KeyEvent e){}
      public void keyPressed(KeyEvent e)
      {
         if (e.getSource() != m_textField) return; // avoid cases where this listens to another by accident
         if (e.getKeyCode() == KeyEvent.VK_ENTER)
         {
            if (m_clickDefault)
            {
               JButton b = getRootPane().getDefaultButton();
               if (b != null) b.doClick();
            }
         }
      }
      
      public void changedUpdate(DocumentEvent e)
      {
         fireStateChanged();
         
      }
      public void insertUpdate(DocumentEvent e)
      {
         fireStateChanged();
         
      }
      public void removeUpdate(DocumentEvent e)
      {
         fireStateChanged();
         
      }   
   }
}
