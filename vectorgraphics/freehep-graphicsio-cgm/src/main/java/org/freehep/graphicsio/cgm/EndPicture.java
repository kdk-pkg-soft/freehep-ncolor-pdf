// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * EndPicture TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: EndPicture.java 8584 2006-08-10 23:06:37Z duns $
 */
public class EndPicture extends CGMTag {

    public EndPicture() {
        super(0, 5, 1);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.outdent();
        cgm.print("ENDPIC");
    }
}
