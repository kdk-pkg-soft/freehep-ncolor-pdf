// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * MarkerBundleIndex TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: MarkerBundleIndex.java 8584 2006-08-10 23:06:37Z duns $
 */
public class MarkerBundleIndex extends CGMTag {

    private int index;

    public MarkerBundleIndex() {
        super(5, 5, 1);
    }

    public MarkerBundleIndex(int index) {
        this();
        this.index = index;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.writeIntegerIndex(index);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("MARKERINDEX ");
        cgm.writeInteger(index);
    }
}
