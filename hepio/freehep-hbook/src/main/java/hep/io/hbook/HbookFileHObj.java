/*
 * HbookFileHObj.java
 *
 * Created on October 31, 2002, 1:32 PM
 */

package hep.io.hbook;

class HbookFileHObj extends CompositeHbookObject
{
   private String root;
   private int lun;

   HbookFileHObj(String filename, int lun, String root)
   {
      super(filename);
      this.root = root;
      this.lun = lun;
   }

   public void close()
   {
      super.close();
      Hbook.close(root, lun);
   }

   String getDirectoryPath(String path)
   {
      return "//" + root + "/" + path;
   }
}
