// Copyright 2001-2005, FreeHEP.
package org.freehep.util.io.test;

import java.io.File;
import java.io.FileInputStream;

import org.freehep.util.Assert;
import org.freehep.util.io.RunLengthInputStream;

/**
 * Test for Run Length Input Stream.
 * 
 * @author Mark Donszelmann
 * @version $Id: RunLengthInputStreamTest.java 8584 2006-08-10 23:06:37Z duns $
 */
public class RunLengthInputStreamTest extends AbstractStreamTest {

    /**
     * Test method for 'org.freehep.util.io.RunLengthInputStream.read()'
     * @throws Exception if ref file cannot be found
     */
    public void testRead() throws Exception {
        File testFile = new File(testDir, "TestFile.rnl");
        File refFile = new File(refDir, "TestFile.xml");
            
        RunLengthInputStream in = new RunLengthInputStream(new FileInputStream(testFile));
        Assert.assertEquals(new FileInputStream(refFile), in, true, refFile.getPath());
    }
}
