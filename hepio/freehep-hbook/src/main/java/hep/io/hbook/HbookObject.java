package hep.io.hbook;

/** Abstract base class for all things read from an hbook file */
public abstract class HbookObject
{
   private String name;
   private CompositeHbookObject parent;

   /**
    * Create the root object (no parent)
    */
   HbookObject(String name)
   {
      this.name = name;
   }

   HbookObject(CompositeHbookObject parent, String name)
   {
      this(name);
      this.parent = parent;
      parent.addChild(name, this);
   }

   /** Get the name for this object
    * @return The name of this object
    */   
   public String getName()
   {
      return name;
   }

   void close()
   {
   }

   String getDirectoryPath(String path)
   {
      return parent.getDirectoryPath(name + "/" + path);
   }

   String getDirectoryPath()
   {
      String s = getDirectoryPath("");
      return s.substring(0, s.length() - 1); // remove trailing /
   }
   CompositeHbookObject getParent()
   {
      return parent;
   }
}