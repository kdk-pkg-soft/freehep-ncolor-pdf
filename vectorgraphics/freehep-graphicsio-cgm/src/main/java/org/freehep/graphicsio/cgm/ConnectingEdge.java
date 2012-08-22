// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * ConnectingEdge TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: ConnectingEdge.java 8584 2006-08-10 23:06:37Z duns $
 */
public class ConnectingEdge extends CGMTag {

    public ConnectingEdge() {
        super(5, 21, 1);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("CONNEDGE");
    }
}
