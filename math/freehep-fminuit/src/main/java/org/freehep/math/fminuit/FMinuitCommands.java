// Copyright 2006, FreeHEP.
package org.freehep.math.fminuit;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 *  Minuit commands.
 *
 * @author Tony Johnson, Victor Serbo, Max Turri, Mark Donszelmann
 *
 */
public class FMinuitCommands {
    
    /**
     * The possible status of the covariance matrix
     *
     */
    protected static final int MATRIX_NOT_CALCULATED  = 0;
    protected static final int MATRIX_DIAGONAL_APPROX = 1;
    protected static final int MATRIX_FULL_FORCED_POS = 2;
    protected static final int MATRIX_FULL_ACCURATE   = 3;
    
    /**
     * The printout levels for the minimizer output are the following:
     *
     */
    protected static final int NO_OUTPUT       = -1;
    protected static final int MINIMAL_OUTPUT  = 0;
    protected static final int NORMAL_OUTPUT   = 1;
    protected static final int DETAILED_OUTPUT = 2;
    protected static final int MAXIMAL_OUTPUT  = 3;
    
    /**
     * The different types of minimization.
     *
     */
    protected static final int SIMPLEX_MIN  = 0;
    protected static final int MIGRAD_MIN   = 1;
    protected static final int MINIMIZE_MIN = 2;
    
    private static boolean isLibLoaded = false;
    private static FMinuitFunction function;  
    
    /**
     * Initialize minuit.
     * @param inputUnit  Unit number for input to Minuit (default 5)
     * @param outputUnit Unit number for output from Minuit (default 6)
     * @param saveUnit   Unit number to be used for SAVE command (default 7)
     *
     */
    protected static native void jmninit(int inputUnit, int outputUnit, int saveUnit);
    
    /**
     * Set the title of the minimization session.
     * The title maximum length is 50 characters.
     * @param title The title.
     *
     */
    protected static native void jmnseti(String title);
    
    /**
     * Define a new fit parameter.
     * @param parNum   The parameter number (+1) as referenced by the IFunction to be
     *                 minimized.
     * @param parName  The parameter's name. (maximum 10 characters).
     * @param parValue The parameter's starting value.
     * @param stepSize Starting step size or approximate error.
     * @param minValue The parameter's lower limit.
     * @param maxValue The parameter's upper limit. If the upper and the lower limits are set to 0,
     *                 the parameter is considered unbounded.
     * @return Error code. 0 if no error, > 0 if request failed.
     *
     */
    protected static native int  jmnparm(int parNum, String parName, double parValue, double stepSize, double minValue, double maxValue);
    
    /**
     * Execute a Minuit command, the command is executed on the current function.
     * @param command The Minuit command to be executed.
     * @param argList Array containing the numeric arguments to the command.
     * @param nArg    The number of arguments specified.
     * @return Error code. 0 if no error, > 0 otherwise.
     *
     */
    protected static native int  jmnexcm(String command, double[] argList, int nArg);
    
    /**
     * Get the current value of a parameter.
     * @param parNum  The parameter's number (+1) as referenced in the function to be minimized.
     * @param parName The parameter's name (it is a one dimensional array);
     * @param parVals The parameter's values. This array contains four entries: the
     *                current value of the parameter, the current error on the parameter,
     *                the parameter's lower and upper bound (both 0 if parameter is unbounded).
     * @return Internal parameter's number (as used by the fit); 0 if parameter is a constant,
     *         a negative number if parameter is not defined.
     *
     */
    protected static native int  jmnpout(int parNum, String[] parName, double[] parVals);
    
    /**
     * Get the current status of minimization.
     * @param vals The values of the minimization. This array contains the following three
     *             entries: the current best value of the function, the estimated vertical distance
     *             to the minimum, the value of UP defining the errors.
     * @param pars The parameters information. This array contains the following two entries:
     *             the number of variables in the fit, the highest parameter number (as seen
     *             by the function to be minimized).
     * @return The status of the fit telling at what stage the covariance matrix is:
     *         0 not calculated, 1 diagonal approximation, 2 full matrix but forced positive-definite,
     *         3 full accurate matrix.
     *
     */
    protected static native int  jmnstat(double[] vals, int[] pars);
    
    /**
     * Get the covariance matrix.
     * @param errorMatrix The error matrix. It is an array of length nDim*nDim
     * @param nDim        The dimension of the error matrix (at least the number of free variables).
     *
     */
    protected static native void jmnemat(double[] errorMatrix, int nDim);
    
    /**
     * Get the current parameter's errors.
     * @param parNum The parameter's number; if positive it is the number as referenced
     *               by the function to be minimized, if negative it is the negative of the
     *               parameter number as used within Minuit (the internal representation)
     * @param errors The parameter's errors. This array has four entries: the positive MINOS
     *               error, the negative MINOS error, the parabolic error from the error matrix
     *               and the global correlation coerricient.
     *
     */
    protected static native void jmnerrs(int parNum, double[] errors);
    
    /**
     * Get the two dimensional contour for the current configuration.
     * @param parNum1 The external number of the first parameter.
     * @param parNum2 The external number of the second parameter.
     * @param nPoints The number of points that are to be calculated on the contour (>4).
     * @param xPoints The array of the x-coordinate of the found points.
     * @param yPoints The array of the y-coordinate of the found points.
     * @param nFound  The number of points that have been found. This is a 1-dimensional array.
     *
     */
    protected static native void jmncont(int parNum1, int parNum2, int nPoints, double[] xPoints, double[] yPoints, int[] nFound);

    /**
     * Evaluate the function.
     * @param vars The values of the parameters where the function has to be evaluated.
     * @return     The value of the function.
     *
     */
    public static double evaluateFunction( double[] vars ) {
        return function.evaluateFunction( vars );
    }
    
    /**
     * Evaluate the function's derivatives.
     * @param vars The values of the parameters where the function's derivatives have to be evaluated.
     * @return     The array containing the function's derivatives.
     *
     */
    public static double[] evaluateDerivatives( double[] vars ) {
        return function.evaluateDerivatives( vars );
    }
    
    /**
     * Initialize the function.
     *
     */
    public static void initializeFunction() {
        function.initializeFunction();
    }
    
    /**
     * Finalize the function after the fit is over.
     *
     */
    public static void finalizeFunction() {
        function.finalizeFunction();
    }

    protected void loadAndInitialize(FMinuitFunction function) {
        if ( ! isLibLoaded ) {
            if (function == null) {
                throw new RuntimeException("Function cannot be null");
            }
            FMinuitCommands.function = function;
            
            Properties mavenProperties = new Properties();
            String propertyFileName = "/META-INF/maven/org.freehep/freehep-fminuit/pom.properties";
            try {
                InputStream is = FMinuitCommands.class.getResourceAsStream(propertyFileName);
                if (is == null) throw new RuntimeException("Cannot find "+propertyFileName+" in jar file");
                mavenProperties.load(is);
            } catch (IOException ioe) {
                throw new RuntimeException("Problem resolving name of the native library", ioe);
            }
            
            String libName = mavenProperties.getProperty("artifactId","undefined")+"-"+
                             mavenProperties.getProperty("version", "undefined");

            try {
                System.loadLibrary(libName);
            } catch ( UnsatisfiedLinkError ule ){
                throw new RuntimeException("Problem loading the library "+libName, ule);
            }
            isLibLoaded = true;
        }
    }    
}
