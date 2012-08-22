// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.IOException;

/**
 * Message TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: Message.java 8584 2006-08-10 23:06:37Z duns $
 */
public class Message extends CGMTag {

    public final static int NO_ACTION = 0;

    public final static int ACTION = 1;

    private int action;

    private String message;

    public Message() {
        super(7, 1, 1);
    }

    public Message(int action, String message) {
        this();
        this.action = action;
        this.message = message;
    }

    public void write(int tagID, CGMOutputStream cgm) throws IOException {
        cgm.writeEnumerate(action);
        cgm.writeString(message);
    }

    public void write(int tagID, CGMWriter cgm) throws IOException {
        cgm.print("MESSAGE ");
        cgm.print((action == NO_ACTION) ? "NOACTION" : "ACTION");
        cgm.println();
        cgm.writeString(message);
    }
}
