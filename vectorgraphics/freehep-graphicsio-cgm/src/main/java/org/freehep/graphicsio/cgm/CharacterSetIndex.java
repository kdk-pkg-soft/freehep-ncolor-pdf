// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * CharacterSetIndex TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: CharacterSetIndex.java 8584 2006-08-10 23:06:37Z duns $
 */
public class CharacterSetIndex extends CGMTag {

    private int index;

    public CharacterSetIndex() {
        super(5, 19, 1);
    }

    public CharacterSetIndex(int index) {
        this();
        this.index = index;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.writeIntegerIndex(index);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("CHARSETINDEX ");
        cgm.writeInteger(index);
    }
}
