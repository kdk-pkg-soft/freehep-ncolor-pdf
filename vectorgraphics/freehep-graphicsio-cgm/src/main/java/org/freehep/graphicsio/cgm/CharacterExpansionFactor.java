// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * CharacterExpansionFactor TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: CharacterExpansionFactor.java 8584 2006-08-10 23:06:37Z duns $
 */
public class CharacterExpansionFactor extends CGMTag {

    private double factor;

    public CharacterExpansionFactor() {
        super(5, 12, 1);
    }

    public CharacterExpansionFactor(double factor) {
        this();
        this.factor = factor;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.writeReal(factor);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("CHAREXPAN ");
        cgm.writeReal(factor);
    }
}
