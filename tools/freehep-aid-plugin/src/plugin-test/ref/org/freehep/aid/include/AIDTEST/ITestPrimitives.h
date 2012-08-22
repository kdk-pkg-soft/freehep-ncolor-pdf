// -*- C++ -*-
// AID-GENERATED
// =========================================================================
// This class was generated by AID - Abstract Interface Definition          
// DO NOT MODIFY, but use the org.freehep.aid.Aid utility to regenerate it. 
// =========================================================================
#ifndef AIDTEST_ITESTPRIMITIVES_H
#define AIDTEST_ITESTPRIMITIVES_H 1

// Copyright 2002, SLAC, Stanford University, U.S.A.
// AID - Compiler Test File

#include "AID/AIDRef.h"

namespace AIDTEST {

/**
 * TestInterface to test the aid compiler.
 *
 * @author Mark Donszelmann
 */
class ITestPrimitives : virtual public AID::AIDRef {

public: 
    /// Destructor.
    virtual ~ITestPrimitives() { /* nop */; }

    /**
     * primitive method
     *
     * @return boolean
     */
    virtual bool returnBoolean() = 0;

    /**
     * primitive method
     *
     * @return int
     */
    virtual int returnInt() = 0;

    /**
     * primitive method
     *
     * @return double
     */
    virtual double returnDouble() = 0;

    /**
     * primitive method
     *
     * @return float
     */
    virtual float returnFloat() = 0;
}; // class
} // namespace AIDTEST
#endif /* ifndef AIDTEST_ITESTPRIMITIVES_H */