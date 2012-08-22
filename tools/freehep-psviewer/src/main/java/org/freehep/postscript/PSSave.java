// Copyright 2001, FreeHEP.
package org.freehep.postscript;

/**
 * Objects for PostScript Processor, as defined in 3.3 Data Types and Objects
 *
 * @author Mark Donszelmann
 * @version $Id: PSSave.java 8958 2006-09-12 23:37:43Z duns $
 */
public class PSSave extends PSComposite {
    // FIXME
    Object value;

    public PSSave() {
        super("save", true);
    }
    
    public boolean execute(OperandStack os) {
        os.push(this);
        return true;
    }
    
    public String getType() {
        return "savetype";
    }

    public int hashCode() {
        return value.hashCode();
    }

    public boolean equals(Object o) {
        if (o instanceof PSSave) {
            return (value == ((PSSave)o).value);
        }
        return false;
    }

    public Object clone() {
        return new PSSave();
    }
    
    public PSObject copy() {
        return new PSSave();
    }
    
    public String cvs() {
        return toString();
    }
    
    public String toString() {
        return "--"+name+"--";
    }
}

