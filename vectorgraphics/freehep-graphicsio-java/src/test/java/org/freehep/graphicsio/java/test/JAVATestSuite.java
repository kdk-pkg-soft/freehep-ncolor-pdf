// Copyright 2005-2006, FreeHEP.
package org.freehep.graphicsio.java.test;

import java.util.Properties;

import org.freehep.graphicsio.java.JAVAGraphics2D;
import org.freehep.graphicsio.test.TestSuite;

/**
 * @author Mark Donszelmann
 * @version $Id: JAVATestSuite.java 10220 2006-12-20 00:45:12Z duns $
 */
public class JAVATestSuite extends TestSuite {

    public static TestSuite suite() {
        JAVATestSuite suite = new JAVATestSuite();

        Properties properties = new Properties();
        properties.setProperty(JAVAGraphics2D.PACKAGE_NAME,
                "org.freehep.graphicsio.java.test");

//        suite.addTests("JAVA", "JAVA", "org/freehep/graphicsio/java/test", "java", true, properties);
        suite.addTests("JAVA", properties);
        return suite;
    }
}
