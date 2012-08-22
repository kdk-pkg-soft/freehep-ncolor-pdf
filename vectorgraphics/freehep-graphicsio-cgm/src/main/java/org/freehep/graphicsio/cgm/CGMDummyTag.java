// Copyright 2003, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * Dummy TAG, for reading to keep length and tagID.
 * 
 * @author Mark Donszelmann
 * @version $Id: CGMDummyTag.java 8584 2006-08-10 23:06:37Z duns $
 */
public class CGMDummyTag extends CGMTag {

    private int len;

    public CGMDummyTag(int tagID, CGMInputStream cgm, int len)
            throws IOException {
        super((tagID >> 7) & 0x0f, tagID & 0x7F, 1);
        this.len = len;
        // read dummy bytes
        cgm.readByte(len);
    }

    public CGMTag read(int tagID, CGMInputStream cgm, int len)
            throws IOException {
        System.err.println(getClass()
                + " is not supposed to be read like this.");
        return null;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        System.err.println(getClass()
                + " is not supposed to be written to output.");
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        System.err.println(getClass()
                + " is not supposed to be written to output.");
    }

    public int getLength() {
        return len;
    }

    public String toString() {
        return super.toString() + " length: " + len;
    }
}
