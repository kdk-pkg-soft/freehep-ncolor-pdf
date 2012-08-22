// Copyright (C) 2001 - 2002 by Oliver Goldman
// All Rights Reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted.
//
// Additional information available at http://software.charlie-dog.com.

//package com.charliedog.argv;
package org.freehep.util.argv;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A List parameter for use with ArgumentParser. A List parameter consumes all values
 * it sees in the command line arguments and returns them as a list. The order of the
 * values is preserved. Because this parameter consumes all available values, it
 * should be registered as the last parameter added to an ArgumentParser instance.
 */

public class ListParameter implements Parameter {
    private String name;
    private String desc;
    private List value = Collections.EMPTY_LIST;

    /**
     * Initialize a new List parameter with the given description.
     */

    public ListParameter( String name, String description ) {
        this.name = name;
        this.desc = description;
    }

    /**
     * Returns the list of values collected by this parameter. The list may
     * be empty but is never null.
     */

    public List getValue() {
        return value;
    }

    /**
     * Parsing method invoked by ArgumentParser.
     */

    public int parse( List values ) {
		value = new LinkedList( values );
		return values.size();
    }

    public String getName() {
        return "["+name+"...]";
    }
    
    /**
     * Usage method invoked by ArgumentParser.
     */

    public String getUsage() {
        return desc;
    }
}
