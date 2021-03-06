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

class ITestCollections: 
    """
    /**
     * TestInterface to test the aid compiler.
     *
     * @author Mark Donszelmann
     */
    """

    def mkdir(self, dummy):
        """
            /**
             * Method throwing exception
             *
             * @param path path to create
             * @raises IllegalArgumentException in case of argument error
             */
        """

        raise NotImplementedError

    def parameterizedMethod(self, map):
        """
            /**
             * Method with parameterized types
             *
             * @param map of key value pairs
             * @return collection of strings
             */
        """

        raise NotImplementedError

# end of class or interface

