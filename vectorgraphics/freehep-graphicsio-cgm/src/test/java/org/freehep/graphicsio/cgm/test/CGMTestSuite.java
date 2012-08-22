// Copyright 2005, FreeHEP.
package org.freehep.graphicsio.cgm.test;

import org.freehep.graphicsio.test.TestSuite;

/**
 * @author Mark Donszelmann
 * @version $Id: CGMTestSuite.java 8584 2006-08-10 23:06:37Z duns $
 */
public class CGMTestSuite extends TestSuite {

    public static TestSuite suite() {
        CGMTestSuite suite = new CGMTestSuite();
        suite.addTests("CGM");
        return suite;
    }

}
