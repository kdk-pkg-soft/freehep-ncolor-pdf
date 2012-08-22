/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 1.3.31
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package hep.aida.swig;
 
import hep.aida.jni.AIDAJNIUtil;

public class IFitResult implements hep.aida.IFitResult {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  public IFitResult(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(hep.aida.IFitResult obj) {
    if (obj instanceof IFitResult) {
      return (obj == null) ? 0 : ((IFitResult)obj).swigCPtr;
    } else {
      long cPtr = AIDAJNI.new_IFitResult();
      // FIXME, memory leak if Java class gets finalized, since C++ director is not freed.
      AIDAJNI.IFitResult_director_connect(obj, cPtr, true, true);
      return cPtr;
    }
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if(swigCPtr != 0 && swigCMemOwn) {
      swigCMemOwn = false;
      AIDAJNI.delete_IFitResult(swigCPtr);
    }
    swigCPtr = 0;
  }

  protected void swigDirectorDisconnect() {
    swigCMemOwn = false;
    delete();
  }

  public void swigReleaseOwnership() {
    swigCMemOwn = false;
    AIDAJNI.IFitResult_change_ownership(this, swigCPtr, false);
  }

  public void swigTakeOwnership() {
    swigCMemOwn = true;
    AIDAJNI.IFitResult_change_ownership(this, swigCPtr, true);
  }

  public boolean isValid() {
    return AIDAJNI.IFitResult_isValid(swigCPtr, this);
  }

  public int fitStatus() {
    return AIDAJNI.IFitResult_fitStatus(swigCPtr, this);
  }

  public hep.aida.IFunction fittedFunction() {
    return new IFunction(AIDAJNI.IFitResult_fittedFunction(swigCPtr, this), false);
  }

  public double quality() {
    return AIDAJNI.IFitResult_quality(swigCPtr, this);
  }

  public int ndf() {
    return AIDAJNI.IFitResult_ndf(swigCPtr, this);
  }

  public double covMatrixElement(int i, int j) {
    return AIDAJNI.IFitResult_covMatrixElement(swigCPtr, this, i, j);
  }

  public String fitMethodName() {
    return AIDAJNI.IFitResult_fitMethodName(swigCPtr, this);
  }

  public String engineName() {
    return AIDAJNI.IFitResult_engineName(swigCPtr, this);
  }

  public String dataDescription() {
    return AIDAJNI.IFitResult_dataDescription(swigCPtr, this);
  }

  public String[] constraints() {
    return AIDAJNIUtil.toStringArray(AIDAJNI.IFitResult_constraints(swigCPtr, this));
  }

  public hep.aida.IFitParameterSettings fitParameterSettings(String name) {
    long cPtr = AIDAJNI.IFitResult_fitParameterSettings(swigCPtr, this, name);
    return (cPtr == 0) ? null : new IFitParameterSettings(cPtr, false);
  }

  public double[] fittedParameters() {
    return AIDAJNI.IFitResult_fittedParameters(swigCPtr, this);
  }

  public String[] fittedParameterNames() {
    return AIDAJNIUtil.toStringArray(AIDAJNI.IFitResult_fittedParameterNames(swigCPtr, this));
  }

  public double fittedParameter(String name) {
    return AIDAJNI.IFitResult_fittedParameter(swigCPtr, this, name);
  }

  public double[] errors() {
    return AIDAJNI.IFitResult_errors(swigCPtr, this);
  }

  public double[] errorsPlus() {
    return AIDAJNI.IFitResult_errorsPlus(swigCPtr, this);
  }

  public double[] errorsMinus() {
    return AIDAJNI.IFitResult_errorsMinus(swigCPtr, this);
  }

  public IFitResult() {
    this(AIDAJNI.new_IFitResult(), true);
    AIDAJNI.IFitResult_director_connect(this, swigCPtr, swigCMemOwn, true);
  }

}