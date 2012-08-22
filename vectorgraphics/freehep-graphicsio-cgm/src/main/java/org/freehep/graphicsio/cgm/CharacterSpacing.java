// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * CharacterSpacing TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: CharacterSpacing.java 8584 2006-08-10 23:06:37Z duns $
 */
public class CharacterSpacing extends CGMTag {

    private double spacing;

    public CharacterSpacing() {
        super(5, 13, 1);
    }

    public CharacterSpacing(double spacing) {
        this();
        this.spacing = spacing;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.writeReal(spacing);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("CHARSPACE ");
        cgm.writeReal(spacing);
    }
}
