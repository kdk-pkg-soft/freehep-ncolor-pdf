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

from sys import stdout
from types import StringTypes

class ITestObjects: 
    """
    /**
     * TestInterface to test the aid compiler.
     *
     * @author Mark Donszelmann
     */
    """

    def returnConstReferenceThrows(self):
        """
            /**
             * return const string reference method throwing exceptions
             *
             * @raises IllegalArgumentException in case of argument error
             * @raises IOException in case of IO error
             * @return name
             */
        """

        raise NotImplementedError

    def mv(self, oldPath, newPath = "dummy"):
        """
            /**
             * Method with 2 parameters of which 1 default.
             *
             * @param oldPath old path for file
             * @param newPath new path for file
             */
        """

        raise NotImplementedError

    def mkdir(self, path):
        """
            /**
             * Method throwing exception
             *
             * @param path path to create
             * @raises IllegalArgumentException in case of argument error
             */
        """

        raise NotImplementedError

    def cd(self, path = "~"):
        """
            /**
             * Method with 1 default parameter
             *
             * @param path to change directory to
             * @return True on succes
             */
        """

        raise NotImplementedError

    def returnConstReferenceConst(self):
        """
            /**
             * return const string reference const method
             *
             * @return name
             */
        """

        raise NotImplementedError

    def returnColor(self):
        """
            /**
             * return Color method
             *
             * @return name
             */
        """

        raise NotImplementedError

    def returnObject(self):
        """
            /**
             * return string method
             *
             * @return name
             */
        """

        raise NotImplementedError

    def returnPointer(self):
        """
            /**
             * return string pointer method
             *
             * @return name
             */
        """

        raise NotImplementedError

    def ls(self, path = ".", recursive = False, os = stdout):
        """
            /**
             * Method with 3 default parameters and special init values
             *
             * @param path path to list
             * @param recursive list files recursively
             * @param os output stream to list file to
             */
        """

        raise NotImplementedError

    def returnConstReferenceConstThrows(self):
        """
            /**
             * return const string reference const method throwing exceptions
             *
             * @raises IllegalArgumentException in case of argument error
             * @return name of something
             */
        """

        raise NotImplementedError

    def returnReference(self):
        """
            /**
             * return string reference method
             *
             * @return name
             */
        """

        raise NotImplementedError

    def returnConstReference(self):
        """
            /**
             * return const string reference method
             *
             * @return name
             */
        """

        raise NotImplementedError

    def find_Color(self, color):
        """
            /**
             * Method with 1 parameter
             *
             * @param path path to find
             * @return name
             */
        """

        raise NotImplementedError

    def find_StringTypes(self, path):
        """
            /**
             * Method with 1 parameter
             *
             * @param path path to find
             * @return name
             */
        """

        raise NotImplementedError

    def find(self, arg1 = None):
        """Dispatch method for the 'find' routine.
        This method takes a maximum number of arguments = 1
        Look at the individual methods with name 'find_...' for documentation.
        @throws TypeError if number of parameters incorrect or types incompatible.
        """

        if isinstance(arg1, Color):
            self.find_Color(arg1)
        elif isinstance(arg1, StringTypes):
            self.find_StringTypes(arg1)
        else:
            raise TypeError

# end of class or interface

