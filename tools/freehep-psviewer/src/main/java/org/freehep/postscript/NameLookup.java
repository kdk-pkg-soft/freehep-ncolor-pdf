// Copyright 2001, FreeHEP.
package org.freehep.postscript;


/**
 * Lookup Interface for PostScript Processor
 *
 * @author Mark Donszelmann
 * @version $Id: NameLookup.java 8958 2006-09-12 23:37:43Z duns $
 */
public interface NameLookup {

    public PSObject lookup(PSObject key);
}