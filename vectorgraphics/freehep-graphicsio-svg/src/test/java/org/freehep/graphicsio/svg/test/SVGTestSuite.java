// Copyright 2005, FreeHEP.
package org.freehep.graphicsio.svg.test;

import org.freehep.graphicsio.test.TestSuite;

/**
 * @author Mark Donszelmann
 * @version $Id: SVGTestSuite.java 8584 2006-08-10 23:06:37Z duns $
 */
public class SVGTestSuite extends TestSuite {

    public static TestSuite suite() {
        SVGTestSuite suite = new SVGTestSuite();
        suite.addTests("SVG");
        return suite;
    }

}
