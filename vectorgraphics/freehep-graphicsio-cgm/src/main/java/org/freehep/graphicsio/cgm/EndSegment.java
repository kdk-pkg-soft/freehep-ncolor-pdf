// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * EndSegment TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: EndSegment.java 8584 2006-08-10 23:06:37Z duns $
 */
public class EndSegment extends CGMTag {

    public EndSegment() {
        super(0, 7, 2);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.outdent();
        cgm.print("ENDSEG");
    }
}
