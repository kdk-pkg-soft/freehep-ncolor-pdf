// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * BeginMetafile TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: BeginMetafile.java 8584 2006-08-10 23:06:37Z duns $
 */
public class BeginMetafile extends CGMTag {

    private String name;

    public BeginMetafile() {
        super(0, 1, 1);
    }

    public BeginMetafile(String name) {
        this();
        this.name = name;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.writeString(name);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("BEGMF ");
        cgm.writeString(name);
        cgm.indent();
    }

}
