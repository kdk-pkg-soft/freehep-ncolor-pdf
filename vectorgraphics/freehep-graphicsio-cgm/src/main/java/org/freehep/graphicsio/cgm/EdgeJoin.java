// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * LineJoin TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: EdgeJoin.java 8584 2006-08-10 23:06:37Z duns $
 */
public class EdgeJoin extends CGMTag {

    public static final int UNSPECIFIED = 1;

    public static final int MITRE = 2;

    public static final int ROUND = 3;

    public static final int BEVEL = 4;

    private int type;

    public EdgeJoin() {
        super(5, 38, 3);
    }

    public EdgeJoin(int type) {
        this();
        this.type = type;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.writeIntegerIndex(type);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("EDGEJOIN ");
        cgm.writeInteger(type);
    }
}
