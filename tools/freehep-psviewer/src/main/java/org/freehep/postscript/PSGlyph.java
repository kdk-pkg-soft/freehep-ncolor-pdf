// Copyright 2001, FreeHEP.
package org.freehep.postscript;

import java.awt.geom.Rectangle2D;

/**
 * Object is only for storage and lookup in Dictionaries and Arrays, 
 * not to be executed.
 *
 * @author Mark Donszelmann
 * @version $Id: PSGlyph.java 8958 2006-09-12 23:37:43Z duns $
 */
public class PSGlyph extends PSSimple {
    double wx, wy, llx, lly, urx, ury;
    
    public PSGlyph() {
        super("glyph", true);
    }
        
    public boolean execute(OperandStack os) {
        // no-op
        return true;
    }
        
    public String getType() {
        return "glyph";
    }
        
    public double getWidth() {
        return wx;
    }
    
    public Rectangle2D getBounds2D() {
        return new Rectangle2D.Double(llx, lly, urx-llx, ury-lly);
    }
    
    public double getLSB() {
        return llx;
    }
    
    public double getRSB() {
        return wx - urx;
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
    
    public String cvs() {
        return toString();
    }
    
    public String toString() {
        return "--"+name+"--";
    }
}

