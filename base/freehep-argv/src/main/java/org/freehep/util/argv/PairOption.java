// Copyright (C) 2001 by Oliver Goldman
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
 * A Pair option for use with ArgumentParser. A Pair option has
 * two values, a source and a destination, and is useful for programs which
 * translate (or otherwise process) some input to produce some output. Source
 * and destination could be, for example, either file formats or file names.
 *
 * On the command line, a translate argument consists of a flag followed by
 * the source and destination arguments, in that order:
 * <PRE>
 *   -&lt;flag&gt; &lt;source&gt; &lt;dest&gt;
 * </PRE>
 */

public class PairOption implements Option {
    private String flag;
    private String shortCut;
    private String name1;
    private String name2;
    private String desc;
    private String source = null;
    private String destination = null;

    /**
     * Initialize a new Pair argument.
     */

    public PairOption( String flag, String name1, String name2, String description ) {
        this(flag, null, name1, name2, description);
    }
    
    public PairOption( String flag, String shortCut, String name1, String name2, String description ) {
        this.flag = flag;
        this.shortCut = shortCut;
        this.name1 = name1;
        this.name2 = name2;
        this.desc = description;
    }

    /**
     * Return the source value of this argument, which may be null.
     */

    public String getSource() {
        return source;
    }

    /**
     * Return the destination value of this argument, which may be null.
     */

    public String getDestination() {
        return destination;
    }

    /**
     * Parsing method invoked by ArgumentParser.
     */

    public int parse( List values ) throws MissingArgumentException {
        if( values.get( 0 ).equals( flag ) || values.get(0).equals(shortCut)) {
            if( values.size() < 3 ) {
                throw new MissingArgumentException( flag+": expects '"+name1+"' '"+name2+"' of type <string>" );
            }

            source = (String)( values.get( 1 ));
            destination = (String)( values.get( 2 ));
            return 3;
        }
        return 0;
    }

    public String getOption() {
        return flag+" <"+name1+"> <"+name2+">";
    }

    public String getUsage() {
        return desc;
    }
}
