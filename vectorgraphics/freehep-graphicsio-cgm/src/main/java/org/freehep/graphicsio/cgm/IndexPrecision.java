// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * IndexPrecision TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: IndexPrecision.java 8584 2006-08-10 23:06:37Z duns $
 */
public class IndexPrecision extends CGMTag {

    // FIXME: not complete
    public IndexPrecision() {
        super(1, 6, 1);
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {

        cgm.setIndexPrecision(16);
        cgm.writeInteger(16);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("INDEXPREC ");
        cgm.writeInteger(Short.MIN_VALUE);
        cgm.print(", ");
        cgm.writeInteger(Short.MAX_VALUE);
    }
}
