// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * EndMetafile TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: EndMetafile.java 8584 2006-08-10 23:06:37Z duns $
 */
public class EndMetafile extends CGMTag {

    public EndMetafile() {
        super(0, 2, 1);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.outdent();
        cgm.print("ENDMF");
    }
}