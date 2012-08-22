package hep.io.hbook;

/** An array whose elements are of type double */
public interface PawDoubleArray extends PawArray
{
   /**
    * Convert the PAW array to a Java array. All of the element of the array are returned
    * as a single 1-dimensional Java array.
    */
   public double[] getAsJavaArray();

   /**
    * Convenience method for accessing elements of 1 dimension arrays
    * <b>Note</b> for compatibility with Fortran/PAW all arrays are indexed from 1
    */
   public double getDouble(int i);

   /**
    * Convenience method for accessing elements of 2 dimension arrays
    * <b>Note</b> for compatibility with Fortran/PAW all arrays are indexed from 1
    */
   public double getDouble(int i, int j);

   /**
    * Convenience method for accessing elements of 3 dimension arrays
    * <b>Note</b> for compatibility with Fortran/PAW all arrays are indexed from 1
    */
   public double getDouble(int i, int j, int k);

   /**
    * Access an element of an array of arbitrary dimension
    * @param i The index for the element to access
    * <b>Note</b> for compatibility with Fortran/PAW all arrays are indexed from 1
    */
   public double getDouble(int[] i);
}