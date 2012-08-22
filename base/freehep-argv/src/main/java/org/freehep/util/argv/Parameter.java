// Copyright 2004, FreeHEP.
package org.freehep.util.argv;

import java.util.List;

/**
 * Interface for parameters that may be registered and parsed by the
 * ArgumentParser.
 */

public interface Parameter {
    /**
     * Must check  for a parameter.
     * This method must return the number of arguments belonging 
     * to this parameter (normally one, but more for lists).
     *
     * ArgumentParser.parse( values ) will invoke this method once
     * for each possible starting position of this parameter
     * in values.
     */

    public int parse( List values ) throws MissingArgumentException, ArgumentFormatException;

    /**
     * Must return name of the parameter.
     */
    public String getName();

    /**
     * Must return a description of the usage of this parameter.
     */

    public String getUsage();
};
