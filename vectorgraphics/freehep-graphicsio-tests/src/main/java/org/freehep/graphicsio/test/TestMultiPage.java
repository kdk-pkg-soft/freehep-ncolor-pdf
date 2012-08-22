// Copyright 2005 freehep
package org.freehep.graphicsio.test;


/**
 * @author Mark Donszelmann
 * @version $Id: TestMultiPage.java 8584 2006-08-10 23:06:37Z duns $
 */
public class TestMultiPage extends TestingPanel {

    public TestMultiPage(String[] args) throws Exception {
        super(args);
        // Create a new instance of this class and add it to the frame.
        addPage("Colors", new TestColors(null));
        addPage("Clip", new TestClip(null));
        addPage("Lines", new TestLineStyles(null));
        addPage("Shapes", new TestShapes(null));
        addPage("Symbols", new TestSymbols2D(null));
    }

    public static void main(String[] args) throws Exception {
        new TestMultiPage(args).runTest();
    }
}
