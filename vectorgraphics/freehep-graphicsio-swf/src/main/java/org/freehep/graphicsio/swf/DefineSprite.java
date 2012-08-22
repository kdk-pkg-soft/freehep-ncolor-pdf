// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.swf;

import java.io.IOException;
import java.util.Vector;

/**
 * DefineSprite TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: DefineSprite.java 8584 2006-08-10 23:06:37Z duns $
 */
public class DefineSprite extends DefinitionTag {

    private int character;

    private int frameCount;

    private Vector tags;

    public DefineSprite(int id, int frameCount, Vector tags) {
        this();
        character = id;
        this.frameCount = frameCount;
        this.tags = tags;
    }

    public DefineSprite() {
        super(39, 3);
    }

    public SWFTag read(int tagID, SWFInputStream swf, int len)
            throws IOException {

        DefineSprite tag = new DefineSprite();
        tag.character = swf.readUnsignedShort();
        swf.getDictionary().put(tag.character, tag);

        tag.frameCount = swf.readUnsignedShort();

        int version = swf.getVersion();
        SWFInputStream sprite = new SWFInputStream(swf, new SWFSpriteTagSet(
                version), new SWFActionSet(version));

        tag.tags = new Vector();
        SWFTag miniTag;
        do {
            miniTag = (SWFTag) sprite.readTag();
            tag.tags.add(miniTag);
        } while (!(miniTag instanceof End));

        return tag;
    }

    public void write(int tagID, SWFOutputStream swf) throws IOException {
        swf.writeUnsignedShort(character);
        swf.writeUnsignedShort(frameCount);
        for (int i = 0; i < tags.size(); i++) {
            swf.writeTag((SWFTag) tags.get(i));
        }
    }

    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append(super.toString() + "\n");
        s.append("  character:  " + character + "\n");
        s.append("  frameCount: " + frameCount + "\n");
        s.append("  tags:       " + tags.size() + "\n");
        return s.toString();
    }
}
