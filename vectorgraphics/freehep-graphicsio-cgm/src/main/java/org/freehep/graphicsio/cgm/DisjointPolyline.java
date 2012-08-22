// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.awt.geom.Point2D;
import java.io.IOException;

/**
 * DisjointPolyline TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: DisjointPolyline.java 8584 2006-08-10 23:06:37Z duns $
 */
public class DisjointPolyline extends Polyline {

    public DisjointPolyline() {
        super(4, 2, 1);
    }

    public DisjointPolyline(Point2D[] p) {
        this();
        this.p = p;
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.println("DISJTLINE");
        writePointList(cgm);
    }

}
