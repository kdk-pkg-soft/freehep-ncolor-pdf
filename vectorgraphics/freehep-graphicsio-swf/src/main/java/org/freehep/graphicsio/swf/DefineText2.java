// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.swf;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.Vector;

/**
 * DefineText2 TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: DefineText2.java 8584 2006-08-10 23:06:37Z duns $
 */
public class DefineText2 extends DefineText {

    public DefineText2(int id, Rectangle2D bounds, AffineTransform matrix,
            Vector text) {
        this();
        character = id;
        this.bounds = bounds;
        this.matrix = matrix;
        this.text = text;
    }

    public DefineText2() {
        super(33, 3);
    }

    public SWFTag read(int tagID, SWFInputStream swf, int len)
            throws IOException {
        DefineText2 tag = new DefineText2();
        tag.read(tagID, swf, len, true);
        return tag;
    }

    public void write(int tagID, SWFOutputStream swf) throws IOException {

        write(swf, true);
    }
}
