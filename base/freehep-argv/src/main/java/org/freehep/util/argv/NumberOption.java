// Copyright (C) 2001 - 2002 by Oliver Goldman
// All Rights Reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted.
//
// Additional information available at http://software.charlie-dog.com.

//package com.charliedog.argv;
package org.freehep.util.argv;

import java.math.BigDecimal;
import java.util.List;

/**
 * A Number option for use with ArgumentParser. A Number option may have a
 * default value. If specified on the command line, a Number option takes the value
 * of the argument immediately following its flag.
 */

public class NumberOption implements Option {
    private String flag;
    private String shortCut;
    private String name;
    private String desc;
    private BigDecimal defaultValue;
    private BigDecimal value = null;

    public NumberOption( String flag, String name, String description ) {
        this(flag, null, name, null, description);
    }

    public NumberOption( String flag, String shortCut, String name, String description ) {
        this(flag, shortCut, name, null, description);
    }

    public NumberOption( String flag, String name, BigDecimal defaultValue, String description ) {
        this(flag, null, name, defaultValue, description);
    }

    public NumberOption( String flag, String shortCut, String name, BigDecimal defaultValue, String description ) {
        this.flag = flag;
        this.shortCut = shortCut;
        this.name = name;
        this.desc = description;
        this.value = defaultValue;
        this.defaultValue = defaultValue;
    }

    /**
     * Return the value of this argument, which may be null. Returns the
     * default value if the value was not set when the command line was parsed.
     */

    public BigDecimal getValue() {
        return value;
    }

    /**
     * Parsing method invoked by ArgumentParser.
     */

    public int parse( List values ) throws MissingArgumentException, ArgumentFormatException {
        if( values.get( 0 ).equals( flag ) || values.get(0).equals(shortCut)) {
            if( values.size() == 1 ) {
                throw new MissingArgumentException( flag+": expects '"+name+"' of type <number>" );
            }

            try {
                value = new BigDecimal( (String)values.get( 1 ));
            } catch (NumberFormatException nfe) {
                throw new ArgumentFormatException( flag+": '"+values.get(1)+"' not a <number>" );
            }
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
