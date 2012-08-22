// Copyright 2001-2005, FreeHEP.
package org.freehep.util.io.test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.freehep.util.Assert;
import org.freehep.util.io.ConditionalInputStream;

/**
 * Test for Conditional Input Stream.
 * 
 * @author Mark Donszelmann
 * @version $Id: ConditionalInputStreamTest.java 8584 2006-08-10 23:06:37Z duns $
 */
public class ConditionalInputStreamTest extends AbstractStreamTest {

    private File testFile;
    
    protected void setUp() throws Exception {
        super.setUp();
        testFile = new File(testDir, "ConditionalInputStream.txt");
    }
    
    /**
     * Test method for 'org.freehep.util.io.RunLengthInputStream.read()'
     * @throws Exception if ref file cannot be found
     */
    public void testRead1() throws Exception {
        File refFile = new File(refDir, "ConditionalInputStream.ref1");
        Properties defines = new Properties();
            
        ConditionalInputStream in = new ConditionalInputStream(new FileInputStream(testFile), defines);        
        Assert.assertEquals(new FileInputStream(refFile), in, false, refFile.getPath());
    }    
    
    /**
     * Test method for 'org.freehep.util.io.RunLengthInputStream.read()'
     * @throws Exception if ref file cannot be found
     */
    public void testRead2() throws Exception {
        File refFile = new File(refDir, "ConditionalInputStream.ref2");
        Properties defines = new Properties();
        defines.setProperty("FREEHEP", "1");
            
        ConditionalInputStream in = new ConditionalInputStream(new FileInputStream(testFile), defines);
        Assert.assertEquals(new FileInputStream(refFile), in, false, refFile.getPath());
    }    
    

    /**
     * Test method for 'org.freehep.util.io.RunLengthInputStream.read()'
     * @throws Exception if ref file cannot be found
     */
    public void testRead3() throws Exception {
        File refFile = new File(refDir, "ConditionalInputStream.ref3");
        Properties defines = new Properties();
        defines.setProperty("FREEHEP", "1");
        defines.setProperty("WIRED", "1");
            
        ConditionalInputStream in = new ConditionalInputStream(new FileInputStream(testFile), defines);
//        OutputStream out = new FileOutputStream("ConditionalInputStream.ref3");
//        int b;
//        while ((b = in.read()) >= 0) out.write(b);
//        out.close();
        Assert.assertEquals(new FileInputStream(refFile), in, false, refFile.getPath());
    }    
}