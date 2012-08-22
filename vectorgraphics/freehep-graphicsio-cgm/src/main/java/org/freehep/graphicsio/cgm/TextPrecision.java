// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * TextPrecision TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: TextPrecision.java 8584 2006-08-10 23:06:37Z duns $
 */
public class TextPrecision extends CGMTag {

    public static final int STRING = 1;

    public static final int CHARACTER = 2;

    public static final int STROKE = 3;

    private int precision;

    public TextPrecision() {
        super(5, 11, 1);
    }

    public TextPrecision(int precision) {
        this();
        this.precision = precision;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.writeEnumerate(precision);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("TEXTPREC ");
        switch (precision) {
        default:
        case STRING:
            cgm.print("STRING");
            break;
        case CHARACTER:
            cgm.print("CHAR");
            break;
        case STROKE:
            cgm.print("STROKE");
            break;
        }
    }
}
