// Copyright 2007, FreeHEP.
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
 * StringOption test.
 *
 * @author Mark Donszelmann
 * @version $Id: ArgvTest.java 10558 2007-03-01 19:38:51Z duns $
 */ 
public class StringOptionTest {

    public static void main(String[] args) {
        BooleanOption help = new BooleanOption("-help", "-h", "Describe command line args", true );

        StringOption so1 =  new StringOption("-so1", "option", "Description of option" );
        StringOption so2 =  new StringOption("-so2", "option", "default", "Description of option" );
        StringOption so3 =  new StringOption("-so3", "-s", "option", "default", "Description of option" );
        
        ArgumentParser parser = new ArgumentParser("StringOptionTest");
        parser.add( help );
        parser.add( so1 );
        parser.add( so2 );
        parser.add( so3 );

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
        System.out.println("so1     = "+so1.getValue());
        System.out.println("so2     = "+so2.getValue());
        System.out.println("so3     = "+so3.getValue());
        for (Iterator i=extra.iterator(); i.hasNext(); ) {
        	System.out.println("Extra: '"+i.next()+"'");
        }
    }
}
