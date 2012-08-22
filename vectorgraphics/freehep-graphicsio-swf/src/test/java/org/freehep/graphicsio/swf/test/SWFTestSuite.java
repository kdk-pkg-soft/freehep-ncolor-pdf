// Copyright 2005, FreeHEP.
package org.freehep.graphicsio.swf.test;

import org.freehep.graphicsio.test.TestSuite;

/**
 * @author Mark Donszelmann
 * @version $Id: SWFTestSuite.java 9198 2006-10-20 22:37:10Z duns $
 */
public class SWFTestSuite extends TestSuite {

    public static TestSuite suite() {
        SWFTestSuite suite = new SWFTestSuite();
        suite.addTests("SWF");
        return suite;
    }

    public static void main(String[] args) {
        new junit.textui.TestRunner().doRun(suite());
    }

}
