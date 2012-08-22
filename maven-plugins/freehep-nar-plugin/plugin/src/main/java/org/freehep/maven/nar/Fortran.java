// Copyright FreeHEP, 2005-2007.
package org.freehep.maven.nar;

/**
 * Fortran compiler tag
 *
 * @author <a href="Mark.Donszelmann@slac.stanford.edu">Mark Donszelmann</a>
 * @version $Id: Fortran.java 13324 2007-09-12 18:18:23Z duns $
 */
public class Fortran extends Compiler {
  
	public Fortran() {
	}
	
    public String getName() {
        return "fortran";
    }         
}
