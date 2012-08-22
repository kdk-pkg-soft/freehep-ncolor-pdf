package hep.io.hbook;

/** An array whose elements are of type String */
public interface PawStringArray extends PawArray
{
   /**
    * Convert the PAW array to a Java array. All of the element of the array are returned
    * as a single 1-dimensional Java array.
    */
   public String[] getAsJavaArray();

   /**
    * Convenience method for accessing elements of 1 dimension arrays
    * <b>Note</b> for compatibility with Fortran/PAW all arrays are indexed from 1
    */
   public String getString(int i);

   /**
    * Convenience method for accessing elements of 2 dimension arrays
    * <b>Note</b> for compatibility with Fortran/PAW all arrays are indexed from 1
    */
   public String getString(int i, int j);

   /**
    * Convenience method for accessing elements of 3 dimension arrays
    * <b>Note</b> for compatibility with Fortran/PAW all arrays are indexed from 1
    */
   public String getString(int i, int j, int k);

   /**
    * Access an element of an array of arbitrary dimension
    * @param i The index for the element to access
    * <b>Note</b> for compatibility with Fortran/PAW all arrays are indexed from 1
    */
   public String getString(int[] i);
}