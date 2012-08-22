// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.swf;

import java.io.IOException;

/**
 * ExportAssets TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: ExportAssets.java 8584 2006-08-10 23:06:37Z duns $
 */
public class ExportAssets extends ControlTag {

    private int[] tags;

    private String[] assets;

    public ExportAssets(int[] tags, String[] assets) {
        this();
        this.tags = tags;
        this.assets = assets;
    }

    public ExportAssets() {
        super(56, 5);
    }

    public SWFTag read(int tagID, SWFInputStream swf, int len)
            throws IOException {

        ExportAssets tag = new ExportAssets();
        int n = swf.readUnsignedShort();
        tag.tags = new int[n];
        tag.assets = new String[n];
        for (int i = 0; i < n; i++) {
            tag.tags[i] = swf.readUnsignedShort();
            tag.assets[i] = swf.readString();
        }
        return tag;
    }

    public void write(int tagID, SWFOutputStream swf) throws IOException {
        swf.writeUnsignedShort(tags.length);
        for (int i = 0; i < tags.length; i++) {
            swf.writeUnsignedShort(tags[i]);
            swf.writeString(assets[i]);
        }
    }
}
