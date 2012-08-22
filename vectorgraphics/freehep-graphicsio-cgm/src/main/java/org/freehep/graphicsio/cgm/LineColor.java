// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.awt.Color;
import java.io.IOException;

/**
 * LineBundleIndex TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: LineColor.java 8584 2006-08-10 23:06:37Z duns $
 */
public class LineColor extends CGMTag {

    private Color color;

    public LineColor() {
        super(5, 4, 1);
    }

    public LineColor(Color color) {
        this();
        this.color = color;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.writeColor(color);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("LINECOLR ");
        cgm.writeColor(color);
    }
}
