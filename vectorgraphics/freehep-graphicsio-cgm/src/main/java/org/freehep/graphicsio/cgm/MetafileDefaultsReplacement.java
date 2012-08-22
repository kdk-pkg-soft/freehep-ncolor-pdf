// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * MetafileDefaultsReplacement TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: MetafileDefaultsReplacement.java 8584 2006-08-10 23:06:37Z duns $
 */
public class MetafileDefaultsReplacement extends CGMTag {

//    private int type;

    public MetafileDefaultsReplacement() {
        super(1, 12, 1);
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        throw new IOException("Not properly implemented.");
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        throw new IOException("Not properly implemented.");
    }
}
