// -*- C++ -*-
// AID-GENERATED
// =========================================================================
// This class was generated by AID - Abstract Interface Definition          
// DO NOT MODIFY, but use the org.freehep.aid.Aid utility to regenerate it. 
// =========================================================================
#ifndef AIDTEST_JITESTPRIMITIVES_H
#define AIDTEST_JITESTPRIMITIVES_H 1

// Copyright 2002, SLAC, Stanford University, U.S.A.
// AID - Compiler Test File

#include <jni.h>

#include "AID/JAIDRef.h"
#include "AIDTEST/ITestPrimitives.h"

namespace AIDTEST {

/**
 * TestInterface to test the aid compiler.
 *
 * @author Mark Donszelmann
 */
class JITestPrimitives: public JAIDRef, public virtual ITestPrimitives {

private: 
    jmethodID returnBooleanOCZMethod;
    jmethodID returnIntOCIMethod;
    jmethodID returnDoubleOCDMethod;
    jmethodID returnFloatOCFMethod;

protected:
    inline JITestPrimitives() { };
    inline JITestPrimitives(const JITestPrimitives& r) { };
    inline JITestPrimitives& operator=(const JITestPrimitives&) { return *this; };

public: 
    /**
     * Default JNI Constructor
     */
    JITestPrimitives(JNIEnv *env, jobject object);

    /// Destructor.
    virtual ~JITestPrimitives();

    /**
     * primitive method
     *
     * @return boolean
     */
    virtual bool returnBoolean();

    /**
     * primitive method
     *
     * @return name
     */
    virtual int returnInt();

    /**
     * primitive method
     *
     * @return name
     */
    virtual double returnDouble();

    /**
     * primitive method
     *
     * @return name
     */
    virtual float returnFloat();
}; // class
}; // namespace AIDTEST
#endif /* ifndef AIDTEST_JITESTPRIMITIVES_H */
