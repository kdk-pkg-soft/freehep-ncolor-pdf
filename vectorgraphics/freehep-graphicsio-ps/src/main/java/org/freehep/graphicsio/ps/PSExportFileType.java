// Copyright 2003-2007, FreeHEP.
package org.freehep.graphicsio.ps;

import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;
import java.io.OutputStream;

import org.freehep.graphics2d.VectorGraphics;

/**
 * @author Mark Donszelmann
 * @author Charles Loomis, Simon Fischer
 * @version $Id: PSExportFileType.java 13311 2007-09-10 18:13:00Z duns $
 */
public class PSExportFileType extends AbstractPSExportFileType {

	public String getDescription() {
		return "PostScript";
	}

	public String[] getExtensions() {
		return new String[] { "ps" };
	}

	public boolean isMultipageCapable() {
		return true;
	}

	public VectorGraphics getGraphics(OutputStream os, Component target)
			throws IOException {

		return new PSGraphics2D(os, target);
	}

	public VectorGraphics getGraphics(OutputStream os, Dimension dimension)
			throws IOException {

		return new PSGraphics2D(os, dimension);
	}

}
