// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.swf;

import java.io.IOException;

/**
 * ShowFrame TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: ShowFrame.java 8584 2006-08-10 23:06:37Z duns $
 */
public class ShowFrame extends ControlTag {

    public ShowFrame() {
        super(1, 1);
    }

    public SWFTag read(int tagID, SWFInputStream swf, int len)
            throws IOException {
        return this;
    }
}
