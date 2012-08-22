// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * Transparency TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: Transparency.java 8584 2006-08-10 23:06:37Z duns $
 */
public class Transparency extends CGMTag {

    private boolean on;

    public Transparency() {
        super(3, 4, 1);
    }

    public Transparency(boolean on) {
        this();
        this.on = on;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.writeEnumerate((on) ? 1 : 0);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("TRANSPARENCY ");
        cgm.print((on) ? "ON" : "OFF");
    }
}
