// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.awt.geom.Point2D;
import java.io.IOException;

/**
 * CircularArc3PointClose TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: CircularArc3PointClose.java 8584 2006-08-10 23:06:37Z duns $
 */
public class CircularArc3PointClose extends CircularArc3Point {

    public final static int PIE = 0;

    public final static int CHORD = 1;

    private int closure;

    public CircularArc3PointClose() {
        super(4, 14, 1);
    }

    public CircularArc3PointClose(Point2D ps, Point2D pi, Point2D pe,
            int closure) {
        this();
        this.ps = ps;
        this.pi = pi;
        this.pe = pe;
        this.closure = closure;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        super.write(tagID, cgm);

        cgm.writeEnumerate(closure);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("ARC3PTCLOSE ");
        writeArcSpec(cgm);
        cgm.print((closure == PIE) ? " PIE" : " CHORD");
    }
}
