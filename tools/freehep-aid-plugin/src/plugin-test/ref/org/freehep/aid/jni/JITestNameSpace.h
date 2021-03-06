// -*- C++ -*-
// AID-GENERATED
// =========================================================================
// This class was generated by AID - Abstract Interface Definition          
// DO NOT MODIFY, but use the org.freehep.aid.Aid utility to regenerate it. 
// =========================================================================
#ifndef AIDTEST_Dev_JITESTNAMESPACE_H
#define AIDTEST_Dev_JITESTNAMESPACE_H 1

// Copyright 2002, SLAC, Stanford University, U.S.A.
// AID - Compiler Test File

#include <jni.h>

#include "AIDTEST/ITestPrimitives.h"
#include "AIDTEST_Dev/ITestNameSpace.h"
#include "JITestInterface.h"

namespace AIDTEST {
namespace Dev {

class AIDTEST_Dev/ITestNameSpace;

/**
 * TestInterface to test the aid compiler.
 *
 * @author Mark Donszelmann
 */
class JITestNameSpace: public JITestInterface, public virtual ITestNameSpace {

private: 
    jmethodID returnPrimitivesOCLorg_freehep_aid_test_ITestPrimitivesEMethod;
    jmethodID instanceOCLorg_freehep_aid_test_dev_ITestNameSpaceEMethod;

protected:
    inline JITestNameSpace() { };
    inline JITestNameSpace(const JITestNameSpace& r) { };
    inline JITestNameSpace& operator=(const JITestNameSpace&) { return *this; };

public: 
    /**
     * Default JNI Constructor
     */
    JITestNameSpace(JNIEnv *env, jobject object);

    /// Destructor.
    virtual ~JITestNameSpace();

    virtual ITestPrimitives returnPrimitives();

    virtual ITestNameSpace instance();
}; // class
}; // namespace Dev
}; // namespace AIDTEST
#endif /* ifndef AIDTEST_Dev_JITESTNAMESPACE_H */
