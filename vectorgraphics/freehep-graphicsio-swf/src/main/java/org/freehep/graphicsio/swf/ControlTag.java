// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.swf;

/**
 * Abstract Control TAG. All control tags derive from this tag.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: ControlTag.java 8584 2006-08-10 23:06:37Z duns $
 */
public abstract class ControlTag extends SWFTag {

    protected ControlTag(int tagID, int version) {
        super(tagID, version);
    }

    public int getTagType() {
        return CONTROL;
    }

    public String toString() {
        return "Control: " + getName() + " (" + getTag() + ")";
    }

}
