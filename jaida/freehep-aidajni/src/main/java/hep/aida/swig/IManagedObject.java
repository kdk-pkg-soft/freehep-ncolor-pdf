/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 1.3.31
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package hep.aida.swig;
 
import hep.aida.jni.AIDAJNIUtil;

public class IManagedObject implements hep.aida.IManagedObject {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  public IManagedObject(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(hep.aida.IManagedObject obj) {
    if (obj instanceof IManagedObject) {
      return (obj == null) ? 0 : ((IManagedObject)obj).swigCPtr;
    } else {
      long cPtr = AIDAJNI.new_IManagedObject();
      // FIXME, memory leak if Java class gets finalized, since C++ director is not freed.
      AIDAJNI.IManagedObject_director_connect(obj, cPtr, true, true);
      return cPtr;
    }
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if(swigCPtr != 0 && swigCMemOwn) {
      swigCMemOwn = false;
      AIDAJNI.delete_IManagedObject(swigCPtr);
    }
    swigCPtr = 0;
  }

  protected void swigDirectorDisconnect() {
    swigCMemOwn = false;
    delete();
  }

  public void swigReleaseOwnership() {
    swigCMemOwn = false;
    AIDAJNI.IManagedObject_change_ownership(this, swigCPtr, false);
  }

  public void swigTakeOwnership() {
    swigCMemOwn = true;
    AIDAJNI.IManagedObject_change_ownership(this, swigCPtr, true);
  }

  public String name() {
    return AIDAJNI.IManagedObject_name(swigCPtr, this);
  }

  public IManagedObject() {
    this(AIDAJNI.new_IManagedObject(), true);
    AIDAJNI.IManagedObject_director_connect(this, swigCPtr, swigCMemOwn, true);
  }

}
