// Copyright 2001, FreeHEP.
package org.freehep.postscript;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Objects for PostScript Processor, as defined in 3.3 Data Types and Objects
 *
 * @author Mark Donszelmann
 * @version $Id: PSDataTarget.java 8958 2006-09-12 23:37:43Z duns $
 */
public interface PSDataTarget {
    
    public OutputStream getOutputStream();
    public void write(int b, boolean secure) throws IOException;
}
