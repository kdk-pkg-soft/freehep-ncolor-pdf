// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * FillBundleIndex TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: FillBundleIndex.java 8584 2006-08-10 23:06:37Z duns $
 */
public class FillBundleIndex extends CGMTag {

    private int index;

    public FillBundleIndex() {
        super(5, 21, 1);
    }

    public FillBundleIndex(int index) {
        this();
        this.index = index;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.writeIntegerIndex(index);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("FILLINDEX ");
        cgm.writeInteger(index);
    }
}
