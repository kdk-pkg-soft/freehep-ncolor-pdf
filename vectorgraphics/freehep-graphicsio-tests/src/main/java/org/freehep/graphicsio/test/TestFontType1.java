// Copyright 2003-2005, FreeHEP.
package org.freehep.graphicsio.test;

import org.freehep.util.UserProperties;

/**
 * 
 * @author Mark Donszelmann
 * @version $Id: TestFontType1.java 8584 2006-08-10 23:06:37Z duns $
 */
public class TestFontType1 extends TestTaggedString {

    public TestFontType1(String[] args) throws Exception {
        super(args);
        setName("Test Font Type1");
    }

    public static void main(String[] args) throws Exception {
        UserProperties p = new UserProperties();
        new TestFontType1(args).runTest(p);
    }
}
