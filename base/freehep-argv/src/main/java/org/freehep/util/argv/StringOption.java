// Copyright (C) 2001 - 2007 by Oliver Goldman
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
 * A String option for use with ArgumentParser. A String option may have a default
 * value. If specified on the command line, a string option takes the value of the
 * argument immediately following its flag.
 */

public class StringOption implements Option {
    private String flag;
    private String shortCut;
    private String name;
    private String desc;
    private String defaultValue;
    private String value = null;
    private boolean bailOut;

    /**
     * Initialize a new String argument with the given flag and description but without
     * a default value.
     */

    public StringOption( String flag, String name, String description ) {
        this(flag, name, null, description);
    }

    public StringOption( String flag, String name, String defaultValue, String description ) {
        this(flag, null, name, defaultValue, description);
    }
    
    public StringOption( String flag, String shortCut, String name, String defaultValue, String description ) {
    	this(flag, shortCut, name, defaultValue, description, false);
    }
    
    public StringOption( String flag, String shortCut, String name, String defaultValue, String description, boolean bailOut ) {
        this.flag = flag;
        this.shortCut = shortCut;
        this.name = name;
        this.desc = description;
        this.value = defaultValue;
        this.defaultValue = defaultValue;
        this.bailOut = bailOut;
    }

    /**
     * Return the string value of this argument, which may be null. Returns the
     * default value if the value was not set when the command line was parsed.
     */

    public String getValue() {
        return value;
    }

    /**
     * Parsing method invoked by ArgumentParser.
     */

    public int parse( List values ) throws MissingArgumentException, BailOutException {
        if( values.get( 0 ).equals( flag ) || values.get(0).equals(shortCut)) {
            if( values.size() == 1 ) {
                throw new MissingArgumentException( flag+": expects '"+name+"' of type <string>" );
            }

            value = (String)( values.get( 1 ));
            if (bailOut) throw new BailOutException();
            return 2;
        }
        return 0;
    }

    public String getOption() {
        StringBuffer s = new StringBuffer(flag);
        s.append(" <");
        s.append(name);        
        if( defaultValue != null ) {
            s.append( "=" );
            s.append( defaultValue );
        }
        s.append(">");
        return s.toString();
    }

    public String getUsage() {
        return desc;
    }
}
