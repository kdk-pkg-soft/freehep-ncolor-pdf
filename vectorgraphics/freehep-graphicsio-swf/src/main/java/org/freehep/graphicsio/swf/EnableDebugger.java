// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.swf;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * EnableDebugger TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: EnableDebugger.java 8584 2006-08-10 23:06:37Z duns $
 */
public class EnableDebugger extends ControlTag {

    private String password;

    public EnableDebugger(String password) {
        this();

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] b = md5.digest(password.getBytes());
            this.password = new String(b);
        } catch (NoSuchAlgorithmException nsae) {
            this.password = password;
        }
    }

    public EnableDebugger() {
        super(58, 5);
    }

    public SWFTag read(int tagID, SWFInputStream swf, int len)
            throws IOException {

        EnableDebugger tag = new EnableDebugger();
        tag.password = swf.readString();
        return tag;
    }

    public void write(int tagID, SWFOutputStream swf) throws IOException {
        swf.writeString(password);
    }
}
