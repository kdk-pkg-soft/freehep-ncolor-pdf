// Copyright (C) 2001 - 2002 by Oliver Goldman
// All Rights Reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted.
//
// Additional information available at http://software.charlie-dog.com.

//package com.charliedog.argv;
package org.freehep.util.argv;

import java.util.List;

/**
 * Interface for options that may be registered and parsed by the
 * ArgumentParser.
 */

public interface Option {
    /**
     * Must check whether values begins with this option.
     * If it does, this method must return number of arguments 
     * belonging to this option. Otherwise, it must return 0.
     *
     * ArgumentParser.parse( values ) will invoke this method once
     * for each possible starting position of this option
     * in values.
     */

    public int parse( List values ) throws MissingArgumentException, ArgumentFormatException, BailOutException;

    /**
     * Must return the flag and parameters of this option.
     */
    public String getOption();

    /**
     * Must return a description of the usage of this option.
     */

    public String getUsage();
};
