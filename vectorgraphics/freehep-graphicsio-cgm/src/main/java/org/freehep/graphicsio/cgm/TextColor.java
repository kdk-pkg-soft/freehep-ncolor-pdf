// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.awt.Color;
import java.io.IOException;

/**
 * TextColor TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: TextColor.java 8584 2006-08-10 23:06:37Z duns $
 */
public class TextColor extends CGMTag {

    private Color color;

    public TextColor() {
        super(5, 14, 1);
    }

    public TextColor(Color color) {
        this();
        this.color = color;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.writeColor(color);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("TEXTCOLR ");
        cgm.writeColor(color);
    }
}
