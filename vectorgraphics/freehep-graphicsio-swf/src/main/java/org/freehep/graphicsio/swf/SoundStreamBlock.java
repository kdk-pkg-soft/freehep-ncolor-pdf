// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.swf;

import java.io.IOException;

/**
 * SoundStreamBlock TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: SoundStreamBlock.java 8584 2006-08-10 23:06:37Z duns $
 */
public class SoundStreamBlock extends DefinitionTag {

    private int[] data;

    public SoundStreamBlock(int[] data) {
        this();
        this.data = data;
    }

    public SoundStreamBlock() {
        super(19, 1);
    }

    public SWFTag read(int tagID, SWFInputStream swf, int len)
            throws IOException {
        SoundStreamBlock tag = new SoundStreamBlock();
        tag.data = swf.readUnsignedByte(len);
        return tag;
    }

    public void write(int tagID, SWFOutputStream swf) throws IOException {
        swf.writeUnsignedByte(data);
    }

    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append(super.toString() + "\n");
        s.append("  length" + data.length + "\n");
        return s.toString();
    }
}
