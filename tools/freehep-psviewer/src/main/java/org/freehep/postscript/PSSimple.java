// Copyright 2001, FreeHEP.
package org.freehep.postscript;

/**
 * Objects for PostScript Processor, as defined in 3.3 Data Types and Objects
 *
 * @author Mark Donszelmann
 * @version $Id: PSSimple.java 8958 2006-09-12 23:37:43Z duns $
 */
public abstract class PSSimple extends PSObject {
    
    public PSSimple(String name, boolean literal) {
        super(name, literal);
    }
    
    public PSObject copy() {
        return (PSObject)clone();
    }
}

