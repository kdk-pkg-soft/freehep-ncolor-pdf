// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * MarkerSizeSpecificationMode TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: MarkerSizeSpecificationMode.java 8584 2006-08-10 23:06:37Z duns $
 */
public class MarkerSizeSpecificationMode extends CGMTag {

    public static final int ABSOLUTE = 0;

    public static final int SCALED = 1;

    public static final int FRACTIONAL = 2;

    public static final int MM = 3;

    private int mode;

    public MarkerSizeSpecificationMode() {
        super(2, 3, 1);
    }

    public MarkerSizeSpecificationMode(int mode) {
        this();
        this.mode = mode;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.setMarkerSizeSpecificationMode(mode);
        cgm.writeEnumerate(mode);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.setMarkerSizeSpecificationMode(mode);
        cgm.print("MARKERSIZEMODE ");
        switch (mode) {
        default:
        case ABSOLUTE:
            cgm.print("ABS");
            break;
        case SCALED:
            cgm.print("SCALED");
            break;
        case FRACTIONAL:
            cgm.print("FRACTIONAL");
            break;
        case MM:
            cgm.print("METRIC");
            break;
        }
    }
}
