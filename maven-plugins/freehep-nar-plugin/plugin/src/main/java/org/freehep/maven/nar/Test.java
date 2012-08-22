// Copyright FreeHEP, 2005-2007.
package org.freehep.maven.nar;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.MojoFailureException;

/**
 * Sets up a test to create
 *
 * @author <a href="Mark.Donszelmann@slac.stanford.edu">Mark Donszelmann</a>
 * @version $Id: Test.java 12936 2007-07-05 21:26:30Z duns $
 */
public class Test implements Executable {

    /**
     * Name of the test to create
     *
     * @required
     * @parameter expression=""
     */
    protected String name = null;

    /**
     * Type of linking used for this test
     * Possible choices are: "shared" or "static".
     * Defaults to "shared".
     * 
     * @parameter expression=""
     */
    protected String link = Library.SHARED;

    /**
     * When true run this test.
     * Defaults to true;
     * 
     * @parameter expresssion=""
     */
	protected boolean run=true;
	
	/**
	 * Arguments to be used for running this test.
	 * Defaults to empty list. This option is 
	 * only used if run=true.
	 * 
	 * @parameter expression=""
	 */
    protected List/*<String>*/ args = new ArrayList();
    
    public String getName() throws MojoFailureException {
        if (name == null) throw new MojoFailureException("NAR: Please specify <Name> as part of <Test>");
        return name;
    }
    
    public String getLink() {
        return link;
    }
    
    public boolean shouldRun() {
    	return run;
    }
    
    public List/*<String>*/ getArgs() {
    	return args;
    }
}

