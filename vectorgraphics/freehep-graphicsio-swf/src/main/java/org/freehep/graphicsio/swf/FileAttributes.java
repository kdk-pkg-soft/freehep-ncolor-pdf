// Copyright 2006, FreeHEP.
package org.freehep.graphicsio.swf;

import java.io.IOException;

/**
 * FileAttributes TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: DefineBits.java 4973 2005-12-05 00:59:43Z duns $
 */
public class FileAttributes extends ControlTag {

	private boolean hasMetadata;
	private boolean useNetwork;
	
    public FileAttributes() {
        super(69, 1);
    }

    public SWFTag read(int tagID, SWFInputStream swf, int len)
            throws IOException {

        FileAttributes tag = new FileAttributes();
        swf.readUBits(2);
        tag.hasMetadata = swf.readBitFlag();
        swf.readUBits(3);
        tag.useNetwork = swf.readBitFlag();
        swf.readUBits(24);
        
        return tag;
    }

    public void write(int tagID, SWFOutputStream swf) throws IOException {

        swf.writeUBits(0, 2);
        swf.writeBitFlag(hasMetadata);
        swf.writeUBits(0, 3);
        swf.writeBitFlag(useNetwork);
        swf.writeUBits(0, 24);
    }

    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append(super.toString() + "\n");
        s.append("  hasMetaData:  " + hasMetadata + "\n");
        s.append("  useNetwork:   " + useNetwork + "\n");
        return s.toString();
    }
}
