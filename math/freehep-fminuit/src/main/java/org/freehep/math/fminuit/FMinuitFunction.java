// Copyright 2006, FreeHEP.
package org.freehep.math.fminuit;

/**
 * Interface to couple funtion callbacks to for instance JAIDA
 * 
 * @author duns
 * @version $Id$
 */
public interface FMinuitFunction {
    public void initializeFunction();
    public double evaluateFunction( double[] vars );
    public double[] evaluateDerivatives( double[] vars );
    public void finalizeFunction();
}
