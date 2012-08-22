// Copyright 2001-2005, FreeHEP.
package org.freehep.util.io.test;

import java.io.File;
import java.io.FileInputStream;

import org.freehep.util.Assert;
import org.freehep.util.io.ASCIIHexInputStream;

/**
 * Test for ASCII Hex Input Stream
 * 
 * @author Mark Donszelmann
 * @version $Id: ASCIIHexInputStreamTest.java 8584 2006-08-10 23:06:37Z duns $
 */
public class ASCIIHexInputStreamTest extends AbstractStreamTest {

    /**
     * Test method for 'org.freehep.util.io.ASCIIHexInputStream.read()'
     * @throws Exception if ref file cannot be found
     */
    public void testRead() throws Exception {
        File testFile = new File(testDir, "TestFile.hex");
        File refFile = new File(refDir, "TestFile.xml");
            
        ASCIIHexInputStream in = new ASCIIHexInputStream(new FileInputStream(testFile));
        Assert.assertEquals(new FileInputStream(refFile), in, false, refFile.getPath());
    }
}
