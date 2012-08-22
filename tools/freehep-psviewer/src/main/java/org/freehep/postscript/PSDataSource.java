// Copyright 2001, FreeHEP.
package org.freehep.postscript;

import java.io.IOException;
import java.io.InputStream;

/**
 * Objects for PostScript Processor, as defined in 3.3 Data Types and Objects
 *
 * @author Mark Donszelmann
 * @version $Id: PSDataSource.java 10178 2006-12-08 09:03:07Z duns $
 */
public interface PSDataSource {

    public InputStream getInputStream();
    public int read() throws IOException;
    public void reset() throws IOException;
    public DSC getDSC();
}
