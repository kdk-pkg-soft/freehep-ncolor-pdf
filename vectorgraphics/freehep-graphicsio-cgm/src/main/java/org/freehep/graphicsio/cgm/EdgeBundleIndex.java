// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * EdgeBundleIndex TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: EdgeBundleIndex.java 8584 2006-08-10 23:06:37Z duns $
 */
public class EdgeBundleIndex extends CGMTag {

    private int index;

    public EdgeBundleIndex() {
        super(5, 26, 1);
    }

    public EdgeBundleIndex(int index) {
        this();
        this.index = index;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.writeIntegerIndex(index);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("EDGEINDEX ");
        cgm.writeInteger(index);
    }
}
