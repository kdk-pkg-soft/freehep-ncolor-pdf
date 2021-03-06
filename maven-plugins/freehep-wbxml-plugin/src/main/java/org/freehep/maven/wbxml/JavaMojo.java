package org.freehep.maven.wbxml;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.util.FileUtils;

/**
 * @goal java
 * @description Creates Java interface from wbxml definition
 * @phase generate-sources
 * @author <a href="Mark.Donszelmann@slac.stanford.edu">Mark Donszelmann</a>
 * @version $Id: IdljMojo.java 9121 2006-10-12 20:58:35Z duns $
 */
public class JavaMojo extends AbstractWBXMLMojo {

    /**
     * The target directory into which to generate the output.
     *
     * @parameter expression="${project.build.directory}/wbxml-generated"
     * @required
     */
    protected String targetDirectory;

    /**
     * The package name for which to generate the output.
     *
     * @parameter
     * @required
     */
    private String pkg;
   
    public void execute() throws MojoExecutionException {        
        if (!FileUtils.fileExists(targetDirectory)) {
            FileUtils.mkdir( targetDirectory );
        }

        if (project != null) {
            project.addCompileSourceRoot(targetDirectory);
        }

        if (!sourceDirectory.endsWith("/")) {
            sourceDirectory = sourceDirectory+"/";
        }
  
        try {
        	readWBXML(sourceDirectory+source);
        	writeJavaInterface(targetDirectory, pkg, source);
        } catch (IOException e) {
        	throw new MojoExecutionException("Error converting wbxml to java", e);
        }
    }
    
	private void writeJavaInterface(String directory, String pkg, String className) throws IOException {
		String dirName = directory + "/" + pkg.replace('.', '/');
		File dir = new File(dirName);
		dir.mkdirs();
		
		PrintWriter writer = new PrintWriter(new File(dir, className+".java"));
		
		writer.println("// Generated by freehep-wbxml-plugin");
		writer.println("package "+pkg+";");
		writer.println();
		writer.println("import org.freehep.wbxml.Attributes;");
		writer.println("import org.freehep.wbxml.WBXML;");
		writer.println();
		writer.println();
		writer.println("public interface "+className+" extends WBXML {");
		
		writer.println("    // Tags");		
		for (Iterator i=tags.iterator(); i.hasNext(); ) {
			Tag tag = (Tag)i.next();
			writer.println("    public final static int "+tag.getConstant()+" = "+tag.getNumber()+";			// "+tag.getComment());			
		}
		writer.println();
		
		writer.println("    // Attributes");
		for (Iterator i=attributes.iterator(); i.hasNext(); ) {
			Attribute attribute = (Attribute)i.next();
			writer.println("    public final static int "+attribute.getConstant()+" = "+attribute.getNumber()+";			// "+attribute.getComment());				
		}
		writer.println("");
		
		writer.println("	// Tag Lookup Table");
		writer.println("	public final static String[] tags = {");
		for (Iterator i=tags.iterator(); i.hasNext(); ) {
			Tag tag = (Tag)i.next();
			writer.println("        \""+tag.getName()+"\""+(i.hasNext() ? "," : "")+"			// "+tag.getConstant());
		}
		writer.println("    };");
		writer.println("");
		
		writer.println("	// TagIsEmpty Lookup Table");
		writer.println("	public final static boolean[] tagIsEmpty = {");
		for (Iterator i=tags.iterator(); i.hasNext(); ) {
			Tag tag = (Tag)i.next();
			writer.println("        "+tag.isEmpty()+(i.hasNext() ? "," : "")+"			// "+tag.getConstant());
		}
		writer.println("    };");
		writer.println("");
		
		writer.println("	// Attribute Lookup Table");
		writer.println("	public final static String[] attributes = {");
		for (Iterator i=attributes.iterator(); i.hasNext(); ) {
			Attribute attribute = (Attribute)i.next();
			writer.println("        \""+attribute.getName()+"\""+(i.hasNext() ? "," : "")+"			// "+attribute.getConstant());
		}
		writer.println("    };");
		writer.println("");
		
		writer.println("	// AttributeType Lookup Table");
		writer.println("	public final static int[] attributeType = {");
		for (Iterator i=attributes.iterator(); i.hasNext(); ) {
			Attribute attribute = (Attribute)i.next();
			writer.println("        Attributes."+attribute.getType().toUpperCase()+(i.hasNext() ? "," : "")+"			// "+attribute.getConstant());
		}
		writer.println("    };");
		writer.println("");
		
		writer.println("");
		writer.println("}");
		
		writer.close();
	}
    
    
}
