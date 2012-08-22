// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.awt.geom.Point2D;
import java.io.IOException;

/**
 * Polygon TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: Polygon.java 8584 2006-08-10 23:06:37Z duns $
 */
public class Polygon extends Polyline {

    public Polygon() {
        super(4, 7, 1);
    }

    public Polygon(Point2D[] p) {
        this();
        this.p = p;
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.println("POLYGON");
        writePointList(cgm);
    }
}
