// Copyright 2001, FreeHEP.
package org.freehep.postscript;


/**
 * Syntax Exception for PostScript Processor
 *
 * @author Mark Donszelmann
 * @version $Id: SyntaxException.java 8958 2006-09-12 23:37:43Z duns $
 */
public class SyntaxException extends Exception {

    private int line;

    public SyntaxException(int line, String msg) {
        super(msg);
        this.line = line;
    }
    
    public int getLineNo() {
        return line;
    }
}
