// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * VDCType TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: VDCType.java 8584 2006-08-10 23:06:37Z duns $
 */
public class VDCType extends CGMTag {

    public final static int INTEGER = 0;

    public final static int REAL = 1;

    private int type;

    public VDCType() {
        super(1, 3, 1);
    }

    public VDCType(int type) {
        this();
        this.type = type;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.setVDCReal((type == INTEGER) ? false : true);
        cgm.writeEnumerate(type);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.setVDCReal((type == INTEGER) ? false : true);
        cgm.print("VDCTYPE ");
        cgm.print((type == INTEGER) ? "INTEGER" : "REAL");
    }
}
