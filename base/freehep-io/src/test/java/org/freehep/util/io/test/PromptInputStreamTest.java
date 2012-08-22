// Copyright 2001-2005, FreeHEP.
package org.freehep.util.io.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.freehep.util.Assert;
import org.freehep.util.io.PromptInputStream;
import org.freehep.util.io.PromptListener;
import org.freehep.util.io.RoutedInputStream;


/**
 * Test for Prompt Input Stream
 * @author Mark Donszelmann
 * @version $Id: PromptInputStreamTest.java 8584 2006-08-10 23:06:37Z duns $
 */
public class PromptInputStreamTest extends AbstractStreamTest {

    /**
     * Test PromptInputStream.read()
     * @throws Exception when ref file cannot be found
     */
    public void testPrompt() throws Exception {
        File testFile = new File(testDir, "PromptInputStream.txt");
        File refFile = new File(refDir, "PromptInputStream.out");
        File outFile = new File(outDir, "PromptInputStream.out");
        
        PromptInputStream in = new PromptInputStream(new FileInputStream(testFile));
        final PrintWriter writer = new PrintWriter(new FileWriter(outFile));
            final int promptNo = 0;
            in.addPromptListener("Idle>", new PromptListener() {
                public void promptFound(RoutedInputStream.Route route) {
                    writer.println();
                    writer.println("PROMPT[" + promptNo + "]: " + (new String(route.getStart())));
                }
            });

        int b = in.read(); 
        while (b >= 0) {
            writer.write(b);
            b = in.read();
        }
        in.close();
        writer.close();

        Assert.assertEquals(refFile, outFile, false);        
    }    
}
