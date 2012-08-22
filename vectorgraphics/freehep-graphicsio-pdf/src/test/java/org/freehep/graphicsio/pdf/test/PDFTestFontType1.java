// Copyright 2005, FreeHEP.
package org.freehep.graphicsio.pdf.test;

import java.util.Properties;

import org.freehep.graphicsio.FontConstants;
import org.freehep.graphicsio.pdf.PDFGraphics2D;
import org.freehep.graphicsio.test.TestFontType1;
import org.freehep.util.UserProperties;

/**
 * @author Mark Donszelmann
 * @version $Id: PDFTestFontType1.java 8584 2006-08-10 23:06:37Z duns $
 */
public class PDFTestFontType1 extends TestFontType1 {

    public PDFTestFontType1(String[] args) throws Exception {
        super(args);
    }

    public void runTest(Properties options) throws Exception {
        UserProperties user = (options == null) ? new UserProperties()
                : new UserProperties(options);
        user.setProperty(PDFGraphics2D.EMBED_FONTS, true);
        user.setProperty(PDFGraphics2D.EMBED_FONTS_AS,
                FontConstants.EMBED_FONTS_TYPE1);
        
        super.runTest(user);
    }

    public static void main(String[] args) throws Exception {
        new PDFTestFontType1(args).runTest();
    }
}
