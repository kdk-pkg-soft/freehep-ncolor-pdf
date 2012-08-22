// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.awt.geom.Point2D;
import java.io.IOException;

/**
 * Polymarker TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: Polymarker.java 8584 2006-08-10 23:06:37Z duns $
 */
public class Polymarker extends Polyline {

    public Polymarker() {
        super(4, 3, 1);
    }

    public Polymarker(Point2D[] p) {
        this();
        this.p = p;
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.println("MARKER");
        writePointList(cgm);
    }

}
