// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * BeginPicture TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: BeginPicture.java 8584 2006-08-10 23:06:37Z duns $
 */
public class BeginPicture extends CGMTag {

    private String name;

    public BeginPicture() {
        super(0, 3, 1);
    }

    public BeginPicture(String name) {
        this();
        this.name = name;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.writeString(name);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("BEGPIC ");
        cgm.writeString(name);
        cgm.indent();
    }
}
