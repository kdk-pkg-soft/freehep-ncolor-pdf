// Copyright 2001-2004, FreeHEP.
package org.freehep.postscript;

/**
 * Objects for PostScript Processor, as defined in 3.3 Data Types and Objects
 *
 * @author Mark Donszelmann
 * @version $Id: PSComment.java 8958 2006-09-12 23:37:43Z duns $
 */
// Not strictly part of the PostScript standard
public class PSComment extends PSSimple {
    protected String value;
    
    public PSComment(String comment) {
        super("comment", false);
        value = comment;
    }
    
    public boolean execute(OperandStack os) {
        // ignore comments
        return true;
    }
    
    public String getType() {
        return "commenttype";
    }

    public String getValue() {
        return value;
    }
    
    public int hashCode() {
        return value.hashCode();
    }

    public boolean equals(Object o) {
        if (o instanceof PSComment) {
            return (value == ((PSComment)o).value);
        }
        return false;
    }

    public Object clone() {
        return new PSComment(value);
    }
    
    public String cvs() {
        return value;
    }
    
    public String toString() {
        return "%"+value;
    }
}

