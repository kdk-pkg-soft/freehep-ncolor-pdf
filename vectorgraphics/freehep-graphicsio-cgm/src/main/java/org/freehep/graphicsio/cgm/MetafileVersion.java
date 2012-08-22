// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * MetafileVersion TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: MetafileVersion.java 8584 2006-08-10 23:06:37Z duns $
 */
public class MetafileVersion extends CGMTag {

    private int version;

    public MetafileVersion() {
        super(1, 1, 1);
    }

    public MetafileVersion(int version) {
        this();
        this.version = version;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.writeInteger(version);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("MFVERSION ");
        cgm.writeInteger(version);
    }
}
