// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.awt.geom.Point2D;
import java.io.IOException;

/**
 * EllipticalArc TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: EllipticalArc.java 8584 2006-08-10 23:06:37Z duns $
 */
public class EllipticalArc extends Ellipse {

    protected Point2D delta0, delta1;

    public EllipticalArc() {
        super(4, 18, 1);
    }

    public EllipticalArc(Point2D p, Point2D c1, Point2D c2, Point2D delta0,
            Point2D delta1) {
        this();
        this.p = p;
        this.c1 = c1;
        this.c2 = c2;
        this.delta0 = delta0;
        this.delta1 = delta1;
    }

    protected EllipticalArc(int elementClass, int elementID, int version) {
        super(elementClass, elementID, version);
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        super.write(tagID, cgm);
        // FIXME: points?
        cgm.writeVDC(delta0.getX());
        cgm.writeVDC(delta0.getY());
        cgm.writeVDC(delta1.getX());
        cgm.writeVDC(delta1.getY());
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("ELLIPARC ");
        writeEllipseSpec(cgm);
        writeArcBounds(cgm);
    }

    protected void writeArcBounds(CGMWriter cgm) throws IOException {
        cgm.print(" ");
        cgm.writePoint(delta0);
        cgm.print(", ");
        cgm.writePoint(delta1);
    }
}
