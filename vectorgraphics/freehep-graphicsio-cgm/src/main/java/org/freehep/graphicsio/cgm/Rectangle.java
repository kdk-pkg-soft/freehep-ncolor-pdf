// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

/**
 * Rectangle TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: Rectangle.java 8584 2006-08-10 23:06:37Z duns $
 */
public class Rectangle extends CGMTag {

    private Rectangle2D rectangle;

    public Rectangle() {
        super(4, 11, 1);
    }

    public Rectangle(Rectangle2D rectangle) {
        this();
        this.rectangle = rectangle;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.writePoint(new Point2D.Double(rectangle.getMinX(), rectangle
                .getMinY()));
        cgm.writePoint(new Point2D.Double(rectangle.getMaxX(), rectangle
                .getMaxY()));
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("RECT ");
        cgm.writePoint(new Point2D.Double(rectangle.getMinX(), rectangle
                .getMinY()));
        cgm.print(", ");
        cgm.writePoint(new Point2D.Double(rectangle.getMaxX(), rectangle
                .getMaxY()));
    }
}
