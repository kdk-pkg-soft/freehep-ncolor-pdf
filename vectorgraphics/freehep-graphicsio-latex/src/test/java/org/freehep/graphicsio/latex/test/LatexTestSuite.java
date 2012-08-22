// Copyright 2005, FreeHEP.
package org.freehep.graphicsio.latex.test;

import org.freehep.graphicsio.test.TestSuite;

/**
 * @author Mark Donszelmann
 * @version $Id: LatexTestSuite.java 8584 2006-08-10 23:06:37Z duns $
 */
public class LatexTestSuite extends TestSuite {

    public static TestSuite suite() {
        LatexTestSuite suite = new LatexTestSuite();
        suite.addTests("LATEX");
        return suite;
    }

}
