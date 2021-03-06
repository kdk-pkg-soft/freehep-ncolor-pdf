// -*- C++ -*-
// AID-GENERATED
// =========================================================================
// This class was generated by AID - Abstract Interface Definition          
// DO NOT MODIFY, but use the org.freehep.aid.Aid utility to regenerate it. 
// =========================================================================
#ifndef AIDTEST_JITESTARRAYS_H
#define AIDTEST_JITESTARRAYS_H 1

// Copyright 2002, SLAC, Stanford University, U.S.A.
// AID - Compiler Test File

#include <jni.h>
#include <string>
#include <vector>

#include "AID/JAIDRef.h"
#include "AIDTEST/ITestArrays.h"

namespace AIDTEST {

/**
 * TestInterface to test the aid compiler.
 *
 * @author Mark Donszelmann
 */
class JITestArrays: public JAIDRef, public virtual ITestArrays {

private: 
    jmethodID returnDoubleArrayOCADMethod;
    jmethodID returnMultiDoubleArrayOCAADMethod;
    jmethodID listObjectNamesOLjava_lang_StringEZCAALorg_freehep_aid_test_EMethod;
    jmethodID mkdirOAICVMethod;
    jmethodID mkdirOAAICVMethod;
    jmethodID mkdirOAAALorg_freehep_aid_test_ECVMethod;
    jmethodID mkdirOAALorg_freehep_aid_test_ECVMethod;
    jmethodID mkdir2OAALorg_freehep_aid_test_ECVMethod;

protected:
    inline JITestArrays() { };
    inline JITestArrays(const JITestArrays& r) { };
    inline JITestArrays& operator=(const JITestArrays&) { return *this; };

public: 
    /**
     * Default JNI Constructor
     */
    JITestArrays(JNIEnv *env, jobject object);

    /// Destructor.
    virtual ~JITestArrays();

    /**
     * primitive array method
     *
     * @return double array
     */
    virtual std::vector<double>  returnDoubleArray();

    /**
     * primitive multi-array method
     *
     * @return multi double array
     */
    virtual std::vector<std::vector<double> >  returnMultiDoubleArray();

    /**
     * Method with array/vector return
     *
     * @param path path to objects
     * @param recursive list objects recursively
     * @return list of object names
     */
    virtual std::vector<std::string>  listObjectNames(const std::string & path, bool recursive);

    /**
     * Method throwing exception
     *
     * @param path path to create
     * @return false in case of argument error
     */
    virtual bool mkdir(std::vector<int>  dummy);

    /**
     * Method throwing exception
     *
     * @param path path to create
     * @return false in case of argument error
     */
    virtual bool mkdir(std::vector<std::vector<int> >  dummy);

    /**
     * Method throwing exception
     *
     * @param path path to create
     * @return false in case of argument error
     */
    virtual bool mkdir(std::vector<std::vector<std::string> >  dummy);

    /**
     * Method throwing exception
     *
     * @param path path to create
     * @return false in case of argument error
     */
    virtual bool mkdir(std::vector<std::string *>  dummy);

    /**
     * Method throwing exception
     *
     * @param path path to create
     * @return false in case of argument error
     */
    virtual bool mkdir2(std::vector<std::string>  & dummy);
}; // class
}; // namespace AIDTEST
#endif /* ifndef AIDTEST_JITESTARRAYS_H */
