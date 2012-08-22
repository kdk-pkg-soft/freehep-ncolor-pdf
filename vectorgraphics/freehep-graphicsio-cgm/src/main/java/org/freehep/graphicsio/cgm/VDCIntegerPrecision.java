// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * VDCIntegerPrecision TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: VDCIntegerPrecision.java 8584 2006-08-10 23:06:37Z duns $
 */
public class VDCIntegerPrecision extends CGMTag {

    // FIXME: not complete
    public VDCIntegerPrecision() {
        super(3, 1, 1);
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.setVDCIntegerPrecision(16);
        cgm.writeInteger(16);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("VDCTYPE INTEGER;");
        cgm.setVDCIntegerPrecision(16);
        cgm.print("VDCINTEGERPREC");
        cgm.writeInteger(Short.MIN_VALUE);
        cgm.print(", ");
        cgm.writeInteger(Short.MAX_VALUE);
    }
}
