// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.awt.Color;
import java.io.IOException;

/**
 * AuxiliaryColor TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: AuxiliaryColor.java 8584 2006-08-10 23:06:37Z duns $
 */
public class AuxiliaryColor extends CGMTag {

    private Color color;

    public AuxiliaryColor() {
        super(3, 3, 1);
    }

    public AuxiliaryColor(Color color) {
        this();
        this.color = color;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.writeColor(color);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("AUXCOLR ");
        cgm.writeColor(color);
    }
}
