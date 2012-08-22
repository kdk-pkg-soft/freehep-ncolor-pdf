// Copyright 2001, FreeHEP.
package org.freehep.postscript;

/**
 * Objects for PostScript Processor, as defined in 3.3 Data Types and Objects
 *
 * @author Mark Donszelmann
 * @version $Id: PSComposite.java 8958 2006-09-12 23:37:43Z duns $
 */
public abstract class PSComposite extends PSObject {
    public final static int UNLIMITED = 3;
    public final static int READ_ONLY = 2;
    public final static int EXECUTE_ONLY = 1;
    public final static int NONE = 0;
    
    protected int access = UNLIMITED;
    
    public PSComposite(String name, boolean literal) {
        super(name, literal);
    }
    
    public boolean accessWrite() {
        return access >= UNLIMITED;
    }
    
    public boolean accessRead() {
        return access >= READ_ONLY;
    }
    
    public boolean accessExecute() {
        return access >= EXECUTE_ONLY;
    }

    public boolean changeAccess(int newAccess) {
        if (newAccess > access) {
            return false;
        }
        access = newAccess;
        return true;
    }
        
}

