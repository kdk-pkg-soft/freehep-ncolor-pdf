// Copyright 2000-2005, FreeHEP.
package org.freehep.graphicsio.test;


/**
 * @author Mark Donszelmann
 * @version $Id: TestPreviewThumbnail.java 8584 2006-08-10 23:06:37Z duns $
 */
public class TestPreviewThumbnail extends TestAll {

    public TestPreviewThumbnail(String[] args) throws Exception {
        super(args);
        setName("Test Preview and/or Thumbnail");
    }

    public static void main(String[] args) throws Exception {
        new TestPreviewThumbnail(args).runTest();
    }
}
