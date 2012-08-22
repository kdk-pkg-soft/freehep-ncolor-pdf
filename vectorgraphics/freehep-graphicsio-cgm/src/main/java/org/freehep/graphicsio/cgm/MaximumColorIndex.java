// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * MaximumColorIndex TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: MaximumColorIndex.java 8584 2006-08-10 23:06:37Z duns $
 */
public class MaximumColorIndex extends CGMTag {

    private int index;

    public MaximumColorIndex() {
        super(1, 9, 1);
    }

    public MaximumColorIndex(int index) {
        this();
        this.index = index;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {

        cgm.writeColorIndex(index);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("MAXCOLRINDEX ");
        cgm.writeInteger(127); // FIXME
    }
}
