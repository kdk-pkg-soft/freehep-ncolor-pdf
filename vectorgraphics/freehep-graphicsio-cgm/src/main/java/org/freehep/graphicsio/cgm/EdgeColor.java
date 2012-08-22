// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.awt.Color;
import java.io.IOException;

/**
 * EdgeColor TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: EdgeColor.java 8584 2006-08-10 23:06:37Z duns $
 */
public class EdgeColor extends CGMTag {

    private Color color;

    public EdgeColor() {
        super(5, 29, 1);
    }

    public EdgeColor(Color color) {
        this();
        this.color = color;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.writeColor(color);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("EDGECOLR ");
        cgm.writeColor(color);
    }
}
