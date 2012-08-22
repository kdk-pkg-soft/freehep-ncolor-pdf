// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * CharacterOrientation TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: CharacterOrientation.java 8584 2006-08-10 23:06:37Z duns $
 */
public class CharacterOrientation extends CGMTag {

    private double xUp, yUp, xBase, yBase;

    public CharacterOrientation() {
        super(5, 16, 1);
    }

    public CharacterOrientation(double xUp, double yUp, double xBase,
            double yBase) {
        this();
        this.xUp = xUp;
        this.yUp = yUp;
        this.xBase = xBase;
        this.yBase = yBase;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.writeVDC(xUp);
        cgm.writeVDC(yUp);
        cgm.writeVDC(xBase);
        cgm.writeVDC(yBase);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("CHARORI ");
        cgm.writeVDC(xUp);
        cgm.print(", ");
        cgm.writeVDC(yUp);
        cgm.print(", ");
        cgm.writeVDC(xBase);
        cgm.print(", ");
        cgm.writeVDC(yBase);
    }
}
