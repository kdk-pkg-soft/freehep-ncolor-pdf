// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * ApplicationData TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: ApplicationData.java 8584 2006-08-10 23:06:37Z duns $
 */
public class ApplicationData extends CGMTag {

    private int id;

    private byte[] data;

    public ApplicationData() {
        super(7, 2, 1);
    }

    public ApplicationData(int id, byte[] data) {
        this();
        this.id = id;
        this.data = data;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.writeInteger(id);
        cgm.writeData(data);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("APPLDATA ");
        cgm.writeInteger(id);
        cgm.println();
        cgm.writeData(data);
    }
}
