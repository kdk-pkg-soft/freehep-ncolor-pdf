// Copyright 2004, FreeHEP.
package org.freehep.util.argv;


/**
 * Exception to handle options such as -version or -help which need to bail out from 
 * the parsing loop.
 *
 * @author Mark Donszelmann
 * @version $Id: BailOutException.java 8584 2006-08-10 23:06:37Z duns $
 */ 
public class BailOutException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8748945748952662967L;

	public BailOutException() {
        super();
    }
}
