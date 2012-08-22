// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * EndFigure TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: EndFigure.java 8584 2006-08-10 23:06:37Z duns $
 */
public class EndFigure extends CGMTag {

    public EndFigure() {
        super(0, 9, 2);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.outdent();
        cgm.print("ENDFIGURE");
    }
}
