// Copyright 2005, FreeHEP.
package org.freehep.graphicsio.pdf.test;

import java.util.Properties;

import org.freehep.graphicsio.pdf.PDFGraphics2D;
import org.freehep.graphicsio.test.TestPreviewThumbnail;
import org.freehep.util.UserProperties;

/**
 * @author Mark Donszelmann
 * @version $Id: PDFTestPreviewThumbnail.java 8584 2006-08-10 23:06:37Z duns $
 */
public class PDFTestPreviewThumbnail extends TestPreviewThumbnail {

    public PDFTestPreviewThumbnail(String[] args) throws Exception {
        super(args);
    }

    public void runTest(Properties options) throws Exception {
        UserProperties user = (options == null) ? new UserProperties()
                : new UserProperties(options);
        user.setProperty(PDFGraphics2D.THUMBNAILS, true);
        
        super.runTest(user);
    }

    public static void main(String[] args) throws Exception {
        new PDFTestPreviewThumbnail(args).runTest();
    }
}
