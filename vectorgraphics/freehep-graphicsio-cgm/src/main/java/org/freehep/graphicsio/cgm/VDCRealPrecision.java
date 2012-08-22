// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * VDCRealPrecision TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: VDCRealPrecision.java 8584 2006-08-10 23:06:37Z duns $
 */
public class VDCRealPrecision extends CGMTag {

    // FIXME: not complete
    public VDCRealPrecision() {
        super(3, 2, 1);
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.setVDCRealPrecision(false, true);
        cgm.writeEnumerate(0); // floating
        cgm.writeInteger(12); // double exp
        cgm.writeInteger(52); // double fract
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.setVDCRealPrecision(false, true);
        cgm.print("VDCREALPREC ");
        cgm.writeReal(Double.MIN_VALUE);
        cgm.print(", ");
        cgm.writeReal(Double.MAX_VALUE);
        cgm.print(", ");
        cgm.writeInteger(20); // FIXME
    }
}
