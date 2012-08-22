// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.swf;

import java.awt.Color;
import java.io.IOException;

/**
 * SetBackgroundColor TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: SetBackgroundColor.java 8584 2006-08-10 23:06:37Z duns $
 */
public class SetBackgroundColor extends ControlTag {

    private Color color;

    public SetBackgroundColor(Color color) {
        this();
        this.color = color;
    }

    public SetBackgroundColor() {
        super(9, 1);
    }

    public SWFTag read(int tagID, SWFInputStream swf, int len)
            throws IOException {

        SetBackgroundColor tag = new SetBackgroundColor();
        tag.color = swf.readColor(false);

        return tag;
    }

    public void write(int tagID, SWFOutputStream swf) throws IOException {
        swf.writeColor(color, false);
    }

    public String toString() {
        return super.toString() + "\n" + "  bkg.color=" + color;
    }
}
