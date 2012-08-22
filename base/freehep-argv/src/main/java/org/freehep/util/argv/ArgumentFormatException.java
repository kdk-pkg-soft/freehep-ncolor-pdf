// Copyright 2004, FreeHEP.
package org.freehep.util.argv;


/**
 * 
 *
 * @author Mark Donszelmann
 * @version $Id: ArgumentFormatException.java 8584 2006-08-10 23:06:37Z duns $
 */ 
public class ArgumentFormatException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6677826455427890342L;

	public ArgumentFormatException(String msg) {
        super(msg);
    }
}
