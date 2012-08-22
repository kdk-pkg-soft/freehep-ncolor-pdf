// Copyright 2007, FreeHEP.
package org.freehep.util.argv.test;

import java.util.Iterator;
import java.util.List;

import org.freehep.util.argv.ArgumentFormatException;
import org.freehep.util.argv.ArgumentParser;
import org.freehep.util.argv.BooleanOption;
import org.freehep.util.argv.MissingArgumentException;
import org.freehep.util.argv.MultiStringOption;

/**
 * StringOption test.
 *
 * @author Mark Donszelmann
 * @version $Id: ArgvTest.java 10558 2007-03-01 19:38:51Z duns $
 */ 
public class MultiStringOptionTest {

    public static void main(String[] args) {
        BooleanOption help = new BooleanOption("-help", "-h", "Describe command line args", true );

        MultiStringOption mso =  new MultiStringOption("-I", "file", "Description of option" );
        
        ArgumentParser parser = new ArgumentParser("StringOptionTest");
        parser.add( help );
        parser.add( mso );

        List extra = null;
        try {
            extra = parser.parse( args );
            if (help.getValue()) {
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
        List includes = mso.getValue();
        if (includes != null) {
        	for (Iterator i=includes.iterator(); i.hasNext(); ) {
                System.out.println("mso     = "+i.next());        		
        	}
        }
        for (Iterator i=extra.iterator(); i.hasNext(); ) {
        	System.out.println("Extra: '"+i.next()+"'");
        }
    }
}
