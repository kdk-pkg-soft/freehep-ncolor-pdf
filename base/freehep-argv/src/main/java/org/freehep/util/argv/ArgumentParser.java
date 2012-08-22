// Copyright (C) 2001 - 2007 by Oliver Goldman
// All Rights Reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted.
//
// Additional information available at http://software.charlie-dog.com.

//package com.charliedog.argv;
package org.freehep.util.argv;

import java.util.*;
import java.io.*;
import java.math.*;

/**
 * A parser for processing command line arguments. It is typically used as such:
 * <PRE>
 *     // Initialize arguments that may appear in the command line. By convention,
 *     // '-help' simply prints the command line usage and exits.
 *
 *     StringOption destination = new StringOption( "-dest", "localhost", "Destination for request tests" );
 *     BooleanOption help = new BooleanOption( "-help", "Describe command line args", true );
 *
 *     // Initialize and invoke the parser. Note that arguments not consumed during
 *     // the parse are returned in case they may be subject to additional processing,
 *     // etc. Variable 'args' is assumed to contain the String array passed to main().
 *
 *     ArgumentParser parser = new ArgumentParser();
 *     parser.add( destination );
 *     parser.add( skipClasses );
 *     parser.add( skipRequests );
 *     List extra = parser.parse( args );
 *
 *     // For this application, extra arguments will be treated as a usage error.
 *
 *     if( !extra.isEmpty() || help.getValue())
 *     {
 *         PrintWriter out = new PrintWriter( System.out );
 *         parser.printUsage( out );
 *         out.close();
 *         System.exit( 0 );
 *     }
 * </PRE>
 *
 * @see BooleanOption
 * @see StringOption
 */

public class ArgumentParser {
    private List/*<Option>*/ options = new LinkedList();
    private List/*<Parameter>*/ parameters = new LinkedList();
    private String name;

    public ArgumentParser(String name) {
        this.name = name;
    }

    /**
     * Add a new option to be taken into consideration during the next parse.
     * Behavior when an option is added multiple times or if multiple options
     * share the same flag is undefined.
     */

    public void add( Option opt ) {
        options.add( opt );
    }

    public void add( Parameter param ) {
        parameters.add( param );
    }

    /**
     * Dumps the usage of the program and each parameter and option to out.
     */

    public void printUsage( OutputStream out ) {
        PrintStream p = new PrintStream(out);
        p.print( "Usage: "+name);
        if (!options.isEmpty()) p.print(" [-options]");
        if (!parameters.isEmpty()) {
            Iterator i1 = parameters.iterator();
            while( i1.hasNext()) {
                Parameter param = (Parameter)( i1.next());
                p.print(" ");
                p.print(param.getName());
            }
            p.println();
            p.println();
            p.println( "parameters:" );
            Iterator i2 = parameters.iterator();
            int w = 0;
            while( i2.hasNext()) {
                Parameter param = (Parameter)( i2.next());
                w = Math.max(w, param.getName().length());
            }
            Iterator i3 = parameters.iterator();
            while( i3.hasNext()) {
                Parameter param = (Parameter)( i3.next());
                p.print("  ");
                p.print(pad(param.getName(), w));
                p.print("   ");
                p.println(param.getUsage());
            }
        } else {
            p.println();
        }

        if (!options.isEmpty()) {
            p.println();
            p.println( "options:" );
    
            Iterator i1 = options.iterator();
            int w = 0;
            while( i1.hasNext()) {
                Option opt = (Option)( i1.next());
                w = Math.max(w, opt.getOption().length());
            }
            Iterator i2 = options.iterator();
            while( i2.hasNext()) {
                Option opt = (Option)( i2.next());
                p.print("  ");
                p.print(pad(opt.getOption(), w));
                p.print("   ");
                p.println(opt.getUsage());
            }
        }
    }

    /**
     * Parses the given argument list according to all Options
     * registered with this parser. Returns any arguments not
     * consumed by the parse.
     */
    public List parse( String args[] ) throws MissingArgumentException, ArgumentFormatException {
        List/*<String>*/ list = Arrays.asList( args );
        return parse(list);
    }
    
