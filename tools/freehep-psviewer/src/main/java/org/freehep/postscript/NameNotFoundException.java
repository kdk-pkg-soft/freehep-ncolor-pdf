// Copyright 2001, FreeHEP.
package org.freehep.postscript;


/**
 * Name Not Found Exception for PostScript Processor
 *
 * @author Mark Donszelmann
 * @version $Id: NameNotFoundException.java 8958 2006-09-12 23:37:43Z duns $
 */
public class NameNotFoundException extends Exception {

    public NameNotFoundException(String msg) {
        super(msg);
    }
}
