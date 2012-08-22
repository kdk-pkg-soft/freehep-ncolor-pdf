// Copyright FreeHEP, 2005.
package org.freehep.maven.jas;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.FileUtils;

/**
 * @description Copies artifact and dependencies to jas extensions directory.
 * @goal install
 * @phase install
 * @requiresProject
 * @requiresDependencyResolution
 * @author <a href="Mark.Donszelmann@slac.stanford.edu">Mark Donszelmann</a>
 * @version $Id: JasMojo.java 9225 2006-11-01 21:23:31Z duns $
 */
public class JasMojo extends AbstractMojo {

    /**
     * The name the artifact is translated to for JAS.
     * Defaults to ${artifactId}
     * @parameter
     */
    protected String jarName;

    /**
     * List of dependencies to include in the copy. If not
     * specified (default) all dependencies will be copied.
     * If empty list is specified no dependencies will be copied.
     * Entries are to be specified using format "groupId:artifactId".
     * 
     * @parameter
     */
    protected List includes = null;
    
    // NOTE: properties did not work (mvn 2.0)
    /**
     * Translations for all dependencies.
     * @parameter
     */
    protected Map dependencyNames;
    
    /**
     * The directory in which to install the extensions.
     * Defaults to "${user.home}/.JAS3"
     * @parameter expression="${jas3.user.dir}"
     */
    protected File jasUserDirectory;

    /**
     * @parameter expression="${project.build.directory}"
     * @readonly
     */
    protected File outputDirectory;

    /**
     * @parameter expression="${project}"
     * @readonly
     * @required
     */
    protected MavenProject project;    

    /**
     * @parameter expression="${localRepository}"
     * @required
     * @readonly
     */
    protected ArtifactRepository localRepository;

    private File jasExtensionsDirectory;
    
    public void execute() throws MojoExecutionException, MojoFailureException {
        int copies = 0;
        
        if (jasUserDirectory == null) {
            jasUserDirectory = new File(System.getProperty("user.home"), ".JAS3");
        }
        
        jasExtensionsDirectory = new File(jasUserDirectory, "extensions");
        if (!jasExtensionsDirectory.exists()) {
            jasExtensionsDirectory.mkdirs();
        }
    
        if (jarName == null) {
            jarName = project.getArtifactId();
        }
        
        if (dependencyNames == null) {
            dependencyNames = new HashMap();
        }
        
        // copy artifact itself
        File artifact = new File(outputDirectory, project.getArtifactId()+"-"+project.getVersion()+".jar");
        copyJar(artifact, jarName, project.getGroupId(), project.getArtifactId(), project.getVersion());
        copies++;
        
        // copy dependencies
        Set artifacts = project.getArtifacts();
        for (Iterator i=artifacts.iterator(); i.hasNext(); ) {
            Artifact dependency = (Artifact)i.next();
            String scope = dependency.getScope();
            if (scope.equals(Artifact.SCOPE_COMPILE) || scope.equals(Artifact.SCOPE_RUNTIME)) {
                if (dependency.getType().equals("jar")) {
                    // FIXME reported to maven developer list, isSnapshot changes behaviour of getBaseVersion, called in pathOf.
                    if (dependency.isSnapshot());
                    File file = new File(localRepository.getBasedir(), localRepository.pathOf(dependency));                    
                    String id = dependency.getArtifactId();
                    String group = dependency.getGroupId();
                    
                    // translate names (based on artifactId only)
                    String translation = (String)dependencyNames.get(id);
                    if (translation == null) translation = id;
                    
                    // copy only if in list, if list exists
                    if ((includes == null) || includes.contains(group+":"+id)) {
                        getLog().info("  Copying "+group+":"+id);
                        copyJar(file, translation, group, id, dependency.getVersion());
                        copies++;
                    }
                }
            }
        }
        
        getLog().info("Copied "+copies+" jar file"+(copies==1 ? "" : "s")+" to "+jasExtensionsDirectory);
    }
    
    private void copyJar(File source, String destination, String groupId, String artifactId, String version) throws MojoExecutionException {
        File destFile = new File(jasExtensionsDirectory, destination+".jar");
        try {
            File versionFile = new File(jasExtensionsDirectory, destination+".version");
            PrintWriter writer = new PrintWriter(new FileWriter(versionFile));
            writer.println(groupId+":"+artifactId+" = "+version);
            writer.close();
            
            FileUtils.copyFile(source, destFile);
        } catch (IOException e) {
            throw new MojoExecutionException("Jas Mojo: cannot copy jar file: "+source+" to destination "+destFile, e);
        }
    }
}
