// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.swf;

import java.io.IOException;

/**
 * VideoFrame TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: VideoFrame.java 8584 2006-08-10 23:06:37Z duns $
 */
public class VideoFrame extends DefinitionTag {

    private byte[] data;

    public VideoFrame() {
        super(61, 6);
    }

    public SWFTag read(int tagID, SWFInputStream swf, int len)
            throws IOException {

        VideoFrame tag = new VideoFrame();
        tag.data = swf.readByte(len);
        return tag;
    }

    public void write(int tagID, SWFOutputStream swf) throws IOException {
        swf.writeByte(data);
    }

    public String toString() {
        return super.toString() + "\n" + "  length: " + data.length + "\n";
    }
}
