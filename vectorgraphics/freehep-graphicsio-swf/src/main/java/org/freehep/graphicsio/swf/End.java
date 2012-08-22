// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.swf;

import java.io.IOException;

/**
 * End TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: End.java 8584 2006-08-10 23:06:37Z duns $
 */
public class End extends ControlTag {

    public End() {
        super(0, 1);
    }

    public SWFTag read(int tagID, SWFInputStream swf, int len)
            throws IOException {

        return this;
    }
}
