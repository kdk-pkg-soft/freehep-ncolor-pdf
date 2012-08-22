// Copyright 2006, FreeHEP.
package org.freehep.maven.nar;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DefaultArtifact;

/**
 * 
 * @author duns
 * @version $Id: NarArtifact.java 8584 2006-08-10 23:06:37Z duns $
 */
public class NarArtifact extends DefaultArtifact {

    private NarInfo narInfo;

    public NarArtifact(Artifact dependency, NarInfo narInfo) {
        super(dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersionRange(), 
              dependency.getScope(), dependency.getType(), dependency.getClassifier(), 
              dependency.getArtifactHandler(), dependency.isOptional());
        this.narInfo = narInfo;
    }
    
    public NarInfo getNarInfo() {
        return narInfo;
    }
}
