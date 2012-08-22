// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.swf.test;

import java.io.FileInputStream;
import java.io.IOException;

import org.freehep.graphicsio.swf.SWFHeader;
import org.freehep.graphicsio.swf.SWFInputStream;
import org.freehep.util.io.Tag;

/**
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: SWFDump.java 8584 2006-08-10 23:06:37Z duns $
 */
public class SWFDump {

    public static void main(String[] args) {

        try {
            if (args.length != 1) {
                System.err.println("Usage: SWFDump file.swf");
                System.exit(1);
            }

            FileInputStream fis = new FileInputStream(args[0]);
            SWFInputStream swf = new SWFInputStream(fis);

            long start = System.currentTimeMillis();
            SWFHeader header = swf.readHeader();
            System.out.println(header);

            Tag tag = swf.readTag();
            while (tag != null) {
                System.out.println(tag);
                tag = swf.readTag();
            }
            // System.out.println(swf.getDictionary());
            System.out.println("Parsed file in: "
                    + (System.currentTimeMillis() - start) + " ms.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
