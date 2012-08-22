// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.awt.geom.Point2D;
import java.io.IOException;

/**
 * Text TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: Text.java 8584 2006-08-10 23:06:37Z duns $
 */
public class Text extends AppendText {

    protected Point2D p;

    public Text() {
        super(4, 4, 1);
    }

    public Text(Point2D p, String text) {
        this();
        this.p = p;
        this.text = text;
    }

    protected Text(int elementClass, int elementID, int version) {
        super(elementClass, elementID, version);
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.writePoint(p);
        super.write(tagID, cgm);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("TEXT ");
        cgm.writePoint(p);
        cgm.print(", ");
        writeTextPiece(cgm);
    }
}
