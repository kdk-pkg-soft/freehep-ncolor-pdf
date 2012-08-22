// Copyright 2001, FreeHEP.
package org.freehep.util.io.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.freehep.util.Assert;
import org.freehep.util.io.RunLengthOutputStream;

/**
 * Test for Run Length Output Stream.
 * 
 * @author Mark Donszelmann
 * @version $Id: RunLengthOutputStreamTest.java 8584 2006-08-10 23:06:37Z duns $
 */
public class RunLengthOutputStreamTest extends AbstractStreamTest {

    /**
     * Test method for 'org.freehep.util.io.RunLengthOutputStream.write()'
     * @throws Exception if ref file cannot be found
     */
    public void testWrite() throws Exception {
        File testFile = new File(testDir, "TestFile.xml");
        File outFile = new File(outDir, "TestFile.rnl");
        File refFile = new File(refDir, "TestFile.rnl");
        
        RunLengthOutputStream out = new RunLengthOutputStream(new FileOutputStream(outFile));
        FileInputStream in = new FileInputStream(testFile);
        int b;
        while ((b = in.read()) >= 0) {
            out.write(b);
        }
        in.close();
        out.close();
        
        Assert.assertEquals(refFile, outFile, true);
    }
}
