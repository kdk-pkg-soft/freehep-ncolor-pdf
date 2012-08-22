// Copyright FreeHEP, 2005.
package org.freehep.maven.nar;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Compiles class files into c/c++ headers using "javah". 
 * Any class file that contains methods that were declared
 * "native" will be run through javah.
 *
 * @goal nar-javah
 * @phase compile
 * @author <a href="Mark.Donszelmann@slac.stanford.edu">Mark Donszelmann</a>
 * @version $Id: NarJavahMojo.java 13111 2007-07-24 04:02:00Z duns $
 */
public class NarJavahMojo extends AbstractCompileMojo {
    
    public void execute() throws MojoExecutionException, MojoFailureException {
    	if (shouldSkip()) return;
    	
        getJavah().execute();
    }    
}