// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.awt.Color;
import java.io.IOException;

/**
 * MarkerColor TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: MarkerColor.java 8584 2006-08-10 23:06:37Z duns $
 */
public class MarkerColor extends CGMTag {

    private Color color;

    public MarkerColor() {
        super(5, 8, 1);
    }

    public MarkerColor(Color color) {
        this();
        this.color = color;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.writeColor(color);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("MARKERCOLR ");
        cgm.writeColor(color);
    }
}
