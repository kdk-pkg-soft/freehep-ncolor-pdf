// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.swf;

import java.io.IOException;
import java.util.Vector;

import org.freehep.util.io.Action;

/**
 * DoInitAction TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: DoInitAction.java 8584 2006-08-10 23:06:37Z duns $
 */
public class DoInitAction extends ControlTag {

    private Vector actions;

    public DoInitAction(Vector actions) {
        this();
        this.actions = actions;
    }

    public DoInitAction() {
        super(59, 6);
    }

    public SWFTag read(int tagID, SWFInputStream swf, int len)
            throws IOException {

        DoInitAction tag = new DoInitAction();
        tag.actions = new Vector();
        Action action = swf.readAction();
        while (action != null) {
            tag.actions.add(action);
            action = swf.readAction();
        }
        return tag;
    }

    public void write(int tagID, SWFOutputStream swf) throws IOException {
        for (int i = 0; i < actions.size(); i++) {
            Action a = (Action) actions.get(i);
            swf.writeAction(a);
        }
        swf.writeAction(null);
    }

    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append(super.toString() + "\n");
        for (int i = 0; i < actions.size(); i++) {
            s.append("  " + actions.get(i) + "\n");
        }
        return s.toString();
    }
}
