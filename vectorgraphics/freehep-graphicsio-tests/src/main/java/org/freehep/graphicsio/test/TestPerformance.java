// University of California, Santa Cruz, USA and
// CERN, Geneva, Switzerland, Copyright (c) 2000
package org.freehep.graphicsio.test;


/**
 * @author Charles Loomis
 * @author Mark Donszelmann
 * @version $Id: TestPerformance.java 8584 2006-08-10 23:06:37Z duns $
 */
public class TestPerformance extends TestSymbols2D {

    public TestPerformance(String[] args) throws Exception {
        super(args);
        setName("Performance");
    }

    public static void main(String[] args) throws Exception {
        long t0 = System.currentTimeMillis();
        new TestSymbols2D(args).runTest();
        if (args.length > 0) {
            System.out.println(args[0] + " took "
                    + (System.currentTimeMillis() - t0) + " ms.");
        }
    }
}