    /**
     * Parses the given argument list according to all Options
     * registered with this parser. Returns any arguments not
     * consumed by the parse.
     */
    public List parse( List/*<String>*/ args ) throws MissingArgumentException, ArgumentFormatException {    
		List extras = new LinkedList();

        try {
    		perValue: while( !args.isEmpty()) {
    			// Give each Option a shot at parsing the list in its
    			// current form. Stop on the first match.
    
                Iterator i = options.iterator();
                while( i.hasNext()) {
                    Option opt = (Option)( i.next());
                    int numArgsConsumed = opt.parse( args );
    
    				if( numArgsConsumed > 0 ) {
    					args = args.subList( numArgsConsumed, args.size());
    					continue perValue;
    				}
    			}
    
    			// If no matches were found, move the first value to the extras
    			// list and try again. Don't use values.remove( 0 ) here because
    			// it is an optional method.
    
    			extras.add( args.get( 0 ));
    			args = args.subList( 1, args.size());
            }
    
            // now parse the parameters in order
            Iterator i = parameters.iterator();
            while( i.hasNext()) {
                Parameter parameter = (Parameter)( i.next());
                int numArgsConsumed = parameter.parse( extras );
    
    			if( numArgsConsumed > 0 ) {
    				extras = extras.subList( numArgsConsumed, extras.size());
    			}
    		}
        } catch (BailOutException boe) {
            // bailed out, no extras
            return Collections.EMPTY_LIST;
        }
        
        return extras;
    }

    
    private String pad(String s, int w) {
        if (w < s.length()) return s.substring(0, w);
        
        StringBuffer sb = new StringBuffer(s);
        for (int i=w-s.length(); i>0; i--) {
            sb.append(" ");
        }
        return sb.toString();
    }

    /**
     * Test driver.
     */

    public static void main( String ignored[] ) throws MissingArgumentException, ArgumentFormatException {
        String empty[] = {};

        String exact[] = {
            "-string1",
            "value1",
            "-n1",
            "0.0314159e2"
        };

        String extra[] = {
            "-string1",
            "value1",
            "-n2",
            "10",
            "-bool1",
            "-string2",
            "value2"
        };

        StringOption string1 = new StringOption( "-string1", "string1", "" );
        StringOption string3 = new StringOption( "-string3", "string3", "value3", "" );
        BooleanOption bool1 = new BooleanOption( "-bool1", "" );
		NumberOption n1 = new NumberOption( "-n1", "n1", "" );

        ArgumentParser parser = new ArgumentParser("ArgumentParser");
        parser.add( string1 );
        parser.add( string3 );
        parser.add( bool1 );
        parser.add( n1 );

		// ----------------------------------------------------------------------

        System.out.println( "Test 01-001: Run arg parser w/ no args" );
        parser.parse( empty );
        
		if( string1.getValue() != null ) {
			throw new RuntimeException( "Default value for string1 failed" );
		}
		if( string3.getValue() != "value3" ) {
			throw new RuntimeException( "Default value for string3 failed" );
		}
		if( bool1.getValue() != false ) {
			throw new RuntimeException( "Default value for bool1 failed" );
		}
		if( n1.getValue() != null ) {
			throw new RuntimeException( "Default value for n1 failed" );
		}

		// ----------------------------------------------------------------------

        System.out.println( "Test 01-002: Run arg parser w/ args" );
        List remaining = parser.parse( exact );

        System.out.println( "Test 02-001: Check string1" );
        if( !string1.getValue().equals( "value1" )) {
            throw new RuntimeException( "Parsing string1 failed" );
        }
		if( string3.getValue() != "value3" ) {
			throw new RuntimeException( "Default value for string3 failed" );
		}
		if( bool1.getValue() != false ) {
			throw new RuntimeException( "Default value for bool1 failed" );
		}
		if( !n1.getValue().equals( new BigDecimal( "3.14159" ))) {
			throw new RuntimeException( "Default value for n1 failed" );
		}
		if( !remaining.isEmpty()) {
			throw new RuntimeException( "Should be no remaining args" );
		}

		// ----------------------------------------------------------------------

        System.out.println( "Test 01-003: Run arg parser w/ extra args" );
        remaining = parser.parse( extra );

        System.out.println( "Test 02-001: Check string1" );
        if( !string1.getValue().equals( "value1" )) {
            throw new RuntimeException( "Parsing string1 failed" );
        }

        System.out.println( "Test 02-002: Check bool1" );
        if( !bool1.getValue()) {
            throw new RuntimeException( "bool1 should have been true" );
        }

        System.out.println( "Test 02-003: Check remaining args" );
        if( !remaining.equals( Arrays.asList( new String[] {
            "-n2",
            "10",
            "-string2",
            "value2"
        }))) {
            throw new RuntimeException( "Remaining args incorrect" );
        }

        System.out.println( "Test 02-004: Check string3" );
        if( !string3.getValue().equals( "value3" )) {
            throw new RuntimeException( "Default for string3 failed" );
        }

		// ----------------------------------------------------------------------

		System.out.println( "Test 3: ListArgument" );

		ListParameter lp = new ListParameter( "vacuum", "list of vacuums" );
		parser.add( lp );
		remaining = parser.parse( extra );

		// List will consume everything starting with -n2, which is the
		// first argument not recognized by some other parser.

		if( !lp.getValue().equals( Arrays.asList( new String[] {
            "-n2",
            "10",
            "-bool1",
            "-string2",
            "value2"
		}))) {
			throw new RuntimeException( "List args incorrect" );
		}
    }
}
