// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * EdgeType TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: EdgeType.java 8584 2006-08-10 23:06:37Z duns $
 */
public class EdgeType extends CGMTag {

    public static final int SOLID = 1;

    public static final int DASH = 2;

    public static final int DOT = 3;

    public static final int DASH_DOT = 4;

    public static final int DASH_DOT_DOT = 5;

    private int type;

    public EdgeType() {
        super(5, 27, 1);
    }

    public EdgeType(int type) {
        this();
        this.type = type;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.writeIntegerIndex(type);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("EDGETYPE ");
        cgm.writeInteger(type);
    }
}
