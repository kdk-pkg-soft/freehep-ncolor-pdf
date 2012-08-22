// Copyright 2003-2005, FreeHEP.
package org.freehep.graphicsio.test;

import org.freehep.util.UserProperties;

/**
 * 
 * @author Mark Donszelmann
 * @version $Id: TestFontType3.java 8584 2006-08-10 23:06:37Z duns $
 */
public class TestFontType3 extends TestTaggedString {

    public TestFontType3(String[] args) throws Exception {
        super(args);
        setName("Test Font Type3");
    }

    public static void main(String[] args) throws Exception {
        UserProperties p = new UserProperties();
        new TestFontType3(args).runTest(p);
    }
}
