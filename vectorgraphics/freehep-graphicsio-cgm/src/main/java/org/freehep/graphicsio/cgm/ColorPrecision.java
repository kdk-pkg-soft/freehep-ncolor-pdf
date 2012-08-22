// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * ColorPrecision TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: ColorPrecision.java 8584 2006-08-10 23:06:37Z duns $
 */
public class ColorPrecision extends CGMTag {

    // FIXME: not complete
    public ColorPrecision() {
        super(1, 7, 1);
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {

        cgm.setDirectColorPrecision(24);
        cgm.writeInteger(24);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.setDirectColorPrecision(24);
        cgm.print("COLRPREC ");
        cgm.writeInteger(255);
    }
}
