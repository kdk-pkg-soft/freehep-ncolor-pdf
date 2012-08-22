// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

/**
 * ClipRectangle TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: ClipRectangle.java 8584 2006-08-10 23:06:37Z duns $
 */
public class ClipRectangle extends CGMTag {

    private Rectangle2D clip;

    public ClipRectangle() {
        super(3, 5, 1);
    }

    public ClipRectangle(Rectangle2D rectangle) {
        this();
        clip = rectangle;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.writePoint(new Point2D.Double(clip.getMinX(), clip.getMinY()));
        cgm.writePoint(new Point2D.Double(clip.getMaxX(), clip.getMaxY()));
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("CLIPRECT ");
        cgm.writePoint(new Point2D.Double(clip.getMinX(), clip.getMinY()));
        cgm.print(", ");
        cgm.writePoint(new Point2D.Double(clip.getMaxX(), clip.getMaxY()));
    }
}
