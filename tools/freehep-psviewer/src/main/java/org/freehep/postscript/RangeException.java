// Copyright 2004, FreeHEP.
package org.freehep.postscript;


/**
 * Range(Check) Exception for PostScript Processor
 *
 * @author Mark Donszelmann
 * @version $Id: RangeException.java 8958 2006-09-12 23:37:43Z duns $
 */
public class RangeException extends Exception {

    public RangeException() {
        super();
    }

    public RangeException(String msg) {
        super(msg);
    }
}
