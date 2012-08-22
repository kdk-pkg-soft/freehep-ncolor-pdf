// Copyright (C) 2001 - 2002 by Oliver Goldman
// All Rights Reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification are permitted.
//
// Additional information available at http://software.charlie-dog.com.

//package com.charliedog.argv;
package org.freehep.util.argv;

import java.util.List;

/**
 * A Boolean option for use with ArgumentParser. It is false unless the flag is
 * found in the command line, in which case it is true.
 */

public class BooleanOption implements Option {
    private String flag;
    private String shortCut;
    private String desc;
    private boolean value = false;
    private boolean bailOut;

    public BooleanOption( String flag, String description ) {
        this(flag, null, description);
    }

    public BooleanOption( String flag, String description, boolean bailOut ) {
        this(flag, null, description, bailOut);
    }

    public BooleanOption( String flag, String shortCut, String description ) {
        this(flag, shortCut, description, false);
    }

    public BooleanOption( String flag, String shortCut, String description, boolean bailOut ) {
        this.flag = flag;
        this.shortCut = shortCut;
        this.desc = description;
        this.bailOut = bailOut;
    }

    /**
     * Returns the boolean value of this option. By default, the option's
     * value is false; it is true if the flag was found when the command line
     * was parsed.
     */

    public boolean getValue() {
        return value;
    }

    /**
     * Parsing method invoked by ArgumentParser.
     */

    public int parse( List values ) throws BailOutException {
        if( values.get( 0 ).equals( flag ) || values.get(0).equals(shortCut)) {
            value = true;
            if (bailOut) throw new BailOutException();
            return 1;
        }
        return 0;
    }

    public String getOption() {
        return flag;
    }

    public String getUsage() {
        return desc;
    }
}
