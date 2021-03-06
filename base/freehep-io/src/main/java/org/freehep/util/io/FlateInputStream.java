// Copyright 2001-2003, FreeHEP.
package org.freehep.util.io;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

/**
 * The FlateInputStream uses the Deflate mechanism to compress data. The exact
 * definition of Deflate encoding can be found in the PostScript Language
 * Reference (3rd ed.) chapter 3.13.3.
 * 
 * @author Mark Donszelmann
 * @version $Id: FlateOutputStream.java,v 1.1 2001/06/05 10:32:52 duns Exp $
 */
public class FlateInputStream extends InflaterInputStream {

    /**
     * Create a (De)Flate input stream.
     * 
     * @param in stream to read from
     */
    public FlateInputStream(InputStream in) {
        super(in);
    }

    /**
     * Reads an image FIXME NOT IMPLEMENTED
     * 
     * @return null
     * @throws IOException
     */
    public Image readImage() throws IOException {
        return null;
    }
}
