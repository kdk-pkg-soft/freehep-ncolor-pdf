// Copyright 2003, SLAC, Stanford, U.S.A
package org.freehep.util.io.test;

import java.io.File;
import java.io.FileFilter;

import org.freehep.util.Assert;
import org.freehep.util.io.StandardFileFilter;

/**
 * Test for the Standard File Filter.
 * 
 * @author duns
 * @version $Id: StandardFileFilterTest.java 8584 2006-08-10 23:06:37Z duns $
 */
public class StandardFileFilterTest extends AbstractStreamTest {

    /**
     * Counts *.txt files in the ref directory
     */
    public void testFileFilterTxt() {
        FileFilter filter = new StandardFileFilter("*.txt");
        File[] files = refDir.listFiles(filter);
        Assert.assertEquals(4, files.length);
    }

    /**
     * Counts *.ref* files in the ref directory
     */
    public void testFileFilterRef() {
        FileFilter filter = new StandardFileFilter("*.ref*");
        File[] files = refDir.listFiles(filter);
        Assert.assertEquals(3, files.length);
    }
}
