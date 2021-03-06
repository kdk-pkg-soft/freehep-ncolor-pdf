// AID-GENERATED
// =========================================================================
// This class was generated by AID - Abstract Interface Definition          
// DO NOT MODIFY, but use the org.freehep.aid.Aid utility to regenerate it. 
// =========================================================================

// Copyright 2002, SLAC, Stanford University, U.S.A.
// AID - Compiler Test File
package org.freehep.aid.test;


/**
 * TestInterface to test the aid compiler.
 *
 * @author Mark Donszelmann
 */
public interface ITestArrays {

    /**
     * primitive array method
     *
     * @return double array
     */
    public double[] returnDoubleArray();

    /**
     * primitive multi-array method
     *
     * @return multi double array
     */
    public double[][] returnMultiDoubleArray();

    /**
     * Method with array/vector return
     *
     * @return list of object names
     */
    public String[] listObjectNames();
    /**
     * Method with array/vector return
     *
     * @param path path to objects
     * @return list of object names
     */
    public String[] listObjectNames(String path);
    /**
     * Method with array/vector return
     *
     * @param path path to objects
     * @param recursive list objects recursively
     * @return list of object names
     */
    public String[] listObjectNames(String path, boolean recursive);

    /**
     * Method throwing exception
     *
     * @param path path to create
     * @throws IllegalArgumentException in case of argument error
     */
    public void mkdir(int[] dummy) throws IllegalArgumentException;

    /**
     * Method throwing exception
     *
     * @param path path to create
     * @throws IllegalArgumentException in case of argument error
     */
    public void mkdir(int[][] dummy) throws IllegalArgumentException;

    /**
     * Method throwing exception
     *
     * @param path path to create
     * @throws IllegalArgumentException in case of argument error
     */
    public void mkdir(String[][] dummy) throws IllegalArgumentException;

    /**
     * Method throwing exception
     *
     * @param path path to create
     * @throws IllegalArgumentException in case of argument error
     */
    public void mkdir(String[] dummy) throws IllegalArgumentException;

    /**
     * Method throwing exception
     *
     * @param path path to create
     * @throws IllegalArgumentException in case of argument error
     */
    public void mkdir2(String[] dummy) throws IllegalArgumentException;
} // class or interface

