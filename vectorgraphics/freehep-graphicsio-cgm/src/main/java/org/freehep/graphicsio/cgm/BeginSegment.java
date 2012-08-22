// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * BeginSegment TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: BeginSegment.java 8584 2006-08-10 23:06:37Z duns $
 */
public class BeginSegment extends CGMTag {

    private int name;

    public BeginSegment() {
        super(0, 6, 2);
    }

    public BeginSegment(int name) {
        this();
        this.name = name;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.writeName(name);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("BEGSEG ");
        cgm.writeName(name);
        cgm.indent();
    }
}