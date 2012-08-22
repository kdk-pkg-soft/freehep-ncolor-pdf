// -*- C++ -*-
// AID-GENERATED
// =========================================================================
// This class was generated by AID - Abstract Interface Definition          
// DO NOT MODIFY, but use the org.freehep.aid.Aid utility to regenerate it. 
// =========================================================================
#ifndef AIDTEST_JITESTCOLLECTIONS_H
#define AIDTEST_JITESTCOLLECTIONS_H 1

// Copyright 2002, SLAC, Stanford University, U.S.A.
// AID - Compiler Test File

#include <jni.h>
#include <map>
#include <memory>
#include <string>
#include <vector>

#include "AID/JAIDRef.h"
#include "AIDTEST/ITestCollections.h"

namespace AIDTEST {

/**
 * TestInterface to test the aid compiler.
 *
 * @author Mark Donszelmann
 */
class JITestCollections: public JAIDRef, public virtual ITestCollections {

private: 
    jmethodID mkdirOLjava_util_CollectionECVMethod;
    jmethodID convertOLorg_freehep_aid_test_auto_ptrECLorg_freehep_aid_test_auto_ptrEMethod;
    jmethodID parameterizedMethodOLjava_util_MapECLjava_util_CollectionEMethod;
    jmethodID convertFromMainOICCLjava_util_CollectionEMethod;

protected:
    inline JITestCollections() { };
    inline JITestCollections(const JITestCollections& r) { };
    inline JITestCollections& operator=(const JITestCollections&) { return *this; };

public: 
    /**
     * Default JNI Constructor
     */
    JITestCollections(JNIEnv *env, jobject object);

    /// Destructor.
    virtual ~JITestCollections();

    /**
     * Method throwing exception
     *
     * @param path path to create
     * @return false in case of argument error
     */
    virtual bool mkdir(std::vector<std::string>  dummy);

    /**
     * Method with auto ptrs
     *
     * @param name
     * @return result
     */
    virtual std::auto_ptr<std::vector<std::string> >  convert(std::auto_ptr<std::string>  name);

    /**
     * Method with parameterized types
     *
     * @param map of key value pairs
     * @return collection of strings
     */
    virtual std::vector<std::string>  parameterizedMethod(std::map<std::string, std::string>  map);

    /**
     * Handle command line arguments
     *
     * @param argc number of arguments
     * @param arg array of arguments
     */
    virtual std::vector<std::string>  convertFromMain(int argc, char * * arg);
}; // class
}; // namespace AIDTEST
#endif /* ifndef AIDTEST_JITESTCOLLECTIONS_H */
