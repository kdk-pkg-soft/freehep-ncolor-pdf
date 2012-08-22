// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * NamePrecision TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: NamePrecision.java 8584 2006-08-10 23:06:37Z duns $
 */
public class NamePrecision extends CGMTag {

    // FIXME: not complete
    public NamePrecision() {
        super(1, 16, 2);
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.setNamePrecision(16);
        cgm.writeInteger(16);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.setNamePrecision(16);
        cgm.print("NAMEPREC ");
        cgm.writeInteger(Short.MIN_VALUE);
        cgm.print(", ");
        cgm.writeInteger(Short.MAX_VALUE);
    }
}
