// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * EdgeVisibility TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: EdgeVisibility.java 8584 2006-08-10 23:06:37Z duns $
 */
public class EdgeVisibility extends CGMTag {

    private boolean on;

    public EdgeVisibility() {
        super(5, 30, 1);
    }

    public EdgeVisibility(boolean on) {
        this();
        this.on = on;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.writeEnumerate((on) ? 1 : 0);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("EDGEVIS ");
        cgm.print((on) ? "ON" : "OFF");
    }
}
