// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * CharacterHeight TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: CharacterHeight.java 8584 2006-08-10 23:06:37Z duns $
 */
public class CharacterHeight extends CGMTag {

    private double height;

    public CharacterHeight() {
        super(5, 15, 1);
    }

    public CharacterHeight(double height) {
        this();
        this.height = height;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.writeVDC(height);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("CHARHEIGHT ");
        cgm.writeVDC(height);
    }
}
