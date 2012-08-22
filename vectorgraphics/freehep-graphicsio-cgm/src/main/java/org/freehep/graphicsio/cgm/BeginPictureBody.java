// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * BeginPictureBody TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: BeginPictureBody.java 8584 2006-08-10 23:06:37Z duns $
 */
public class BeginPictureBody extends CGMTag {

    public BeginPictureBody() {
        super(0, 4, 1);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.outdent();
        cgm.print("BEGPICBODY");
        cgm.indent();
    }
}
