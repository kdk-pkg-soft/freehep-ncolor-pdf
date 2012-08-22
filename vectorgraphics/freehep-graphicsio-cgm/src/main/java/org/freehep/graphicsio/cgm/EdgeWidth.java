// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * EdgeWidth TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: EdgeWidth.java 8584 2006-08-10 23:06:37Z duns $
 */
public class EdgeWidth extends CGMTag {

    private double width;

    public EdgeWidth() {
        super(5, 28, 1);
    }

    public EdgeWidth(double width) {
        this();
        this.width = width;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        if (cgm.getEdgeWidthSpecificationMode() == EdgeWidthSpecificationMode.ABSOLUTE) {
            cgm.writeVDC(width);
        } else {
            cgm.writeReal(width);
        }
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("EDGEWIDTH ");
        if (cgm.getEdgeWidthSpecificationMode() == EdgeWidthSpecificationMode.ABSOLUTE) {
            cgm.writeVDC(width);
        } else {
            cgm.writeReal(width);
        }
    }
}
