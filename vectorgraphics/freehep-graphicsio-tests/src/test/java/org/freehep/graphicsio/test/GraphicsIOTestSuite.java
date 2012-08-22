// Copyright 2005-2007, FreeHEP.
package org.freehep.graphicsio.test;

import org.freehep.graphicsio.ImageConstants;


/**
 * @author Mark Donszelmann
 * @version $Id: GraphicsIOTestSuite.java 10276 2007-01-09 19:24:50Z duns $
 */
public class GraphicsIOTestSuite extends TestSuite {

    public static TestSuite suite() {
        GraphicsIOTestSuite suite = new GraphicsIOTestSuite();
        suite.addTests(ImageConstants.BMP);
        suite.addTests(ImageConstants.GIF);
        suite.addTests(ImageConstants.JPG);
        suite.addTests(ImageConstants.PNG);
        suite.addTests(ImageConstants.WBMP);
        return suite;
    }

}
