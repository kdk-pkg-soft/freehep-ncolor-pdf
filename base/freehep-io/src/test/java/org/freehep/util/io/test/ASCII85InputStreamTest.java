// Copyright 2001-2005, FreeHEP.
package org.freehep.util.io.test;

import java.io.File;
import java.io.FileInputStream;

import org.freehep.util.Assert;
import org.freehep.util.io.ASCII85InputStream;

/**
 * Test for ASCII85 Input Stream.
 * 
 * @author Mark Donszelmann
 * @version $Id: ASCII85InputStreamTest.java 8584 2006-08-10 23:06:37Z duns $
 */
public class ASCII85InputStreamTest extends AbstractStreamTest {
        
    /**
     * Test method for 'org.freehep.util.io.ASCII85InputStream.read()'
     * @throws Exception if ref file cannot be found
     */
    public void testRead() throws Exception {
        File testFile = new File(testDir, "TestFile.a85");
        File refFile = new File(refDir, "TestFile.xml");
            
        ASCII85InputStream in = new ASCII85InputStream(new FileInputStream(testFile));
        Assert.assertEquals(new FileInputStream(refFile), in, false, refFile.getPath());
    }
}
