// Copyright FreeHEP 2007.
package org.freehep.maven.wbxml;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.util.FileUtils;

/**
 * @goal cpp
 * @description Creates Cpp classes from wbxml definition
 * @phase generate-sources
 * @author <a href="Mark.Donszelmann@slac.stanford.edu">Mark Donszelmann</a>
 * 
 * @version $Id: IdljMojo.java 9121 2006-10-12 20:58:35Z duns $
 */
public class CppMojo extends AbstractWBXMLMojo {

	/**
	 * The target directory into which to generate the output.
	 * 
	 * @parameter expression="${project.build.directory}/wbxml-generated"
	 * @required
	 */
	protected String targetDirectory;

	/**
	 * The namespace for which to generate the output.
	 * 
	 * @parameter
	 */
	protected String namespace = null;

	public void execute() throws MojoExecutionException {
		if (!FileUtils.fileExists(targetDirectory)) {
			FileUtils.mkdir(targetDirectory);
		}

		if (!sourceDirectory.endsWith("/")) {
			sourceDirectory = sourceDirectory + "/";
		}

		try {
			readWBXML(sourceDirectory + source);

			File dir = new File(targetDirectory);
			writeCppClass(dir, source);

			if (namespace != null)
				dir = new File(dir, namespace);
			writeCppHeader(dir, source);
		} catch (IOException e) {
			throw new MojoExecutionException("Error converting wbxml to java",
					e);
		}
	}

	private void writeCppClass(File directory, String className)
			throws IOException {
		directory.mkdirs();

		PrintWriter writer = new PrintWriter(new File(directory, className
				+ ".cpp"));

		writer.println("// Generated by freehep-wbxml-plugin");
		writer.println();
		writer.println("#include <"
				+ (namespace != null ? namespace + "/" : "") + className
				+ ".h>");
		writer.println();

		if (namespace != null) {
			writer.println("using namespace " + namespace + ";");
			writer.println();
		}

		writer.println(className + "::" + className + "() {");
		writer.println("    // Tag Lookup Table");
		for (Iterator i = tags.iterator(); i.hasNext();) {
			Tag tag = (Tag) i.next();
			writer.println("    tags[" + tag.getConstant() + "] = \""
					+ tag.getName() + "\";");
		}
		writer.println();
		
		writer.println("    // TagIsEmpty Lookup Table");
		for (Iterator i = tags.iterator(); i.hasNext();) {
			Tag tag = (Tag) i.next();
			writer.println("    tagIsEmpty[" + tag.getConstant() + "] = "
					+ tag.isEmpty() + ";");
		}
		writer.println("");

		writer.println("    // Attribute Lookup Table");
		for (Iterator i = attributes.iterator(); i.hasNext();) {
			Attribute attribute = (Attribute) i.next();
			writer.println("    attributes[" + attribute.getConstant()
					+ "] = \"" + attribute.getName() + "\";");
		}
		writer.println("");

		writer.println("    // AttributeType Lookup Table");
		for (Iterator i = attributes.iterator(); i.hasNext();) {
			Attribute attribute = (Attribute) i.next();
			writer.println("    attributeTypes[" + attribute.getConstant()
					+ "] = IAttributes::" + attribute.getType().toUpperCase()
					+ ";");
		}
		writer.println("");

		writer.println("}");
		writer.println("");

		writer.close();
	}

	private void writeCppHeader(File directory, String className)
			throws IOException {
		directory.mkdirs();

		PrintWriter writer = new PrintWriter(new File(directory, className
				+ ".h"));

		writer.println("// Generated by freehep-wbxml-plugin");
		writer.println("#ifndef " + className.toUpperCase() + "_INCLUDE");
		writer.println("#define " + className.toUpperCase() + "_INCLUDE 1");
		writer.println();
		writer.println("#include <string>");
		writer.println("#include <sstream>");
		writer.println("#include <map>");
		writer.println();

		writer.println("#include <WBXML/IAttributes.h>");
		writer.println();

		if (namespace != null) {
			writer.println("namespace " + namespace + " {");
			writer.println();
		}

		writer.println("class " + className + " {");
		writer.println("public:");
		writer.println();

		writer.println("    inline std::string getTag(unsigned int tag) {");
		writer.println("        if (tag >= tags.size()) {");
		writer.println("            std::stringstream s;");
		writer.println("            s << \"Unrecognized TagID: \" << tag;");
		writer.println("            return s.str();");
		writer.println("        }");
		writer.println("        return tags[tag];");
		writer.println("    }");
		writer.println();

		writer.println("    inline bool isTagEmpty(unsigned int tag) {");
		writer.println("        if (tag >= tagIsEmpty.size()) return false;");
		writer.println("        return tagIsEmpty[tag];");
		writer.println("    }");

		writer.println("    inline std::string getAttribute(unsigned int tag) {");
		writer.println("        if (tag >= attributes.size()) {");
		writer.println("            std::stringstream s;");
		writer.println("            s << \"Unrecognized AttributeID: \" << tag;");
		writer.println("            return s.str();");
		writer.println("        }");
		writer.println("        return attributes[tag];");
		writer.println("    }");
		writer.println();

		writer.println("    inline IAttributes::Types getAttributeType(unsigned int tag) {");
		writer.println("        if (tag >= attributeTypes.size()) return IAttributes::UNDEFINED;");
		writer.println("        return attributeTypes[tag];");
		writer.println("    }");
		writer.println();

		int k;
		writer.println("    enum Tag {");
		k = 0;
		for (Iterator i = tags.iterator(); i.hasNext();) {
			Tag tag = (Tag) i.next();
			writer.println("        " + tag.getConstant()
					+ (i.hasNext() ? "," : "") + "			// " + k + " " + tag.getComment());
			k++;
		}
		writer.println("    };");
		writer.println();

		writer.println("    enum Attribute {");
		k = 0;
		for (Iterator i = attributes.iterator(); i.hasNext();) {
			Attribute attribute = (Attribute) i.next();
			writer.println("        " + attribute.getConstant()
					+ (i.hasNext() ? "," : "") + "			// "
					+ k + " "+ attribute.getComment());
			k++;
		}
		writer.println("    };");
		writer.println();

		writer.println("    " + className + "();");
		writer.println();

		writer.println("private:");
		writer.println("    std::map<unsigned int, std::string> tags;");
		writer.println("    std::map<unsigned int, bool> tagIsEmpty;");
		writer.println("    std::map<unsigned int, std::string> attributes;");
		writer.println("    std::map<unsigned int, IAttributes::Types> attributeTypes;");
		writer.println();

		writer.println("}; // class");
		writer.println();

		if (namespace != null) {
			writer.println("} // namespace");
			writer.println();
		}

		writer.println("#endif");

		writer.close();
	}

}
