// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.swf;

/**
 * Abstract Definition TAG. All definition tags inherit from this class.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: DefinitionTag.java 8584 2006-08-10 23:06:37Z duns $
 */
public abstract class DefinitionTag extends SWFTag {

    protected DefinitionTag(int tagID, int version) {
        super(tagID, version);
    }

    public int getTagType() {
        return DEFINITION;
    }

    public String toString() {
        return "Definition: " + getName() + " (" + getTag() + ")";
    }

}
