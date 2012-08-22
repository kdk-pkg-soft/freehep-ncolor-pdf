package hep.io.hbook;

/**
 * Interface for accessing arrays stored in PAW ntuples.
 * 
 * Subclasses of PawArray can be used for accessing different types (Double, Integer etc).
 * Arrays of arbitrary dimension are supported.
 * 
 * <b>Note</b> for compatibility with Fortran/PAW all arrays are indexed from 1
 */
public interface PawArray
{
   /**
    * Get the size of a particular dimension of the array.
    * 
    * For a PAW array declare Integer(100,200) getDimension(0) will return 100
    * 
    * @param index The index of the array (0 based)
    * @return The size of the dimension corresponding to index
    */
   public int getDimension(int index);

   /**
    * Get the number of dimensions of the array.
    * 
    * For a PAW array declared Integer(100,200) this method will return 2
    */
   public int getNDimensions();
}