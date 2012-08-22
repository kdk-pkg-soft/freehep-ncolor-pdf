// Copyright 2001, FreeHEP.
package org.freehep.postscript;

import java.io.IOException;

/**
 * Objects for PostScript Processor, as defined in 3.3 Data Types and Objects
 *
 * @author Mark Donszelmann
 * @version $Id: PSTokenizable.java 8958 2006-09-12 23:37:43Z duns $
 */
public interface PSTokenizable {
    
    public PSObject token(boolean packingMode, NameLookup lookup) throws IOException, SyntaxException, NameNotFoundException;

}
