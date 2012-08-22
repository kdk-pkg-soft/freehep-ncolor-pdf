// Copyright FreeHEP, 2005-2007.
package org.freehep.maven.nar;

/**
 * C compiler tag
 *
 * @author <a href="Mark.Donszelmann@slac.stanford.edu">Mark Donszelmann</a>
 * @version $Id: C.java 13014 2007-07-17 14:21:11Z duns $
 */
public class C extends Compiler { 
    
	public C() {
	}
	
    public String getName() {
        return "c";
    }   
}
