""" AID-GENERATED
=========================================================================
This class was generated by AID - Abstract Interface Definition          
DO NOT MODIFY, but use the org.freehep.aid.Aid utility to regenerate it. 
=========================================================================
"""

"""
// Copyright 2002, SLAC, Stanford University, U.S.A.
// AID - Compiler Test File
"""

from AIDTEST.ITestInterface import *

class ITestNameSpace(ITestInterface): 
    """
    /**
     * TestInterface to test the aid compiler.
     *
     * @author Mark Donszelmann
     */
    """

    def instance(self):
        raise NotImplementedError

    def returnPrimitives(self):
        raise NotImplementedError

# end of class or interface

