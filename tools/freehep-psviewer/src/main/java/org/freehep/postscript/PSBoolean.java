// Copyright 2001, FreeHEP.
package org.freehep.postscript;

/**
 * Objects for PostScript Processor, as defined in 3.3 Data Types and Objects
 *
 * @author Mark Donszelmann
 * @version $Id: PSBoolean.java 8958 2006-09-12 23:37:43Z duns $
 */
public class PSBoolean extends PSSimple {
    protected boolean value;
    
    public PSBoolean(boolean b) {
        super("boolean", true);
        value = b;
    }
    
    public boolean execute(OperandStack os) {
        os.push(this);
        return true;
    }
    
    public String getType() {
        return "booleantype";
    }

    public boolean getValue() {
        return value;
    }
    
    public Object clone() {
        return new PSBoolean(value);
    }
    
    public int hashCode() {
    	return value ? 1231 : 1237;
    }
    
    public boolean equals(Object o) {
        if (o instanceof PSBoolean) {
            return (value = ((PSBoolean)o).getValue());
        }
        return false;
    }
    
    public String cvs() {
        return toString();
    }
    
    public String toString() {
        return (value) ? "true" : "false";
    }
}

