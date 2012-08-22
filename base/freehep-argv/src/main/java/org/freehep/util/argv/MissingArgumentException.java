// Copyright 2004, FreeHEP.
package org.freehep.util.argv;


/**
 * 
 *
 * @author Mark Donszelmann
 * @version $Id: MissingArgumentException.java 8584 2006-08-10 23:06:37Z duns $
 */ 
public class MissingArgumentException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5296018960092550727L;

	public MissingArgumentException(String msg) {
        super(msg);
    }
}
