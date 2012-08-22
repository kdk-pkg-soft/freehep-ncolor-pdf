// Copyright 2003-2005, FreeHEP.
package org.freehep.util.io.test;

import java.io.File;
import java.io.FileInputStream;

import org.freehep.util.Assert;
import org.freehep.util.io.Base64InputStream;

/**
 * Test for Base64 Input Stream.
 * 
 * @author Mark Donszelmann
 * @version $Id: Base64InputStreamTest.java 8584 2006-08-10 23:06:37Z duns $
 */
public class Base64InputStreamTest extends AbstractStreamTest {

    /**
     * Test method for 'org.freehep.util.io.Base64InputStream.read()'
     * @throws Exception if ref file cannot be found
     */
    public void testRead() throws Exception {
        File testFile = new File(testDir, "TestFile.b64");
        File refFile = new File(refDir, "TestFile.xml");
            
        Base64InputStream in = new Base64InputStream(new FileInputStream(testFile));
        Assert.assertEquals(new FileInputStream(refFile), in, false, refFile.getPath());
    }
}
