// Copyright FreeHEP, 2005-2006.
package org.freehep.maven.nar;

import java.util.Iterator;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.plexus.archiver.manager.ArchiverManager;

/**
 * Unpacks NAR files. Unpacking happens in the local repository, 
 * and also sets flags on binaries and corrects static libraries.
 * 
 * @goal nar-unpack
 * @phase process-sources
 * @requiresProject
 * @requiresDependencyResolution
 * @author <a href="Mark.Donszelmann@slac.stanford.edu">Mark Donszelmann</a>
 * @version $Id: NarUnpackMojo.java 12779 2007-06-15 22:04:56Z duns $
 */
public class NarUnpackMojo extends AbstractDependencyMojo {

    /**
     * List of classifiers which you want unpack. Example ppc-MacOSX-g++,
     * x86-Windows-msvc, i386-Linux-g++.
     * 
     * @parameter expression=""
     */
    private List classifiers;

    /**
     * To look up Archiver/UnArchiver implementations
     *
     * @parameter expression="${component.org.codehaus.plexus.archiver.manager.ArchiverManager}"
     * @required
     */
    private ArchiverManager archiverManager;

    public void execute() throws MojoExecutionException, MojoFailureException {
    	if (shouldSkip()) return;
    	
		List narArtifacts = getNarManager().getNarDependencies("compile");
        if (classifiers == null) {
            getNarManager().unpackAttachedNars(narArtifacts, archiverManager, null, getOS());
        } else {
            for (Iterator j = classifiers.iterator(); j.hasNext();) {
            	getNarManager().unpackAttachedNars(narArtifacts, archiverManager, (String) j.next(), getOS());
            }
        }
    }
}
