// Copyright 2005, FreeHEP.
package org.freehep.graphicsio.emf.test;

import org.freehep.graphicsio.test.TestSuite;

/**
 * @author Mark Donszelmann
 * @version $Id: EMFTestSuite.java 8584 2006-08-10 23:06:37Z duns $
 */
public class EMFTestSuite extends TestSuite {

    public static TestSuite suite() {
        EMFTestSuite suite = new EMFTestSuite();
        suite.addTests("EMF");
        return suite;
    }

}
