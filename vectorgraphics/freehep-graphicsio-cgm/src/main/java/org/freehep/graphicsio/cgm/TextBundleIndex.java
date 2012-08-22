// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * TextBundleIndex TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: TextBundleIndex.java 8584 2006-08-10 23:06:37Z duns $
 */
public class TextBundleIndex extends CGMTag {

    private int index;

    public TextBundleIndex() {
        super(5, 9, 1);
    }

    public TextBundleIndex(int index) {
        this();
        this.index = index;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.writeIntegerIndex(index);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("TEXTINDEX ");
        cgm.writeInteger(index);
    }
}
