// Copyright FreeHEP, 2005-2006.
package org.freehep.maven.nar;

import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @author <a href="Mark.Donszelmann@slac.stanford.edu">Mark Donszelmann</a>
 * @version $Id: AbstractDependencyMojo.java 12936 2007-07-05 21:26:30Z duns $
 */
public abstract class AbstractDependencyMojo extends AbstractNarMojo {

    /**
     * @parameter expression="${localRepository}"
     * @required
     * @readonly
     */
    private ArtifactRepository localRepository;

    protected ArtifactRepository getLocalRepository() {
        return localRepository;
    }
	
	protected NarManager getNarManager() throws MojoFailureException {
		return new NarManager(getLog(), getLocalRepository(), getMavenProject(), getArchitecture(), getOS(), getLinker());
	}
}
