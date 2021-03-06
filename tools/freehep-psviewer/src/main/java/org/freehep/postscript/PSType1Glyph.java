// Copyright 2001, FreeHEP.
package org.freehep.postscript;

/**
 * Object is only for storage and lookup in Dictionaries and Arrays, 
 * not to be executed.
 *
 * @author Mark Donszelmann
 * @version $Id: PSType1Glyph.java 8958 2006-09-12 23:37:43Z duns $
 */
public class PSType1Glyph extends PSGlyph {
    private PSPackedArray proc;
        
    public PSType1Glyph(PSPackedArray proc, double wx, double lsb) {
        this.proc = proc;
        this.wx = wx;
        this.llx = lsb;
    }
                
    public PSPackedArray getProc() {
        return proc;
    }
    
    // FIXME    
    public int hashCode() {
        return 0;
    }

    // FIXME
    public boolean equals(Object o) {
        return false;
    }

    // FIXME
    public Object clone() {
        return null;
    }
}

