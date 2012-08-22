// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * MarkerSize TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: MarkerSize.java 8584 2006-08-10 23:06:37Z duns $
 */
public class MarkerSize extends CGMTag {

    private double size;

    public MarkerSize() {
        super(5, 7, 1);
    }

    public MarkerSize(int size) {
        this();
        this.size = size;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        if (cgm.getMarkerSizeSpecificationMode() == MarkerSizeSpecificationMode.ABSOLUTE) {
            cgm.writeVDC(size);
        } else {
            cgm.writeReal(size);
        }
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("MARKERSIZE ");
        if (cgm.getMarkerSizeSpecificationMode() == MarkerSizeSpecificationMode.ABSOLUTE) {
            cgm.writeVDC(size);
        } else {
            cgm.writeReal(size);
        }
    }
}
