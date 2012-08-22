// Copyright FreeHEP, 2006.
package org.freehep.maven.one;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.FileUtils;

/**
 * Builds an XML fragment with all dependencies (flattened) for maven 1.
 * @goal generate-dependencies
 * @description Builds an XML fragment with all dependencies (flattened) for maven 1.
 * @phase generate-resources
 * @requiresProject
 * @requiresDependencyResolution
 * @author <a href="Mark.Donszelmann@slac.stanford.edu">Mark Donszelmann</a>
 * @version $Id: OneMojo.java 8947 2006-09-12 18:16:26Z duns $
 */
public class OneMojo extends AbstractMojo {

    /**
     * The target directory into which to generate the output.
     * 
     * @parameter expression="${project.build.directory}"
     * @required
     */
    private String targetDirectory;

    /**
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    public void execute() throws MojoExecutionException {
        if (!FileUtils.fileExists(targetDirectory)) {
            FileUtils.mkdir(targetDirectory);
        }

	String siteDirectory = targetDirectory+"/site";
        if (!FileUtils.fileExists(siteDirectory)) {
            FileUtils.mkdir(siteDirectory);
        }

        try {
            PrintWriter xml = new PrintWriter(new FileWriter(
                    siteDirectory+"/project.xml"));

            xml.println("<project>");
            xml.println("  <pomVersion>3</pomVersion>");
            xml.println("  <id>jaida-example</id>");
            xml.println("  <groupId>jaida.example.group</groupId>");
            xml.println("  <artifactId>jaida-example</artifactId>");
            xml.println("  <name>JAIDA Maven1 Example</name>");
            xml.println("  <currentVersion>1.0-SNAPSHOT</currentVersion>");
            xml.println();
            xml.println("  <dependencies>");
            
            List dependencies = project.getRuntimeArtifacts();
            Collections.sort(dependencies);
            
            for (Iterator i = dependencies.iterator(); i.hasNext();) {
                Artifact dependency = (Artifact) i.next();
                xml.println("    <dependency>");
                xml.println("      <groupId>"+dependency.getGroupId()+"</groupId>");
                xml.println("      <artifactId>"+dependency.getArtifactId()+"</artifactId>");
                xml.println("      <version>"+dependency.getVersion()+"</version>");
                xml.println("    </dependency>");
            }

            xml.println("  </dependencies>");
            xml.println();
            xml.println("</project>");

            xml.close();
        } catch (IOException e) {
            throw new MojoExecutionException(
                    "Failed to write Maven1 dependencies file", e);
        }
    }
}
