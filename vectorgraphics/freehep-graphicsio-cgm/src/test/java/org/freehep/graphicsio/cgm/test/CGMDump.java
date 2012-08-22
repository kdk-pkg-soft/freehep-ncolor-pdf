// Copyright 2003, FreeHEP.
package org.freehep.graphicsio.cgm.test;

import java.io.FileInputStream;
import java.io.IOException;

import org.freehep.graphicsio.cgm.CGMInputStream;
import org.freehep.util.io.Tag;

/**
 * @author Mark Donszelmann
 * @version $Id: CGMDump.java 8584 2006-08-10 23:06:37Z duns $
 */
public class CGMDump {

    public static void main(String[] args) {

        try {
            FileInputStream fis = new FileInputStream(args[0]);
            CGMInputStream cgm = new CGMInputStream(fis);

            long start = System.currentTimeMillis();

            Tag tag = cgm.readTag();
            while (tag != null) {
                System.out.println(tag);
                tag = cgm.readTag();
            }
            System.out.println("Parsed file in: "
                    + (System.currentTimeMillis() - start) + " ms.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
