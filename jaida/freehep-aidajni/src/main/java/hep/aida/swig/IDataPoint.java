/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 1.3.31
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package hep.aida.swig;
 
import hep.aida.jni.AIDAJNIUtil;

public class IDataPoint implements hep.aida.IDataPoint {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  public IDataPoint(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(hep.aida.IDataPoint obj) {
    if (obj instanceof IDataPoint) {
      return (obj == null) ? 0 : ((IDataPoint)obj).swigCPtr;
    } else {
      long cPtr = AIDAJNI.new_IDataPoint();
      // FIXME, memory leak if Java class gets finalized, since C++ director is not freed.
      AIDAJNI.IDataPoint_director_connect(obj, cPtr, true, true);
      return cPtr;
    }
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if(swigCPtr != 0 && swigCMemOwn) {
      swigCMemOwn = false;
      AIDAJNI.delete_IDataPoint(swigCPtr);
    }
    swigCPtr = 0;
  }

  protected void swigDirectorDisconnect() {
    swigCMemOwn = false;
    delete();
  }

  public void swigReleaseOwnership() {
    swigCMemOwn = false;
    AIDAJNI.IDataPoint_change_ownership(this, swigCPtr, false);
  }

  public void swigTakeOwnership() {
    swigCMemOwn = true;
    AIDAJNI.IDataPoint_change_ownership(this, swigCPtr, true);
  }

  public int dimension() {
    return AIDAJNI.IDataPoint_dimension(swigCPtr, this);
  }

  public hep.aida.IMeasurement coordinate(int coord) {
    long cPtr = AIDAJNI.IDataPoint_coordinate(swigCPtr, this, coord);
    return (cPtr == 0) ? null : new IMeasurement(cPtr, false);
  }

  public IDataPoint() {
    this(AIDAJNI.new_IDataPoint(), true);
    AIDAJNI.IDataPoint_director_connect(this, swigCPtr, swigCMemOwn, true);
  }

}
