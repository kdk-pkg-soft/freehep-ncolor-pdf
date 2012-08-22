// Copyright 2004, FreeHEP.
package org.freehep.util.argv.test;

import java.util.Iterator;
import java.util.List;

import org.freehep.util.argv.ArgumentFormatException;
import org.freehep.util.argv.ArgumentParser;
import org.freehep.util.argv.BooleanOption;
import org.freehep.util.argv.DoubleOption;
import org.freehep.util.argv.IntOption;
import org.freehep.util.argv.ListParameter;
import org.freehep.util.argv.MissingArgumentException;
import org.freehep.util.argv.StringOption;
import org.freehep.util.argv.StringParameter;

/**
 * Test case for Argv package.
 *
 * @author Mark Donszelmann
 * @version $Id: ArgvTest.java 10558 2007-03-01 19:38:51Z duns $
 */ 
public class ArgvTest {

    public static void main(String[] args) {
        BooleanOption help       = new BooleanOption(   "-help",                             "Describe command line args", true );
        StringOption  dst        = new StringOption(    "-dest", "destination", "localhost", "Destination for request tests" );
        DoubleOption  scale      = new DoubleOption(    "-scale", "factor", 1.0,             "Scale output by factor");
        IntOption     page       = new IntOption(       "-page",  "#", 1,                    "Display page #");
        StringParameter file     = new StringParameter( "File",                              "InputFile");
        ListParameter files      = new ListParameter(   "Files",                             "Additional Input Files");
        
        ArgumentParser parser = new ArgumentParser("ArgvTest");
        parser.add( dst );
        parser.add( help );
        parser.add( scale );
        parser.add( page );
        parser.add( file);
        parser.add( files);

        try {
            List extra = parser.parse( args );

            if( !extra.isEmpty() || help.getValue()) {
                parser.printUsage( System.out );
                System.exit( 0 );
            }
        } catch (MissingArgumentException mae) {
            System.out.println(mae.getMessage());
            System.exit(1);
        } catch (ArgumentFormatException afe) {
            System.out.println(afe.getMessage());
            System.exit(1);
        }
        
        System.out.println("ArgvTest ok");
        System.out.println("dest    = "+dst.getValue());
        System.out.println("scale   = "+scale.getDouble());
        System.out.println("page    = "+page.getInt());
        System.out.println("file    = "+file.getValue());
        int n = 0;
        for (Iterator i=files.getValue().iterator(); i.hasNext(); ) {
            n++;
            System.out.println("files["+n+"] = "+i.next());
        }
    }
}
