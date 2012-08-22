package hep.io.hbook;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/** An HbookObject which can have children. This class can represent
 * either a directory in a hbook file, in which case the children
 * will be the directory entries, or a tuple, in which case the
 * children will be tuple columns.
 */
public class CompositeHbookObject extends HbookObject
{
   final static int FOLDER = 0;
   final static int CWTUPLE = 1;
   final static int RWTUPLE = 2;
   
    CompositeHbookObject(String name) {
        super(name);
        type = FOLDER;
    }
    CompositeHbookObject(CompositeHbookObject parent, String name, int type) {
        super(parent,name);
        this.type = type;
    }
    void addChild(String name, HbookObject child) {
        indexesOfChildren.put(name, new Integer(children.size()));
        children.addElement(child);        
    }
    /** Get child by index
     * @param index The index of the object to retreive (0 to n-1)
     */    
    public final HbookObject getChild(final int index) {
        if (children == null) visitChildren();
        return (HbookObject) children.elementAt(index);
    }
    /** Get an object by name. For tuples the name is the column name. */    
    public final HbookObject getChild(String name) {
        if (children == null) visitChildren();
        Object o = indexesOfChildren.get(name);
        if ( o != null )
            return (HbookObject) children.elementAt(((Integer) o ).intValue());
        return null;
    }
    void close() {
        if (children != null) {
            Enumeration e = children.elements();
            while (e.hasMoreElements()) {
                HbookObject h = (HbookObject) e.nextElement();
                h.close();
            }
        }
    }
    /** Get an enumeration of the children. */    
    public Enumeration getChildren() {
        if (children == null) visitChildren();
        return children.elements();
    }
    public String toString() {
        if (children != null) return "Folder: "+getName()+" ("+children.size()+" elements)";
        else return "Folder: "+getName();
    }
    /** Get the number of children */    
    public final int nChildren() {
        if (children == null) visitChildren();
        return children.size();
    }
    /** Get the index of a named object. */    
    public final int getIndex(final String name) {
        if (children == null) visitChildren();
        Integer index = (Integer) indexesOfChildren.get(name);
        if ( index == null ) return -1;
        return index.intValue();
    }
    private void visitChildren()
    {
       children = new Vector();
       Hbook.visitChildren(this);
    }
    protected int type;
    private Vector children;
    private final Hashtable indexesOfChildren = new Hashtable();
}