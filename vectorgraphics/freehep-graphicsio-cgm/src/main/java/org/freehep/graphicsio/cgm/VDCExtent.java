// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.awt.geom.Point2D;
import java.io.IOException;

/**
 * VDCExtent TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: VDCExtent.java 8584 2006-08-10 23:06:37Z duns $
 */
public class VDCExtent extends CGMTag {

    private Point2D p0, p1;

    public VDCExtent() {
        super(2, 6, 1);
    }

    public VDCExtent(Point2D p0, Point2D p1) {
        this();
        this.p0 = p0;
        this.p1 = p1;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.writePoint(p0);
        cgm.writePoint(p1);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("VDCEXT ");
        cgm.writePoint(p0);
        cgm.print(", ");
        cgm.writePoint(p1);
    }
}
