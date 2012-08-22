// AID-GENERATED
// =========================================================================
// This class was generated by AID - Abstract Interface Definition          
// DO NOT MODIFY, but use the org.freehep.aid.Aid utility to regenerate it. 
// =========================================================================

// Copyright 2002, SLAC, Stanford University, U.S.A.
// AID - Compiler Test File
package org.freehep.aid.test;

import java.util.Collection;
import java.util.Map;

/**
 * TestInterface to test the aid compiler.
 *
 * @author Mark Donszelmann
 */
public interface ITestCollections {

    /**
     * Method throwing exception
     *
     * @param path path to create
     * @throws IllegalArgumentException in case of argument error
     */
    public void mkdir(Collection/*<String>*/ dummy) throws IllegalArgumentException;

    /**
     * Method with parameterized types
     *
     * @param map of key value pairs
     * @return collection of strings
     */
    public Collection/*<ITestEmpty>*/ parameterizedMethod(Map/*<String, String>*/ map);

    /**
     * Handle command line arguments
     *
     * @param args array of arguments
     */
    public Collection/*<String>*/ convertFromMain(String[] args);
} // class or interface

