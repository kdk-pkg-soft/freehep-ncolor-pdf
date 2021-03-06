// Copyright FreeHEP, 2007.
package org.freehep.maven.nar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Generates a NarSystem class with static methods to use inside the java part
 * of the library.
 * 
 * @goal nar-system-generate
 * @phase generate-sources
 * @requiresProject
 * @author <a href="Mark.Donszelmann@slac.stanford.edu">Mark Donszelmann</a>
 * @version $Id: NarDownloadMojo.java 10588 2007-03-16 17:24:33Z duns $
 */
public class NarSystemGenerate extends AbstractCompileMojo {

	public void execute() throws MojoExecutionException, MojoFailureException {
		if (shouldSkip())
			return;

		// get packageName if specified for JNI.
		String packageName = null;
		String narSystemName = null;
		File narSystemDirectory = null;
		for (Iterator i = getLibraries().iterator(); i.hasNext()
				&& (packageName == null);) {
			Library library = (Library) i.next();
			if (library.getType().equals(Library.JNI)) {
				packageName = library.getNarSystemPackage();
				narSystemName = library.getNarSystemName();
				narSystemDirectory = library.getNarSystemDirectory();
			}
		}

		if (packageName == null)
			return;

		// make sure destination is there
		narSystemDirectory.mkdirs();

		getMavenProject().addCompileSourceRoot(narSystemDirectory.getPath());

		File fullDir = new File(narSystemDirectory, packageName.replace('.', '/'));
		fullDir.mkdirs();

		File narSystem = new File(fullDir, narSystemName + ".java");
		try {
			FileOutputStream fos = new FileOutputStream(narSystem);
			PrintWriter p = new PrintWriter(fos);
			p.println("// DO NOT EDIT: Generated by NarSystemGenerate.");
			p.println("package " + packageName + ";");
			p.println("");
			p.println("public class NarSystem {");
			p.println("");
			p.println("    private NarSystem() {");
			p.println("    }");
			p.println("");
			p.println("    public static void loadLibrary() {");
			p.println("        System.loadLibrary(\""
					+ getMavenProject().getArtifactId() + "-"
					+ getMavenProject().getVersion() + "\");");
			p.println("    }");
			p.println("}");
			p.close();
			fos.close();
		} catch (IOException e) {
			throw new MojoExecutionException("Could not write '"
					+ narSystemName + "'", e);
		}
	}
}
