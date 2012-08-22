// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * ColorSelectionMode TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: ColorSelectionMode.java 8584 2006-08-10 23:06:37Z duns $
 */
public class ColorSelectionMode extends CGMTag {

    public static final int INDEXED = 0;

    public static final int DIRECT = 1;

    private int mode;

    public ColorSelectionMode() {
        super(2, 2, 1);
    }

    public ColorSelectionMode(int mode) {
        this();
        this.mode = mode;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.setColorMode(mode == DIRECT);
        cgm.writeEnumerate(mode);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.setColorMode(mode == DIRECT);
        cgm.print("COLRMODE ");
        cgm.print((mode == DIRECT) ? "DIRECT" : "INDEXED");
    }
}
